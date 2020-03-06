package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.role.service.RoleDtoService;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.service.LoggedInUserDtoService;
import com.nilsign.springbootdemo.domain.user.service.UserDtoService;
import com.nilsign.springbootdemo.property.KeycloakProperties;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  private KeycloakProperties keycloakProperties;

  @Autowired
  private UserDtoService userDtoService;

  @Autowired
  private RoleDtoService roleDtoService;

  @Autowired
  private LoggedInUserDtoService loggedInUserDtoService;

  @GetMapping("/logged-in-user")
  @PreAuthorize("isAuthenticated()")
  public UserDto getLoggedInUser() {
    return loggedInUserDtoService.getLoggedInUserDto();
  }

  // Note, that Keycloak (Feb, 2020) does not offer an endpoint to request all users that are
  // assigned to a specific client role. Anyhow, pagination should be used here in the future
  // anyway, as the total amount of users is expected to be huge in real world projects. Then only
  // the currently returned (paginated) users need to request their roles, each by a single request,
  // which can run simultaneously. This facts are the reason why only realm Keycloak roles are added
  // here to the Jpa users.
  @GetMapping
  @PreAuthorize("hasRole('REALM_SUPERADMIN') OR hasRole('REALM_CLIENT_ADMIN')")
  public List<UserDto> findAll(HttpServletRequest request) {
    Mono<List<UserDto>> jpaUsersMono = Mono.fromCallable(() -> userDtoService.findAll());
    Mono<Set<String>> superAdminEmailAddressesMono = Mono.fromCallable(() ->
        getKeycloakClient(request)
            .realm(keycloakProperties.getRealm())
            .roles().get(RoleType.ROLE_REALM_SUPERADMIN.name())
            .getRoleUserMembers()
            .stream()
            .map(UserRepresentation::getEmail)
            .collect(Collectors.toSet()));
    // The zip method allows to combine the results of many different Mono's with the advantage that
    // all combined Monos are executed parallelly. That means that the total run time equals the run
    // time of the longest Mono.
    Mono<Pair<List<UserDto>, Set<String>>> resultPairMono = jpaUsersMono
        .zipWith(superAdminEmailAddressesMono)
        .map(tuple -> Pair.of(tuple.getT1(), tuple.getT2()));
    final List<UserDto> returnValue = new ArrayList<>();
    resultPairMono.subscribe(
        resultPair -> {
          resultPair.getFirst().forEach(userDto -> {
            if (resultPair.getSecond().contains(userDto.getEmail())) {
              // Adds the realm super admin role to the UserDto, which is loaded via the JPA (and NOT
              // via the Keycloak RestApi). User loaded via JPA lack all information which is stored
              // in Keycloak.
              userDto.getRoles().add(RoleDto.builder()
                .roleType(RoleType.ROLE_REALM_SUPERADMIN)
                .build());
            }
          });
          returnValue.addAll(resultPair.getFirst());
        },
        error -> log.error(error.toString())
    );
    return returnValue;
  }

  @GetMapping(path = "{id}")
  public Optional<UserDto> findById(@NotNull @PathVariable Long id) {
    return userDtoService.findById(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('REALM_SUPERADMIN') OR hasRole('REALM_CLIENT_ADMIN')")
  public Optional<UserDto> save(
      HttpServletRequest request,
      @NotNull @Valid @RequestBody UserDto dto) {
    Set<RoleDto> dbRoles = Collections.emptySet();
    dto.getRoles().forEach(role -> roleDtoService
        .findRoleByType(role.getRoleType())
        .ifPresent(dbRoles::add));
    dto.setRoles(dbRoles);
    saveUserInKeycloak(request, dto);
    return userDtoService.save(dto);
  }

  // TODO(nilsheumer): Introduce a keycloak service and move all keycloak RestAPI related code.
  private Keycloak getKeycloakClient(HttpServletRequest request) {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakProperties.getAuthServerUrl())
        .realm(keycloakProperties.getClient())
        .authorization(getKeycloakSecurityContext(request).getTokenString())
        .resteasyClient(new ResteasyClientBuilder()
            .connectionPoolSize(keycloakProperties.getConnectionPoolSize())
            .build())
        .build();
  }

  private KeycloakSecurityContext getKeycloakSecurityContext(HttpServletRequest request) {
    return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
  }

  private void saveUserInKeycloak(HttpServletRequest request, UserDto userDto) {
    List<String> realmRoles = List.of(
        "offline_access",
        "uma_authorization"
    );
    Map<String, List<String>> clientToRoles = Map.of("account", List.of(
        "manage-account",
        "view-profile"
    ));
    userDto.getRoles()
        .stream()
        .map(RoleDto::getRoleType)
        .forEach(roleType -> {
            switch (roleType) {
              case ROLE_REALM_SUPERADMIN:
                realmRoles.add(RoleType.ROLE_REALM_SUPERADMIN.name());
                addRoleToClient(
                    clientToRoles,
                    keycloakProperties.getClient(),
                    List.of(RoleType.ROLE_REALM_CLIENT_ADMIN.name()));
                addRoleToClient(
                    clientToRoles,
                    keycloakProperties.getAngularFrontEndClient(),
                    List.of(RoleType.ROLE_REALM_CLIENT_ADMIN.name()));
                addRoleToClient(
                    clientToRoles,
                   "realm-management",
                    List.of(
                        "manage-users",
                        "realm-admin",
                        "view-realm",
                        "view-users"));
                break;
              case ROLE_REALM_CLIENT_ADMIN:
              case ROLE_REALM_CLIENT_SELLER:
              case ROLE_REALM_CLIENT_BUYER:
                addRoleToClient(
                    clientToRoles,
                    keycloakProperties.getClient(),
                    List.of(roleType.name()));
                addRoleToClient(
                    clientToRoles,
                    keycloakProperties.getAngularFrontEndClient(),
                    List.of(roleType.name()));
                 break;
            }
        });

    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setEmail(userDto.getEmail());
    userRepresentation.setUsername(userDto.getFirstName());
    userRepresentation.setFirstName(userDto.getFirstName());
    userRepresentation.setLastName(userDto.getLastName());
    userRepresentation.setRealmRoles(realmRoles);
    userRepresentation.setClientRoles(clientToRoles);
    Keycloak keycloak = getKeycloakClient(request);
    keycloak.realm(keycloakProperties.getRealm()).users().create(userRepresentation);
  }

  private void addRoleToClient(
      Map<String, List<String>> clientToRoles,
      String clientName,
      List<String> roleNames) {
    if (clientToRoles.get(clientName) == null) {
      clientToRoles.put(clientName, Collections.emptyList());
    }
    roleNames.forEach(roleName -> {
      if (!clientToRoles.get(clientName).contains(roleName)) {
        clientToRoles.get(clientName).add(roleName);
      }
    });
  }
}

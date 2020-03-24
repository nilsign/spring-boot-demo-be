package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.domain.keycloak.KeycloakService;
import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.role.service.RoleDtoService;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.service.LoggedInUserDtoService;
import com.nilsign.springbootdemo.domain.user.service.UserDtoService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  private KeycloakService keycloakService;

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
  public List<UserDto> findAll(@NotNull HttpServletRequest request) {
    Mono<List<UserDto>> jpaUsersMono
        = Mono.fromCallable(() -> userDtoService.findAll());
    Mono<Set<String>> superAdminEmailAddressesMono
        = Mono.fromCallable(() -> keycloakService.findUserEmailAddressesByRealmRole(
            request, RoleType.ROLE_REALM_SUPERADMIN));
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
              // Adds the realm super admin role to the UserDto, which is loaded via the JPA (and
              // NOT via the Keycloak RestApi). Users loaded via the JPA lack all the information
              // that are stored in Keycloak, which includes the Keycloak managed roles.
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
  @PreAuthorize("hasRole('REALM_SUPERADMIN')")
  public Optional<UserDto> save(
      @NotNull HttpServletRequest request,
      @NotNull @Valid @RequestBody UserDto dto) {
    Set<RoleDto> dbRoles = new HashSet<>();
    dto.getRoles().forEach(role -> roleDtoService
        .findRoleByType(role.getRoleType())
        .ifPresent(dbRoles::add));
    dto.setRoles(dbRoles);
    keycloakService.saveUser(request, dto);
    return userDtoService.save(dto);
  }
}

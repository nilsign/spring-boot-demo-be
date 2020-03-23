package com.nilsign.springbootdemo.domain.keycloak;

import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.property.KeycloakProperties;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KeycloakService {

  @Autowired
  private KeycloakProperties keycloakProperties;

  public Set<String> findUserEmailAddressesByRealmRole(
     @NotNull HttpServletRequest request,
     @NotNull RoleType realmRoleType) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      return keycloak
          .realm(keycloakProperties.getRealm())
          .roles().get(realmRoleType.name())
          .getRoleUserMembers()
          .stream()
          .map(UserRepresentation::getEmail)
          .collect(Collectors.toSet());
    }
  }

  public UserRepresentation findUserByEmailAddress(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank @Email String email) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      List<UserRepresentation> foundUsers = keycloak
          .realm(keycloakProperties.getRealm())
          .users().search(email, 0, 1);
      return foundUsers != null && foundUsers.size() == 1
          ? foundUsers.get(0)
          : null;
    }
  }

  public void saveUser(@NotNull HttpServletRequest request, @NotNull UserDto userDto) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      UserRepresentation userRepresentation = new UserRepresentation();
      userRepresentation.setEnabled(true);
      userRepresentation.setEmail(userDto.getEmail());
      userRepresentation.setUsername(userDto.getFirstName());
      userRepresentation.setFirstName(userDto.getFirstName());
      userRepresentation.setLastName(userDto.getLastName());
      keycloak
          .realm(keycloakProperties.getRealm())
          .users()
          .create(userRepresentation);

      userRepresentation = findUserByEmailAddress(request, userDto.getEmail());
      if (userRepresentation == null || userRepresentation.getId() == null) {
        log.error(String.format(
            "Keycloak role mapping failed on the saved user. There is no user with the email '%s'.",
            userDto.getEmail()));
        return;
      }

      // If the save user has just a JPA role, but not the corresponding Keycloak managed role, the
      // missing Keycloak role is added here, before the actual Keycloak role mapping to the user
      // happens.
      Set<String> realmRolesToAdd = new HashSet<>();
      Set<String> realmClientRolesToAdd = new HashSet<>();
      userDto.getRoles().forEach(roleDto -> {
        switch (roleDto.getRoleType()) {
          case ROLE_JPA_GLOBALADMIN:
            realmRolesToAdd.add(RoleType.ROLE_REALM_SUPERADMIN.name());
            break;
          case ROLE_JPA_ADMIN:
            realmClientRolesToAdd.add(RoleType.ROLE_REALM_CLIENT_ADMIN.name());
            break;
          case ROLE_JPA_SELLER:
            realmClientRolesToAdd.add(RoleType.ROLE_REALM_CLIENT_SELLER.name());
            break;
          case ROLE_JPA_BUYER:
            realmClientRolesToAdd.add(RoleType.ROLE_REALM_CLIENT_BUYER.name());
            break;
        }
      });
      saveRealmRolesOfUser(keycloak, userRepresentation.getId(), realmRolesToAdd);
      saveRealmClientRolesOfUser(keycloak, userRepresentation.getId(), realmClientRolesToAdd);
    } catch (Exception e) {
      // TODO(nilsheumer): Add proper exception handling.
      log.error(e.getMessage());
    }
  }

  public void saveRealmRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank  String userId,
      @NotNull @NotEmpty Set<String> roleNames) {
    // Adds the Keycloak realm roles to the newly created super/global admin user.
    Map<String, RoleRepresentation> realmRoleNamesToRoleRepresentation
        = getRealmRoleNamesToRoleRepresentationMap(keycloak);
    List<RoleRepresentation> realmRoleRepresentationsToAdd = roleNames
        .stream()
        .filter(roleName -> realmRoleNamesToRoleRepresentation.containsKey(roleName))
        .map(roleName -> realmRoleNamesToRoleRepresentation.get(roleName))
        .collect(Collectors.toList());
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(realmRoleRepresentationsToAdd);

    // Adds the required internal Keycloak 'realm-management' client roles to the newly created
    // super/global admin user.
    String realmManagementClientUuid = getRealmClientUuid(
        keycloak,
        keycloakProperties.getKeycloakRealmManagementClient());;
    List<RoleRepresentation> realmManagementClientRoleRepresentationsToAdd = keycloak
        .realm(keycloakProperties.getRealm())
        .clients()
        .get(realmManagementClientUuid)
        .roles()
        .list()
        .stream()
        .filter(roleRepresentation -> roleRepresentation.getName().equals("manage-users")
            || roleRepresentation.getName().equals("realm-admin")
            || roleRepresentation.getName().equals("view-realm"))
        .collect(Collectors.toList());
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .clientLevel(realmManagementClientUuid)
        .add(realmManagementClientRoleRepresentationsToAdd);
  }

  private void saveRealmClientRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank  String userId,
      @NotNull @NotEmpty Set<String> roleNames) {

    List<RoleRepresentation> backendClientRoleRepresentations;
    List<RoleRepresentation> frontendClientRoleRepresentations;

    String backendClientUuid = getRealmClientUuid(
        keycloak, keycloakProperties.getKeycloakBackendClient());
    String frontendClientUuid = getRealmClientUuid(
        keycloak, keycloakProperties.getKeycloakAngularFrontendClient());

    backendClientRoleRepresentations = getRealmClientRoleNamesToRoleRepresentationMap(
        keycloak, backendClientUuid)
            .entrySet()
            .stream()
            .filter(entry -> roleNames.contains(entry.getKey()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());


    frontendClientRoleRepresentations = getRealmClientRoleNamesToRoleRepresentationMap(
        keycloak, frontendClientUuid)
            .entrySet()
            .stream()
            .filter(entry -> roleNames.contains(entry.getKey()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());

    // Note the backend Keycloak client and the frontend Keycloak client do have the same roles,
    // which are bound (just for demo purposes) together with according JPA roles (which can be
    // found in according the Postgres database table only, and not in Keycloak).
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .clientLevel(backendClientUuid)
        .add(backendClientRoleRepresentations);

    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .clientLevel(frontendClientUuid)
        .add(frontendClientRoleRepresentations);
  }

  private Map<String, RoleRepresentation> getRealmRoleNamesToRoleRepresentationMap(
      @NotNull Keycloak keycloak) {
    return keycloak
        .realm(keycloakProperties.getRealm())
        .roles()
        .list()
        .stream()
        .collect(Collectors.toMap(
            RoleRepresentation::getName,
            roleRepresentation -> roleRepresentation));
  }

  private Map<String, RoleRepresentation> getRealmClientRoleNamesToRoleRepresentationMap(
      @NotNull Keycloak keycloak, @NotNull @NotBlank String realmClientUuid) {
    return keycloak
        .realm(keycloakProperties.getRealm())
        .clients()
        .get(realmClientUuid)
        .roles()
        .list()
        .stream()
        .collect(Collectors.toMap(
            RoleRepresentation::getName,
            roleRepresentation -> roleRepresentation));
  }

  private String getRealmClientUuid(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String realmClientName) {
    List<ClientRepresentation> foundClientRepresentation = keycloak
        .realm(keycloakProperties.getRealm())
        .clients()
        .findByClientId(realmClientName);

    if (foundClientRepresentation.size() == 0) {
      log.error(String.format(
          "Keycloak role mapping failed. Could not find a client with name '%s'.",
          realmClientName));
      return null;
    }
    if (foundClientRepresentation.size() > 1) {
      log.error(String.format(
          "Keycloak role mapping failed. The Keycloak realm client name '%s' is not unique.",
          realmClientName));
      return null;
    }
    return foundClientRepresentation.get(0).getId();
  }

  private Keycloak getKeycloakClient(@NotNull HttpServletRequest request) {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakProperties.getAuthServerUrl())
        .realm(keycloakProperties.getKeycloakBackendClient())
        .authorization(getKeycloakSecurityContext(request).getTokenString())
        .resteasyClient(new ResteasyClientBuilder()
            .connectionPoolSize(keycloakProperties.getConnectionPoolSize())
            .build())
        .build();
  }

  private KeycloakSecurityContext getKeycloakSecurityContext(@NotNull HttpServletRequest request) {
    return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
  }
}

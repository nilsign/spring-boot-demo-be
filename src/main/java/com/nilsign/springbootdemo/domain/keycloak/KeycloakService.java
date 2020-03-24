package com.nilsign.springbootdemo.domain.keycloak;

import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
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
    } catch (Exception e) {
      // TODO(nilsheumer): Add proper exception handling.
      log.error(e.getMessage());
      return;
    }
    if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
      saveRolesOfUser(request, userDto.getEmail(), userDto.getRoles());
    }
  }

  public void saveRolesOfUser(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank @Email String emailAddress,
      @NotNull @NotEmpty Set<RoleDto> roleDtos) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      UserRepresentation userRepresentation = findUserByEmailAddress(request, emailAddress);
      if (userRepresentation == null || userRepresentation.getId() == null) {
        log.error(String.format(
            "Keycloak role mapping failed on the saved user. There is no user with the email '%s'.",
            emailAddress));
        return;
      }
      // If the save user has just a JPA role, but not the corresponding Keycloak managed role, the
      // missing Keycloak role is added here, before the actual Keycloak role mapping to the user
      // happens. Note that within a Keycloak realm role names must be unique.
      Set<String> realmRolesToAdd = new HashSet<>();
      Set<String> realmManagementClientRolesToAdd = new HashSet<>();
      Set<String> realmClientRolesToAdd = new HashSet<>();
      roleDtos.forEach(roleDto -> {
        switch (roleDto.getRoleType()) {
          case ROLE_JPA_GLOBALADMIN:
            realmRolesToAdd.add(RoleType.ROLE_REALM_SUPERADMIN.name());
            realmManagementClientRolesToAdd.clear();
            realmManagementClientRolesToAdd.addAll(
                Set.of("manage-users", "realm-admin", "view-realm"));
            break;
          case ROLE_JPA_ADMIN:
            realmClientRolesToAdd.add(RoleType.ROLE_REALM_CLIENT_ADMIN.name());
            if (realmManagementClientRolesToAdd.isEmpty()) {
              realmManagementClientRolesToAdd.add("view-users");
            }
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
      saveRealmManagementClientRolesOfUser(
          keycloak, userRepresentation.getId(), realmManagementClientRolesToAdd);
      saveRealmClientRolesOfUser(
          keycloak,
          getRealmClientUuid(keycloak, keycloakProperties.getKeycloakBackendClient()),
          userRepresentation.getId(),
          realmClientRolesToAdd);
      saveRealmClientRolesOfUser(
          keycloak,
          getRealmClientUuid(keycloak, keycloakProperties.getKeycloakAngularFrontendClient()),
          userRepresentation.getId(),
          realmClientRolesToAdd);
    } catch (Exception e) {
      // TODO(nilsheumer): Add proper exception handling.
      log.error(e.getMessage());
    }
  }

  private boolean containsRealmRoles(@NotNull Set<String> roleNames) {
    return roleNames.contains(RoleType.ROLE_REALM_SUPERADMIN.name());
  }

  private boolean containsRealmManagementClientRoles(@NotNull Set<String> roleNames) {
    return roleNames.contains(RoleType.ROLE_REALM_SUPERADMIN.name())
        || roleNames.contains(RoleType.ROLE_REALM_CLIENT_ADMIN.name());
  }

  private boolean containsRealmClientRoles(@NotNull Set<String> roleNames) {
    return roleNames.contains(RoleType.ROLE_REALM_CLIENT_ADMIN.name())
        || roleNames.contains(RoleType.ROLE_REALM_CLIENT_SELLER.name())
        || roleNames.contains(RoleType.ROLE_REALM_CLIENT_BUYER.name());
  }

  private void saveRealmRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank  String userId,
      @NotNull Set<String> roleNames) {
    if (!containsRealmRoles(roleNames)) {
      return;
    }
    List<RoleRepresentation> realmRoleRepresentationsToAdd = keycloak
        .realm(keycloakProperties.getRealm())
        .roles()
        .list()
        .stream()
        .filter(roleRepresentation -> roleNames.contains(roleRepresentation.getName()))
        .collect(Collectors.toList());
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(realmRoleRepresentationsToAdd);
  }

  private void saveRealmManagementClientRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank  String userId,
      @NotNull Set<String> roleNamesToAdd) {
    if (!containsRealmManagementClientRoles(roleNamesToAdd)) {
      return;
    }
    String realmManagementClientUuid = getRealmClientUuid(
        keycloak,
        keycloakProperties.getKeycloakRealmManagementClient());
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .clientLevel(realmManagementClientUuid)
        .add(getRealmClientRoleRepresentationsToAdd(
            keycloak,
            realmManagementClientUuid,
            roleNamesToAdd));
  }

  private void saveRealmClientRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String clientUuid,
      @NotNull @NotBlank String userUuid,
      @NotNull Set<String> roleNames) {
    if (!containsRealmClientRoles(roleNames)) {
      return;
    }
    keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userUuid)
        .roles()
        .clientLevel(clientUuid)
        .add(getRealmClientRoleRepresentationsToAdd(keycloak, clientUuid, roleNames));
  }

  private List<RoleRepresentation> getRealmClientRoleRepresentationsToAdd(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String realmClientUuid,
      @NotNull @NotEmpty Set<String> roleNamesToAdd) {
    return getRealmClientRoleRepresentations(keycloak, realmClientUuid)
        .stream()
        .filter(roleRepresentation -> roleNamesToAdd.contains(roleRepresentation.getName()))
        .collect(Collectors.toList());
  }

  private List<RoleRepresentation> getRealmClientRoleRepresentations(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String realmClientUuid) {
    return keycloak
        .realm(keycloakProperties.getRealm())
        .clients()
        .get(realmClientUuid)
        .roles()
        .list();
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

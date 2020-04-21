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
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// TODO(nilsheumer): Divide this class into a KeycloakUserService and KeycloakRoleService.
@Slf4j
@Service
public class KeycloakService {

  // All none default realm roles, created for just this project.
  private static final Set<String> REALM_ROLE_NAMES
      = Set.of(RoleType.ROLE_REALM_SUPERADMIN.name());

  // Set of roles for both none default Keycloak realm clients, DemoProjectRestApiClient and
  // DemoProjectAngularFrontendClient.
  private static final Set<String> REALM_CLIENT_ROLE_NAMES
      = Set.of(
          RoleType.ROLE_REALM_CLIENT_ADMIN.name(),
          RoleType.ROLE_REALM_CLIENT_SELLER.name(),
          RoleType.ROLE_REALM_CLIENT_BUYER.name());

  // Keycloak internal roles that are required to view and modify users and roles.
  private static final Set<String> REALM_MANAGEMENT_CLIENT_SUPERADMIN_ROLE_NAMES
      = Set.of("manage-users", "realm-admin", "view-realm");

  // Keycloak internal roles that are required to view users and roles.
  private static final Set<String> REALM_MANAGEMENT_CLIENT_ADMIN_ROLE_NAMES
      = Set.of("view-realm");

  @Autowired
  private KeycloakProperties keycloakProperties;

  public Set<String> findUserEmailAddressesByRealmRole(
     @NotNull HttpServletRequest request,
     @NotNull RoleType realmRoleType) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      return keycloak
          .realm(keycloakProperties.getRealm())
          .roles()
          .get(realmRoleType.name())
          .getRoleUserMembers()
          .stream()
          .map(UserRepresentation::getEmail)
          .collect(Collectors.toSet());
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public Optional<UserDto> findUserWithRolesByEmailAddress(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank @Email String email) {
    Optional<UserRepresentation> userRepresentation
        = findUserRepresentationByEmailAddress(request, email);
    try (Keycloak keycloak = getKeycloakClient(request)) {
      Set<RoleDto> roleDtos = new HashSet<>();
      if (userRepresentation.isPresent()) {
        MappingsRepresentation roleMappingsRepresentation = keycloak
            .realm(keycloakProperties.getRealm())
            .users()
            .get(userRepresentation.get().getId())
            .roles()
            .getAll();
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        roleRepresentations.addAll(roleMappingsRepresentation.getRealmMappings());
        if (roleMappingsRepresentation.getClientMappings().containsKey(
            keycloakProperties.getKeycloakBackendClient())) {
          roleRepresentations.addAll(roleMappingsRepresentation
              .getClientMappings()
              .get(keycloakProperties.getKeycloakBackendClient())
              .getMappings());
        }
        if (roleMappingsRepresentation.getClientMappings().containsKey(
            keycloakProperties.getKeycloakAngularFrontendClient())) {
          roleRepresentations.addAll(roleMappingsRepresentation
              .getClientMappings()
              .get(keycloakProperties.getKeycloakAngularFrontendClient())
              .getMappings());
        }
        roleDtos = roleRepresentations
            .stream()
            .map(roleRepresentation ->
                RoleDto.builder()
                    .roleType(RoleType.from(roleRepresentation.getName()))
                    .build())
            .collect(Collectors.toSet());
      }
      return userRepresentation.isEmpty()
          ? Optional.empty()
          : Optional.of(UserDto.builder()
              .firstName(userRepresentation.get().getFirstName())
              .lastName(userRepresentation.get().getLastName())
              .email(userRepresentation.get().getEmail())
              .roles(roleDtos)
              .build());
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public List<UserDto> searchUsers(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank String text) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      List<UserRepresentation> foundUsers = keycloak
          .realm(keycloakProperties.getRealm())
          .users()
          .search(text, 0, 250, true);
      return foundUsers
          .stream()
          .map(userRepresentation -> UserDto.builder()
              .firstName(userRepresentation.getFirstName())
              .lastName(userRepresentation.getLastName())
              .email(userRepresentation.getEmail())
              .build())
          .collect(Collectors.toList());
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public void saveUser(
      @NotNull HttpServletRequest request,
      @NotNull UserDto userDto) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      Optional<UserRepresentation> userRepresentation = findUserRepresentationByEmailAddress(
          request, userDto.getEmail());
      if (userRepresentation.isEmpty()) {
        // Creates new user if not existing.
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(true);
        credentialRepresentation.setValue("root");
        userRepresentation = Optional.of(new UserRepresentation());
        userRepresentation.get().setEnabled(true);
        userRepresentation.get().setCredentials(List.of(credentialRepresentation));
        userRepresentation.get().setRequiredActions(List.of("UPDATE_PASSWORD"));
        userRepresentation.get().setEmail(userDto.getEmail());
        userRepresentation.get().setUsername(userDto.getFirstName());
        userRepresentation.get().setFirstName(userDto.getFirstName());
        userRepresentation.get().setLastName(userDto.getLastName());
        keycloak
          .realm(keycloakProperties.getRealm())
          .users()
          .create(userRepresentation.get());
      } else {
        // Updates existing user.
        userRepresentation.get().setEmail(userDto.getEmail());
        userRepresentation.get().setUsername(userDto.getFirstName());
        userRepresentation.get().setFirstName(userDto.getFirstName());
        userRepresentation.get().setLastName(userDto.getLastName());
        keycloak
            .realm(keycloakProperties.getRealm())
            .users()
            .get(userRepresentation.get().getId())
            .update(userRepresentation.get());
      }
      if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
        saveRolesOfUser(request, userDto.getEmail(), userDto.getRoles());
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      return;
    }
  }

  public void saveRolesOfUser(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank @Email String emailAddress,
      @NotNull @NotEmpty Set<RoleDto> roleDtos) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      Optional<UserRepresentation> userRepresentation
          = findUserRepresentationByEmailAddress(request, emailAddress);
      if (userRepresentation.isEmpty() || userRepresentation.get().getId() == null) {
        log.error(String.format(
            "Keycloak role mapping failed on the saved user. There is no user with the email '%s'.",
            emailAddress));
        return;
      }
      // If the given user has just a JPA role, but not the corresponding Keycloak managed role, the
      // missing role collected here, before the actual Keycloak role mapping to the user occurs.
      // Note that within a Keycloak realm role names must be unique.
      Set<String> realmRolesToAdd = new HashSet<>();
      Set<String> realmManagementClientRolesToAdd = new HashSet<>();
      Set<String> realmClientRolesToAdd = new HashSet<>();
      roleDtos.forEach(roleDto -> {
        switch (roleDto.getRoleType()) {
          case ROLE_JPA_GLOBALADMIN:
            realmRolesToAdd.addAll(REALM_ROLE_NAMES);
            realmManagementClientRolesToAdd.remove(REALM_MANAGEMENT_CLIENT_ADMIN_ROLE_NAMES);
            realmManagementClientRolesToAdd.addAll(REALM_MANAGEMENT_CLIENT_SUPERADMIN_ROLE_NAMES);
            break;
          case ROLE_JPA_ADMIN:
            realmClientRolesToAdd.add(RoleType.ROLE_REALM_CLIENT_ADMIN.name());
            if (realmManagementClientRolesToAdd.isEmpty()) {
              realmManagementClientRolesToAdd.addAll(REALM_MANAGEMENT_CLIENT_ADMIN_ROLE_NAMES);
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
      saveRealmRolesOfUser(keycloak, userRepresentation.get().getId(), realmRolesToAdd);
      saveRealmManagementClientRolesOfUser(
          keycloak, userRepresentation.get().getId(), realmManagementClientRolesToAdd);
      saveRealmClientRolesOfUser(
          keycloak,
          getRealmClientUuid(keycloak, keycloakProperties.getKeycloakBackendClient()),
          userRepresentation.get().getId(),
          realmClientRolesToAdd);
      saveRealmClientRolesOfUser(
          keycloak,
          getRealmClientUuid(keycloak, keycloakProperties.getKeycloakAngularFrontendClient()),
          userRepresentation.get().getId(),
          realmClientRolesToAdd);
    } catch (Exception e) {
      // TODO(nilsheumer): Add proper exception handling.
      log.error(e.getMessage());
    }
  }

  private Optional<UserRepresentation> findUserRepresentationByEmailAddress(
      @NotNull HttpServletRequest request,
      @NotNull @NotBlank @Email String email) {
    try (Keycloak keycloak = getKeycloakClient(request)) {
      List<UserRepresentation> foundUsers = keycloak
          .realm(keycloakProperties.getRealm())
          .users()
          .search(email, 0, 1);
      return foundUsers != null && foundUsers.size() == 1
          ? Optional.of(foundUsers.get(0))
          : Optional.empty();
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  private boolean hasIntersectionWithRealmRoles(@NotNull Set<String> roleNames) {
    return roleNames
        .stream()
        .filter(roleName -> REALM_ROLE_NAMES.contains(roleName))
        .findFirst()
        .isPresent();
  }

  private boolean containsRealmManagementClientRoles(@NotNull Set<String> roleNames) {
    return roleNames.containsAll(REALM_MANAGEMENT_CLIENT_SUPERADMIN_ROLE_NAMES)
        || roleNames.containsAll(REALM_MANAGEMENT_CLIENT_ADMIN_ROLE_NAMES);
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
    RoleScopeResource roleScopeResource = keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .realmLevel();
    // Deletes all existing realm role mappings of the user, except of the Keycloak default roles.
    roleScopeResource.remove(roleScopeResource.listEffective()
        .stream()
        .filter(
            roleRepresentation -> REALM_ROLE_NAMES.contains(roleRepresentation.getName()))
        .collect(Collectors.toList()));
    if (!hasIntersectionWithRealmRoles(roleNames)) {
      // No roles to add, early exit.
      return;
    }
    List<RoleRepresentation> realmRoleRepresentationsToAdd = keycloak
        .realm(keycloakProperties.getRealm())
        .roles()
        .list()
        .stream()
        .filter(roleRepresentation -> roleNames.contains(roleRepresentation.getName()))
        .collect(Collectors.toList());
    roleScopeResource.add(realmRoleRepresentationsToAdd);
  }

  private void saveRealmManagementClientRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank  String userId,
      @NotNull Set<String> roleNamesToAdd) {
    String realmManagementClientUuid = getRealmClientUuid(
        keycloak,
        keycloakProperties.getKeycloakRealmManagementClient());
    RoleScopeResource roleScopeResource = keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userId)
        .roles()
        .clientLevel(realmManagementClientUuid);
    Set<String> roleNamesToRemove = new HashSet<>();
    roleNamesToRemove.addAll(KeycloakService.REALM_MANAGEMENT_CLIENT_SUPERADMIN_ROLE_NAMES);
    roleNamesToRemove.addAll(KeycloakService.REALM_MANAGEMENT_CLIENT_ADMIN_ROLE_NAMES);
    roleScopeResource.remove(getRealmClientRoleRepresentations(
        keycloak,
        realmManagementClientUuid,
        roleNamesToRemove
    ));
    if (containsRealmManagementClientRoles(roleNamesToAdd)) {
      // Assign the additional and save the updated role mapping.
      roleScopeResource.add(getRealmClientRoleRepresentations(
          keycloak,
          realmManagementClientUuid,
          roleNamesToAdd));
    }
  }

  private void saveRealmClientRolesOfUser(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String clientUuid,
      @NotNull @NotBlank String userUuid,
      @NotNull Set<String> roleNames) {
    RoleScopeResource roleScopeResource =  keycloak
        .realm(keycloakProperties.getRealm())
        .users()
        .get(userUuid)
        .roles()
        .clientLevel(clientUuid);
    roleScopeResource.remove(getRealmClientRoleRepresentations(
        keycloak,
        clientUuid,
        KeycloakService.REALM_CLIENT_ROLE_NAMES));

    if (!containsRealmClientRoles(roleNames)) {
      return;
    }
    roleScopeResource.add(getRealmClientRoleRepresentations(keycloak, clientUuid, roleNames));
  }

  private List<RoleRepresentation> getRealmClientRoleRepresentations(
      @NotNull Keycloak keycloak,
      @NotNull @NotBlank String realmClientUuid,
      @NotNull @NotEmpty Set<String> roleNames) {
    return getRealmClientRoleRepresentations(keycloak, realmClientUuid)
        .stream()
        .filter(roleRepresentation -> roleNames.contains(roleRepresentation.getName()))
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

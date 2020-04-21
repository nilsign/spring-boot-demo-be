package com.nilsign.springbootdemo.domain.role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This enum defines roles that are loaded via the JPA and also the roles defined by the OAuth2
 * provider (here Keycloak).
 */
public enum RoleType {
  ROLE_JPA_GLOBALADMIN("ROLE_JPA_GLOBALADMIN"),
  ROLE_JPA_ADMIN("ROLE_JPA_ADMIN"),
  ROLE_JPA_SELLER("ROLE_JPA_SELLER"),
  ROLE_JPA_BUYER("ROLE_JPA_BUYER"),
  ROLE_REALM_SUPERADMIN("ROLE_REALM_SUPERADMIN"),
  ROLE_REALM_CLIENT_ADMIN("ROLE_REALM_CLIENT_ADMIN"),
  ROLE_REALM_CLIENT_SELLER("ROLE_REALM_CLIENT_SELLER"),
  ROLE_REALM_CLIENT_BUYER("ROLE_REALM_CLIENT_BUYER"),
  // Default Keycloak realm roles.
  ROLE_USER("ROLE_USER"),
  ROLE_OFFLINE_ACCESS("ROLE_OFFLINE_ACCESS"),
  ROLE_UMA_AUTHORIZATION("ROLE_UMA_AUTHORIZATION");

  public static final String ROLE_TYPE_NAME_PREFIX = "ROLE_";

  private final String name;

  RoleType(@NotNull String name) {
    this.name = name;
  }

  public static RoleType from(@NotNull @NotEmpty String name) {
    name = name.trim().toUpperCase();
    if (!name.startsWith(ROLE_TYPE_NAME_PREFIX)) {
      name = ROLE_TYPE_NAME_PREFIX + name;
    }
    try {
      return RoleType.valueOf(name);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}

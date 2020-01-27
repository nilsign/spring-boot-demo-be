package com.nilsign.springbootdemo.domain.role;

import javax.validation.constraints.NotNull;

/**
 * This enum defines roles that are loaded via the JPA. So, Keycloak realm and client roles are
 * excluded here.
 */
public enum RoleType {
  ROLE_JPA_GLOBALADMIN("ROLE_JPA_GLOBALADMIN"),
  ROLE_JPA_ADMIN("ROLE_JPA_ADMIN"),
  ROLE_JPA_SELLER("ROLE_JPA_SELLER"),
  ROLE_JPA_BUYER("ROLE_JPA_BUYER"),
  ROLE_REALM_SUPERADMIN("ROLE_REALM_SUPERADMIN"),
  ROLE_REALM_CLIENT_ADMIN("ROLE_REALM_CLIENT_ADMIN"),
  ROLE_REALM_CLIENT_SELLER("ROLE_REALM_CLIENT_SELLER"),
  ROLE_REALM_CLIENT_BUYER("ROLE_REALM_CLIENT_BUYER");;

  private final String name;

  RoleType(@NotNull String name) {
    this.name = name;
  }
}

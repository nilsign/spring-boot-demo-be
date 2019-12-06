package com.nilsign.springbootdemo.entity;

import javax.validation.constraints.NotNull;

public enum RoleType {
  GLOBALADMIN("GLOBALADMIN"),
  ADMIN("ADMIN"),
  SELLER("SELLER"),
  SUPPORT("SUPPORT"),
  BUYER("BUYER");

  private final String name;

  RoleType(@NotNull String name) {
    this.name = name;
  }
}

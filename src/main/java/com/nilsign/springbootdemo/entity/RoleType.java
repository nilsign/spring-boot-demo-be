package com.nilsign.springbootdemo.entity;

public enum RoleType {
  GLOBALADMIN("GLOBALADMIN"),
  ADMIN("ADMIN"),
  SELLER("SELLER"),
  SUPPORT("SUPPORT"),
  BUYER("BUYER");

  private final String name;

  RoleType(String name) {
    this.name = name;
  }
}

package com.nilsign.springbootdemo.entity.base;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class Entity<T> {

  public abstract T getId();

  // Use lomboks ToString.Exclude instead of defining the toString method for every entity, if you want to reduce the boiler-plate code.
  @Override
  public abstract String toString();
}

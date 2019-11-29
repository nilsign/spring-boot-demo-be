package com.nilsign.springbootdemo.entity.base;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class Entity<T> {

  public abstract T getId();

  @Override
  public abstract String toString();
}

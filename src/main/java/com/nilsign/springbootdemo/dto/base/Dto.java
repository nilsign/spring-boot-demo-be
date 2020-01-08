package com.nilsign.springbootdemo.dto.base;

public interface Dto {

  // toString doesn't have to be in the interface.
  // Use lomboks @ToString.Exclude to customize the toString method.
  String toString();
}

package com.nilsign.springbootdemo.helper;

import java.util.function.Predicate;

public final class JavaStreamHelper {

  private JavaStreamHelper() {
  }

  public static <R> Predicate<R> not(Predicate<R> predicate) {
    return predicate.negate();
  }
}

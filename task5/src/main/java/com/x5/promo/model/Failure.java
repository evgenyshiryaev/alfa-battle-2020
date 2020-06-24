package com.x5.promo.model;

import java.util.Objects;

import lombok.Value;

@Value
public class Failure {
  String exception;
  String message;

  public static Failure of(Throwable throwable) {
    Objects.requireNonNull(throwable, () -> "throwable must not be null");
    return new Failure(throwable.getClass().getName(), throwable.getMessage());
  }
}

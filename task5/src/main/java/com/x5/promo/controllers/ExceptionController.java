package com.x5.promo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.x5.promo.model.Failure;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Failure> processException(IllegalArgumentException exception) {
    return ResponseEntity.badRequest().body(Failure.of(exception));
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Failure> processException(NullPointerException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Failure.of(exception));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Failure> processException(MethodArgumentNotValidException exception) {
    return ResponseEntity.badRequest().body(Failure.of(exception));
  }
}

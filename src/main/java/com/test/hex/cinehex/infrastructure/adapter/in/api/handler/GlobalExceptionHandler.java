package com.test.hex.cinehex.infrastructure.adapter.in.api.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.test.hex.cinehex.domain.exception.SeatAlreadyBookedException;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.exception.ErrorResponse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SeatAlreadyBookedException.class)
  public ResponseEntity<@NonNull ErrorResponse> handleBussinesException(SeatAlreadyBookedException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), LocalDateTime.now());

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<@NonNull Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Traducimos a 400
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<@NonNull ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage(), LocalDateTime.now());
    // Aquí podrías loguear el stacktrace real en consola/logs
    log.info("Se ha producido un error inesperado: {}", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500
  }

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<@NonNull ErrorResponse> handleOptimisticLockingFailureException(
      OptimisticLockingFailureException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.CONFLICT.name(),
            "Conflict occurred due to concurrent updates. Please retry.",
            LocalDateTime.now());

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<@NonNull ErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), LocalDateTime.now());

    return ResponseEntity.badRequest().body(errorResponse);
  }
}

package com.oliver.portfolio.endpoint.exceptionHandler;

import com.oliver.portfolio.exception.ConflictException;
import com.oliver.portfolio.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandles;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  @ExceptionHandler(value = {ConflictException.class})
  protected ResponseEntity<Object> handleConflictException(ConflictException e) {
    LOGGER.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("status", "CONFLICT_ERROR", "errors", e.getErrors()));
  }
  
  @ExceptionHandler(value = {ValidationException.class})
  protected ResponseEntity<Object> handleValidationException(ValidationException e) {
    LOGGER.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(Map.of("status", "VALIDATION_ERROR", "errors", e.getErrors()));
  }
}

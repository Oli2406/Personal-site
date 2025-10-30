package com.oliver.portfolio.exception;

import java.util.List;

public class ValidationException extends ErrorListException {
  public ValidationException(String message, List<String> errors) {
    super("Validation failed", message, errors);
  }
}

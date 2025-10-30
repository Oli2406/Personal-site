package com.oliver.portfolio.exception;

import java.util.List;

public class ConflictException extends ErrorListException {
  public ConflictException(String message, List<String> errors) {
    super("Conflicts", message, errors);
  }
}

package com.epam.war.domain;

public class SpecialCodeFileMangledException extends RuntimeException {
  public SpecialCodeFileMangledException(String message) {
    super(message);
  }

  public SpecialCodeFileMangledException(String message, Throwable cause) {
    super(message, cause);
  }
}

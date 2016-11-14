package com.zireck.remotecraft.infrastructure.exception;

public class InvalidWorldException extends Exception {

  public InvalidWorldException() {
    super();
  }

  public InvalidWorldException(String message) {
    super(message);
  }

  public InvalidWorldException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidWorldException(Throwable cause) {
    super(cause);
  }
}

package com.remotecraft.app.infrastructure.exception;

public class InvalidServerException extends Exception {

  public InvalidServerException() {
    super();
  }

  public InvalidServerException(String message) {
    super(message);
  }

  public InvalidServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidServerException(Throwable cause) {
    super(cause);
  }
}

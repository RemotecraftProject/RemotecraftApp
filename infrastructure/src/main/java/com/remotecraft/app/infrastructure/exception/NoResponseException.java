package com.remotecraft.app.infrastructure.exception;

public class NoResponseException extends Exception {

  public NoResponseException() {
    super();
  }

  public NoResponseException(String message) {
    super(message);
  }

  public NoResponseException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoResponseException(Throwable cause) {
    super(cause);
  }
}

package com.remotecraft.app.infrastructure.exception;

public class NoInternetConnectionException extends Exception {

  public NoInternetConnectionException() {
    super();
  }

  public NoInternetConnectionException(String message) {
    super(message);
  }

  public NoInternetConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoInternetConnectionException(Throwable cause) {
    super(cause);
  }
}

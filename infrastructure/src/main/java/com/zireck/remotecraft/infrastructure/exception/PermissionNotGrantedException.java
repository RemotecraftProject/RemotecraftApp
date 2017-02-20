package com.zireck.remotecraft.infrastructure.exception;

public class PermissionNotGrantedException extends Exception {

  public PermissionNotGrantedException() {
  }

  public PermissionNotGrantedException(String message) {
    super(message);
  }

  public PermissionNotGrantedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PermissionNotGrantedException(Throwable cause) {
    super(cause);
  }
}

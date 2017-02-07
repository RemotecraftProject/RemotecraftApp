package com.zireck.remotecraft.infrastructure.exception;

import com.zireck.remotecraft.domain.exception.ErrorBundle;

public class InfrastructureErrorBundle implements ErrorBundle {

  private final Exception exception;

  public InfrastructureErrorBundle(Exception exception) {
    this.exception = exception;
  }

  @Override public Exception getException() {
    return exception;
  }

  @Override public String getErrorMessage() {
    String errorMessage = "";
    if (exception != null && exception.getMessage() != null) {
      errorMessage = exception.getMessage();
    }

    return errorMessage;
  }
}

package com.remotecraft.app.domain.exception;

public interface ErrorBundle {
  Exception getException();
  String getErrorMessage();
}

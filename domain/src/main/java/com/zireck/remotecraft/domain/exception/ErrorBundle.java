package com.zireck.remotecraft.domain.exception;

public interface ErrorBundle {
  Exception getException();
  String getErrorMessage();
}

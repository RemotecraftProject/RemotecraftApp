package com.remotecraft.app.infrastructure.validation;

public interface Validator<T> {
  boolean isValid(T data);
}

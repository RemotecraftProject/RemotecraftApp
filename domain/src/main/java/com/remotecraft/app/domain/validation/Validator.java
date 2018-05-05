package com.remotecraft.app.domain.validation;

public interface Validator<T> {
  boolean isValid(T data);
}

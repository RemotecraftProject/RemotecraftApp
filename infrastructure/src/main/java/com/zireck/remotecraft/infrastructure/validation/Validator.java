package com.zireck.remotecraft.infrastructure.validation;

public interface Validator<T> {
  boolean isValid(T data);
}

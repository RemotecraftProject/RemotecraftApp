package com.zireck.remotecraft.domain.validation;

public interface Validator<T> {
  boolean isValid(T data);
}

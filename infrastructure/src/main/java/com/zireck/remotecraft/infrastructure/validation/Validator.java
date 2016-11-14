package com.zireck.remotecraft.infrastructure.validation;

import java.util.List;

public interface Validator<T> {
  boolean isValid(T value);
  List<InvalidData> getInvalidData();
}

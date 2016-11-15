package com.zireck.remotecraft.infrastructure.validation;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import java.util.List;

public interface MessageValidator<T> {
  boolean isValid(Message message);
  T cast(Message message);
  List<InvalidData> getInvalidData();
}

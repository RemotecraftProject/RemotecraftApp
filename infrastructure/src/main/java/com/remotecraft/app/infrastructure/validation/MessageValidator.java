package com.remotecraft.app.infrastructure.validation;

import com.remotecraft.app.infrastructure.protocol.base.Message;
import java.util.List;

public interface MessageValidator<T> extends Validator<Message> {
  boolean isValid(Message message);
  T cast(Message message);
  List<InvalidServerData> getInvalidServerData();
}

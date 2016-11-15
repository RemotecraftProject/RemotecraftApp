package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import java.lang.reflect.Type;
import javax.inject.Inject;

public class MessageJsonMapper {

  private final Gson gson;

  @Inject
  public MessageJsonMapper(Gson gson) {
    this.gson = gson;
  }

  public Message transformMessage(String messageJsonResponse) {
    try {
      Type messageType = new TypeToken<Message>(){}.getType();
      Message message = gson.fromJson(messageJsonResponse, messageType);

      return message;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }
}

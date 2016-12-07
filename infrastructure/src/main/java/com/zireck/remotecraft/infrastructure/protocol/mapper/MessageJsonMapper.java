package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.google.gson.JsonSyntaxException;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import javax.inject.Inject;

public class MessageJsonMapper {

  private final JsonSerializer jsonSerializer;

  @Inject public MessageJsonMapper(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  public Message transformMessage(String messageJsonResponse) {
    try {
      Message message = jsonSerializer.fromJson(messageJsonResponse, Message.class);

      return message;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }
}

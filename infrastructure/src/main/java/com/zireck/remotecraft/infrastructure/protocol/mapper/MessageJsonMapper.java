package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import javax.inject.Inject;

public class MessageJsonMapper {

  private final JsonSerializer jsonSerializer;

  @Inject public MessageJsonMapper(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  public Message transformMessage(String messageJsonResponse) {
    return jsonSerializer.fromJson(messageJsonResponse, Message.class);
  }

  public String transformMessage(Message message) {
    return jsonSerializer.toJson(message);
  }
}

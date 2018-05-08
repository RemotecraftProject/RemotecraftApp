package com.remotecraft.app.infrastructure.protocol.mapper;

import com.remotecraft.app.infrastructure.protocol.base.Message;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import javax.inject.Inject;

public class MessageJsonMapper {

  private final JsonSerializer jsonSerializer;

  @Inject
  public MessageJsonMapper(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  public Message transformMessage(String messageJsonResponse) {
    return jsonSerializer.fromJson(messageJsonResponse, Message.class);
  }

  public String transformMessage(Message message) {
    return jsonSerializer.toJson(message);
  }
}

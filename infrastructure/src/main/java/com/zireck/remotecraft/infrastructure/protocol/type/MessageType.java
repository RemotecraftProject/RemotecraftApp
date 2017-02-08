package com.zireck.remotecraft.infrastructure.protocol.type;

public enum MessageType {
  COMMAND ("command"),
  INFO ("info"),
  SERVER ("server"),
  ERROR ("error");

  private final String messageType;

  private MessageType(String messageType) {
    this.messageType = messageType;
  }

  @Override public String toString() {
    return messageType;
  }
}

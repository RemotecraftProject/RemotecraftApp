package com.remotecraft.app.infrastructure.protocol.messages;

import com.remotecraft.app.infrastructure.protocol.base.Message;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import com.remotecraft.app.infrastructure.protocol.enumeration.MessageType;

public class ServerMessage extends Message {

  private ServerMessage(Builder builder) {
    this.isSuccess = true;
    this.type = MessageType.SERVER.toString();
    this.serverProtocol = builder.serverProtocol;
  }

  @Override
  public boolean isServer() {
    return true;
  }

  public static class Builder {
    private ServerProtocol serverProtocol;

    public Builder() {

    }

    public ServerMessage build() {
      return new ServerMessage(this);
    }

    public Builder with(ServerProtocol serverProtocol) {
      this.serverProtocol = serverProtocol;
      return this;
    }
  }
}

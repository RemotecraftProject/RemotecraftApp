package com.zireck.remotecraft.server.mock.protocol.messages;

import com.zireck.remotecraft.server.mock.protocol.MessageType;
import com.zireck.remotecraft.server.mock.protocol.base.Message;
import com.zireck.remotecraft.server.mock.protocol.data.ServerProtocol;

public class ServerMessage extends Message {

  private ServerMessage(Builder builder) {
    this.isSuccess = builder.isSuccess;
    this.type = MessageType.SERVER.toString();
    this.serverProtocol = builder.serverProtocol;
  }

  public static class Builder {
    private boolean isSuccess = true;
    private ServerProtocol serverProtocol;

    public Builder() {

    }

    public ServerMessage build() {
      return new ServerMessage(this);
    }

    public Builder success(boolean isSuccess) {
      this.isSuccess = isSuccess;
      return this;
    }

    public Builder with(ServerProtocol serverProtocol) {
      this.serverProtocol = serverProtocol;
      return this;
    }
  }
}

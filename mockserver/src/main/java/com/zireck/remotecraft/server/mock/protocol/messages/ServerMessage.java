package com.zireck.remotecraft.server.mock.protocol.messages;

import com.zireck.remotecraft.server.mock.protocol.MessageType;
import com.zireck.remotecraft.server.mock.protocol.base.Message;
import com.zireck.remotecraft.server.mock.protocol.data.Server;

public class ServerMessage extends Message {

  private ServerMessage() {
    this.type = MessageType.SERVER.toString();
  }

  public static class Builder {
    private ServerMessage serverMessage;

    public Builder() {
      serverMessage = new ServerMessage();
    }

    public ServerMessage build() {
      return serverMessage;
    }

    public Builder success(boolean success) {
      serverMessage.isSuccess = success;
      return this;
    }

    public Builder server(Server server) {
      serverMessage.server = server;
      return this;
    }
  }
}

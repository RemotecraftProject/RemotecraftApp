package com.zireck.remotecraft.server.mock.protocol.messages;

import com.zireck.remotecraft.server.mock.protocol.MessageType;
import com.zireck.remotecraft.server.mock.protocol.base.Message;
import com.zireck.remotecraft.server.mock.protocol.data.CommandProtocol;

public final class CommandMessage extends Message {

  private CommandMessage() {
    this.type = MessageType.COMMAND.toString();
  }

  public boolean isCommand() {
    return commandProtocol != null;
  }

  public CommandProtocol getCommand() {
    return commandProtocol;
  }

  public static class Builder {
    private CommandMessage commandMessage;

    public Builder() {
      commandMessage = new CommandMessage();
    }

    public CommandMessage build() {
      return commandMessage;
    }

    public Builder success(boolean success) {
      commandMessage.isSuccess = success;
      return this;
    }

    public Builder command(CommandProtocol commandProtocol) {
      commandMessage.commandProtocol = commandProtocol;
      return this;
    }
  }
}

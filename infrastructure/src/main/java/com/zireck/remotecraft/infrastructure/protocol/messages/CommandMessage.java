package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.base.type.CommandProtocol;
import com.zireck.remotecraft.infrastructure.protocol.enumeration.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;

public class CommandMessage extends Message {

  private CommandMessage(Builder builder) {
    this.isSuccess = true;
    this.type = MessageType.COMMAND.toString();
    this.commandProtocol = builder.commandProtocol;
  }

  @Override public boolean isCommand() {
    return true;
  }

  public static class Builder {
    private CommandProtocol commandProtocol;

    public Builder() {

    }

    public CommandMessage build() {
      return new CommandMessage(this);
    }

    public Builder with(CommandProtocol commandProtocol) {
      this.commandProtocol = commandProtocol;
      return this;
    }
  }
}

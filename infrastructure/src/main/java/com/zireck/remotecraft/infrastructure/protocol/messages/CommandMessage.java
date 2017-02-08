package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.type.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.Command;

public final class CommandMessage extends Message {

  private CommandMessage(Builder builder) {
    this.isSuccess = true;
    this.type = MessageType.COMMAND.toString();
    this.command = builder.command;
  }

  @Override public boolean isCommand() {
    return true;
  }

  public static class Builder {
    private Command command;

    public Builder() {

    }

    public CommandMessage build() {
      return new CommandMessage(this);
    }

    public Builder with(Command command) {
      this.command = command;
      return this;
    }
  }
}

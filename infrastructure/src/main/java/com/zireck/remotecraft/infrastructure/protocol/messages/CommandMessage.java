package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;

public final class CommandMessage extends Message {

  private CommandMessage() {
    this.type = MessageType.COMMAND.toString();
  }

  public boolean isCommand() {
    return command != null;
  }

  public Command getCommand() {
    return command;
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

    public Builder command(Command command) {
      commandMessage.command = command;
      return this;
    }
  }
}

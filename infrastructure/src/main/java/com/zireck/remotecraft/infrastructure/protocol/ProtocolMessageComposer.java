package com.zireck.remotecraft.infrastructure.protocol;

import com.zireck.remotecraft.infrastructure.protocol.base.Command;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.protocol.type.CommandType;

public class ProtocolMessageComposer {

  public ProtocolMessageComposer() {

  }

  public CommandMessage composeGetServerInfoCommand() {
    return new CommandMessage.Builder()
        .with(new Command(CommandType.GET_SERVER_INFO))
        .build();
  }
}

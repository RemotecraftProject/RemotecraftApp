package com.remotecraft.app.infrastructure.protocol;

import com.remotecraft.app.infrastructure.protocol.base.type.CommandProtocol;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import com.remotecraft.app.infrastructure.protocol.messages.CommandMessage;
import com.remotecraft.app.infrastructure.protocol.enumeration.CommandType;
import com.remotecraft.app.infrastructure.protocol.messages.ServerMessage;

public class ProtocolMessageComposer {

  public ProtocolMessageComposer() {

  }

  public CommandMessage composeGetServerInfoCommand() {
    return new CommandMessage.Builder()
        .with(new CommandProtocol(CommandType.GET_SERVER_INFO))
        .build();
  }

  public ServerMessage composeServerMessage(ServerProtocol serverProtocol) {
    return new ServerMessage.Builder()
        .with(serverProtocol)
        .build();
  }
}

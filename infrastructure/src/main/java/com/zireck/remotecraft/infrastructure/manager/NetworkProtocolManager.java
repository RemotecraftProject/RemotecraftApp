package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.protocol.CommandType;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;

public class NetworkProtocolManager {

  private JsonSerializer jsonSerializer;

  public NetworkProtocolManager(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  public String composeServerSearchRequest() {
    CommandMessage getServerInfo = new CommandMessage.Builder()
        .success(true)
        .command(new Command(CommandType.GET_WORLD_INFO))
        .build();
    return jsonSerializer.toJson(getServerInfo);
  }
}

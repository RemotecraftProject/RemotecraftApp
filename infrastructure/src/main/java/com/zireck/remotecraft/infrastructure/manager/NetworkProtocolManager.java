package com.zireck.remotecraft.infrastructure.manager;

import com.google.gson.Gson;
import com.zireck.remotecraft.infrastructure.protocol.CommandType;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;

public class NetworkProtocolManager {

  private Gson gson;

  public NetworkProtocolManager(Gson gson) {
    this.gson = gson;
  }

  public String getDiscoveryRequest() {
    CommandMessage getWorldInfo = new CommandMessage.Builder().success(true)
        .command(new Command(CommandType.GET_WORLD_INFO))
        .build();
    return gson.toJson(getWorldInfo);
  }
}

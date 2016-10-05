package com.zireck.remotecraft.infrastructure.manager;

import com.google.gson.Gson;
import com.zireck.remotecraft.infrastructure.protocol.Command;
import com.zireck.remotecraft.infrastructure.protocol.request.DiscoveryRequest;

public class NetworkProtocolManager {

  private Gson gson;

  public NetworkProtocolManager(Gson gson) {
    this.gson = gson;
  }

  public String getDiscoveryRequest() {
    DiscoveryRequest discoveryRequest = new DiscoveryRequest("remotecraft", "request", new Command("discovery", 0));
    return gson.toJson(discoveryRequest);
  }
}

package com.zireck.remotecraft.infrastructure.protocol.request;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.BaseJson;
import com.zireck.remotecraft.infrastructure.protocol.Command;

public final class DiscoveryRequest extends BaseJson {
  @SerializedName("command") private Command command;

  public DiscoveryRequest(String app, String type, Command command) {
    this.app = app;
    this.type = type;
    this.command = command;
  }
}

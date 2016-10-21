package com.zireck.remotecraft.infrastructure.protocol.request;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;

public final class DiscoveryRequest extends Message {

  @SerializedName("command") private Command command;

  public DiscoveryRequest(Command command) {
    this.command = command;
  }
}

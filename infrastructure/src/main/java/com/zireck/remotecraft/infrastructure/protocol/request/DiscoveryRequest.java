package com.zireck.remotecraft.infrastructure.protocol.request;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.BaseMessage;
import com.zireck.remotecraft.infrastructure.protocol.Command;

public final class DiscoveryRequest extends BaseMessage {

  @SerializedName("command") private Command command;

  public DiscoveryRequest(Command command) {
    this.command = command;
  }
}

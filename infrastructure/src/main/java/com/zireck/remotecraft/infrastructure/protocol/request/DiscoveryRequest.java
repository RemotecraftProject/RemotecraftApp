package com.zireck.remotecraft.infrastructure.protocol.request;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;

public final class DiscoveryRequest extends Message {

  public DiscoveryRequest(Command command) {
    this.command = command;
  }
}

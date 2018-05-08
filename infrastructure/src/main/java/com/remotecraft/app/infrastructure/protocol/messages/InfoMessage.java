package com.remotecraft.app.infrastructure.protocol.messages;

import com.remotecraft.app.infrastructure.protocol.base.type.InfoProtocol;
import com.remotecraft.app.infrastructure.protocol.enumeration.MessageType;
import com.remotecraft.app.infrastructure.protocol.base.Message;

public class InfoMessage extends Message {

  private InfoMessage(Builder builder) {
    this.isSuccess = true;
    this.type = MessageType.INFO.toString();
    this.infoProtocol = builder.infoProtocol;
  }

  @Override
  public boolean isInfo() {
    return true;
  }

  public static class Builder {
    private InfoProtocol infoProtocol;

    public Builder() {

    }

    public InfoMessage build() {
      return new InfoMessage(this);
    }

    public Builder with(InfoProtocol infoProtocol) {
      this.infoProtocol = infoProtocol;
      return this;
    }
  }
}

package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.type.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.Info;

public final class InfoMessage extends Message {

  private InfoMessage(Builder builder) {
    this.isSuccess = true;
    this.type = MessageType.INFO.toString();
    this.info = builder.info;
  }

  @Override public boolean isInfo() {
    return true;
  }

  public static class Builder {
    private Info info;

    public Builder() {

    }

    public InfoMessage build() {
      return new InfoMessage(this);
    }

    public Builder with(Info info) {
      this.info = info;
      return this;
    }
  }
}

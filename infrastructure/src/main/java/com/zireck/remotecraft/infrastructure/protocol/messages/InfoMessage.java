package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Info;

public final class InfoMessage extends Message {

  private InfoMessage() {
    this.type = MessageType.INFO.toString();
  }

  public boolean isInfo() {
    return info != null;
  }

  public Info getInfo() {
    return info;
  }

  public static class Builder {
    private InfoMessage infoMessage;

    public Builder() {
      infoMessage = new InfoMessage();
    }

    public InfoMessage build() {
      return infoMessage;
    }

    public Builder success(boolean success) {
      infoMessage.isSuccess = success;
      return this;
    }

    public Builder info(Info info) {
      infoMessage.info = info;
      return this;
    }
  }
}

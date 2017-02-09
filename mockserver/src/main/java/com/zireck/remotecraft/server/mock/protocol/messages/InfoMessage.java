package com.zireck.remotecraft.server.mock.protocol.messages;

import com.zireck.remotecraft.server.mock.protocol.MessageType;
import com.zireck.remotecraft.server.mock.protocol.base.Message;
import com.zireck.remotecraft.server.mock.protocol.data.InfoProtocol;

public final class InfoMessage extends Message {

  private InfoMessage() {
    this.type = MessageType.INFO.toString();
  }

  public boolean isInfo() {
    return infoProtocol != null;
  }

  public InfoProtocol getInfo() {
    return infoProtocol;
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

    public Builder info(InfoProtocol infoProtocol) {
      infoMessage.infoProtocol = infoProtocol;
      return this;
    }
  }
}

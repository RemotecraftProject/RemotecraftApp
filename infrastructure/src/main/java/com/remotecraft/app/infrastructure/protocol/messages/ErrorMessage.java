package com.remotecraft.app.infrastructure.protocol.messages;

import com.remotecraft.app.infrastructure.protocol.base.type.ErrorProtocol;
import com.remotecraft.app.infrastructure.protocol.enumeration.MessageType;
import com.remotecraft.app.infrastructure.protocol.base.Message;

public class ErrorMessage extends Message {

  private ErrorMessage(Builder builder) {
    this.isSuccess = false;
    this.type = MessageType.ERROR.toString();
    this.errorProtocol = builder.errorProtocol;
  }

  @Override public boolean isSuccess() {
    return false;
  }

  public static class Builder {
    private ErrorProtocol errorProtocol;

    public Builder() {
      this.errorProtocol = new ErrorProtocol();
    }

    public ErrorMessage build() {
      return new ErrorMessage(this);
    }

    public Builder with(int code) {
      this.errorProtocol.setCode(code);
      return this;
    }

    public Builder and(String message) {
      this.errorProtocol.setMessage(message);
      return this;
    }
  }
}

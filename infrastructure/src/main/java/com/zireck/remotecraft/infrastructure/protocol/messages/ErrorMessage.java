package com.zireck.remotecraft.infrastructure.protocol.messages;

import com.zireck.remotecraft.infrastructure.protocol.type.MessageType;
import com.zireck.remotecraft.infrastructure.protocol.base.Error;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;

public final class ErrorMessage extends Message {

  private ErrorMessage(Builder builder) {
    this.isSuccess = false;
    this.type = MessageType.ERROR.toString();
    this.error = builder.error;
  }

  @Override public boolean isSuccess() {
    return false;
  }

  public static class Builder {
    private Error error;

    public Builder() {
      this.error = new Error();
    }

    public ErrorMessage build() {
      return new ErrorMessage(this);
    }

    public Builder with(int code) {
      this.error.setCode(code);
      return this;
    }

    public Builder and(String message) {
      this.error.setMessage(message);
      return this;
    }
  }
}

package com.zireck.remotecraft.infrastructure.protocol.base.type;

import com.google.gson.annotations.SerializedName;

public class ErrorProtocol {

  @SerializedName("code") private int code;
  @SerializedName("message") private String message;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

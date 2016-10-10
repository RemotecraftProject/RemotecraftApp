package com.zireck.remotecraft.server.mock.protocol;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

  @SerializedName("code") private int code;
  @SerializedName("message") private String message;

  public int getCode() {
    return code;
  }

  void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  void setMessage(String message) {
    this.message = message;
  }
}

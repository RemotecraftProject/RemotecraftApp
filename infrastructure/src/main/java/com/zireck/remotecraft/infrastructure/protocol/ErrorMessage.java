package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

  @SerializedName("code") private int code;
  @SerializedName("message") private String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}

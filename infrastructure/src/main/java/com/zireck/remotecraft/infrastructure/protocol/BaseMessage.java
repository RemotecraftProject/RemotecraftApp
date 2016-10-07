package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;

public abstract class BaseMessage {

  @SerializedName("success") protected boolean isSuccess = true;
  @SerializedName("data") protected DataMessage data = null;
  @SerializedName("error") protected ErrorMessage error = null;

  public boolean isSuccess() {
    return isSuccess;
  }

  public DataMessage getdata() {
    return data;
  }

  public ErrorMessage getError() {
    return error;
  }
}

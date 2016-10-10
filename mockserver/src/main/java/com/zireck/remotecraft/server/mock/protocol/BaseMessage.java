package com.zireck.remotecraft.server.mock.protocol;

import com.google.gson.annotations.SerializedName;

public class BaseMessage<T extends BaseData> {

  @SerializedName("success") private boolean isSuccess = true;
  @SerializedName("data") protected T data = null;
  @SerializedName("error") private ErrorMessage error = null;

  public boolean isSuccess() {
    return isSuccess;
  }

  void setSuccess(boolean success) {
    isSuccess = success;
  }

  public T getData() {
    return data;
  }

  void setData(T data) {
    this.data = data;
  }

  public ErrorMessage getError() {
    return error;
  }

  void setError(ErrorMessage error) {
    this.error = error;
  }
}

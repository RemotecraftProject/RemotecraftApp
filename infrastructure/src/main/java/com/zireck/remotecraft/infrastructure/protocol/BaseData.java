package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;

public abstract class BaseData {

  @SerializedName("type") private String type;

  public boolean isRequest() {
    return type.equalsIgnoreCase(NetworkProtocolHelper.MESSAGE_TYPE_REQUEST);
  }

  void setRequest() {
    type = NetworkProtocolHelper.MESSAGE_TYPE_REQUEST;
  }

  public boolean isResponse() {
    return type.equalsIgnoreCase(NetworkProtocolHelper.MESSAGE_TYPE_RESPONSE);
  }

  void setResponse() {
    type = NetworkProtocolHelper.MESSAGE_TYPE_RESPONSE;
  }
}

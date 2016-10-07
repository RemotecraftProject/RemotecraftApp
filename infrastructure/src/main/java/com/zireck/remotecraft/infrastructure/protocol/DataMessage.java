package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;

public class DataMessage {

  @SerializedName("type") private String type;
  // TODO: add more fields

  public boolean isRequest() {
    return type.equalsIgnoreCase(NetworkProtocolHelper.MESSAGE_TYPE_REQUEST);
  }

  public boolean isResponse() {
    return type.equalsIgnoreCase(NetworkProtocolHelper.MESSAGE_TYPE_RESPONSE);
  }
}

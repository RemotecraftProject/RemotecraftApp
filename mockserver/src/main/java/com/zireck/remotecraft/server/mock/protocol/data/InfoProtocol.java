package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;

public class InfoProtocol extends ParameterizedData {

  @SerializedName("from") private String from;
  @SerializedName("description") private String description;

  public InfoProtocol(String from, String description, int argumentsCount, String... arguments) {
    super(argumentsCount, arguments);
    this.from = from;
    this.description = description;
  }

  public String getFrom() {
    return from;
  }

  public String getDescription() {
    return description;
  }
}

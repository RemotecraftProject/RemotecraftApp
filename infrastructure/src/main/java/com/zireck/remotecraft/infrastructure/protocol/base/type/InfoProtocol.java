package com.zireck.remotecraft.infrastructure.protocol.base.type;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.base.ParameterizedData;

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

package com.zireck.remotecraft.infrastructure.protocol.data;

import com.google.gson.annotations.SerializedName;

public class Info extends ParameterizedData {

  @SerializedName("from") private String from;
  @SerializedName("description") private String description;

  public Info(String from, String description, int argumentsCount, String... arguments) {
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

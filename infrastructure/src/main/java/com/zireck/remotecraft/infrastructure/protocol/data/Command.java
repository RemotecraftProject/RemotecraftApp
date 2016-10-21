package com.zireck.remotecraft.infrastructure.protocol.data;

import com.google.gson.annotations.SerializedName;

public final class Command extends ParameterizedData {

  @SerializedName("name") private String name;

  public Command(String name, int argumentsCount, String... arguments) {
    super(argumentsCount, arguments);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

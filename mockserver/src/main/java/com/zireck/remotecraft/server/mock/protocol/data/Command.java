package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.server.mock.protocol.CommandType;

public final class Command extends ParameterizedData {

  @SerializedName("name") private String name;

  public Command(CommandType commandType) {
    super(0, null);
    this.name = commandType.toString();
  }

  public Command(CommandType commandType, int argumentsCount, String... arguments) {
    super(argumentsCount, arguments);
    this.name = commandType.toString();
  }

  public String getName() {
    return name;
  }

  public boolean equals(CommandType commandType) {
    return name.equalsIgnoreCase(commandType.toString());
  }
}

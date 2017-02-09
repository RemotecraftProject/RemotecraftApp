package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.server.mock.protocol.CommandType;

public final class CommandProtocol extends ParameterizedData {

  @SerializedName("name") private String name;

  public CommandProtocol(CommandType commandType) {
    super(0, null);
    this.name = commandType.toString();
  }

  public CommandProtocol(CommandType commandType, int argumentsCount, String... arguments) {
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

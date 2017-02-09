package com.zireck.remotecraft.infrastructure.protocol.base.type;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.base.ParameterizedData;
import com.zireck.remotecraft.infrastructure.protocol.enumeration.CommandType;

public final class CommandProtocol extends ParameterizedData {

  @SerializedName("name") private String name;

  public CommandProtocol(CommandType commandType) {
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

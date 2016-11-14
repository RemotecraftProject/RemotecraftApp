package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;

public abstract class ParameterizedData {

  @SerializedName("argument_count") private Integer argumentsCount;
  @SerializedName("arguments") private List<String> arguments;

  public ParameterizedData(Integer argumentsCount, String... arguments) {
    this.argumentsCount = argumentsCount;
    if (!(argumentsCount <= 0 || arguments.length <= 0)) {
      this.arguments = Arrays.asList(arguments);
    }
  }

  public Integer getArgumentsCount() {
    return argumentsCount;
  }

  public List<String> getArguments() {
    return arguments;
  }
}

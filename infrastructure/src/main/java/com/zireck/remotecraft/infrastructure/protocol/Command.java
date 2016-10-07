package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;

public final class Command {

  @SerializedName("name") private String name;
  @SerializedName("argument_count") private Integer argumentCount;
  @SerializedName("arguments") private List<String> arguments;

  public Command(String name, int argumentCount, String... arguments) {
    this.name = name;
    this.argumentCount = argumentCount;
    this.arguments = Arrays.asList(arguments);
  }

  public String getName() {
    return name;
  }

  public Integer getArgumentCount() {
    return argumentCount;
  }

  public List<String> getArguments() {
    return arguments;
  }
}

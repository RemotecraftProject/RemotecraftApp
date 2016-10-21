package com.zireck.remotecraft.infrastructure.protocol.base;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.data.Command;
import com.zireck.remotecraft.infrastructure.protocol.data.Info;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;

public class Message {

  @SerializedName("success") private boolean isSuccess;
  @SerializedName("type") private String type;
  @SerializedName("command") private Command command;
  @SerializedName("info") private Info info;
  @SerializedName("server") private Server server;
  @SerializedName("error") private Error error;

  public boolean isSuccess() {
    return isSuccess;
  }

  public String getType() {
    return type;
  }

  public boolean isCommand() {
    return command != null;
  }

  public Command getCommand() {
    return command;
  }

  public boolean isInfo() {
    return info != null;
  }

  public Info getInfo() {
    return info;
  }

  public boolean isServer() {
    return server != null;
  }

  public Server getServer() {
    return server;
  }

  public Error getError() {
    return error;
  }
}

package com.zireck.remotecraft.server.mock.protocol.base;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.server.mock.protocol.data.Command;
import com.zireck.remotecraft.server.mock.protocol.data.Info;
import com.zireck.remotecraft.server.mock.protocol.data.Server;

public class Message {

  @SerializedName("success") protected boolean isSuccess;
  @SerializedName("type") protected String type;
  @SerializedName("command") protected Command command;
  @SerializedName("info") protected Info info;
  @SerializedName("server") protected Server server;
  @SerializedName("error") protected Error error;

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

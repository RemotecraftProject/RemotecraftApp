package com.zireck.remotecraft.server.mock.protocol.base;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.server.mock.protocol.data.CommandProtocol;
import com.zireck.remotecraft.server.mock.protocol.data.InfoProtocol;
import com.zireck.remotecraft.server.mock.protocol.data.ServerProtocol;

public class Message {

  @SerializedName("success") protected boolean isSuccess;
  @SerializedName("type") protected String type;
  @SerializedName("command") protected CommandProtocol commandProtocol;
  @SerializedName("info") protected InfoProtocol infoProtocol;
  @SerializedName("server") protected ServerProtocol serverProtocol;
  @SerializedName("error") protected ErrorProtocol errorProtocol;

  public boolean isSuccess() {
    return isSuccess;
  }

  public String getType() {
    return type;
  }

  public boolean isCommand() {
    return commandProtocol != null;
  }

  public CommandProtocol getCommand() {
    return commandProtocol;
  }

  public boolean isInfo() {
    return infoProtocol != null;
  }

  public InfoProtocol getInfo() {
    return infoProtocol;
  }

  public boolean isServer() {
    return serverProtocol != null;
  }

  public ServerProtocol getServer() {
    return serverProtocol;
  }

  public ErrorProtocol getError() {
    return errorProtocol;
  }
}

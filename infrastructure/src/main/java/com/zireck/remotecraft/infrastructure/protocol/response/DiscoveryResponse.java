package com.zireck.remotecraft.infrastructure.protocol.response;

import com.google.gson.annotations.SerializedName;
import com.zireck.remotecraft.infrastructure.protocol.BaseJson;

public class DiscoveryResponse extends BaseJson {
  @SerializedName("ssid") private String ssid;
  @SerializedName("ip") private String ip;
  @SerializedName("version") private String version;
  @SerializedName("world_name") private String worldName;
  @SerializedName("player_name") private String playerName;

  public DiscoveryResponse(String app, String type) {
    this.app = app;
    this.type = type;
  }

  public String getSsid() {
    return ssid;
  }

  public String getIp() {
    return ip;
  }

  public String getVersion() {
    return version;
  }

  public String getWorldName() {
    return worldName;
  }

  public String getPlayerName() {
    return playerName;
  }
}

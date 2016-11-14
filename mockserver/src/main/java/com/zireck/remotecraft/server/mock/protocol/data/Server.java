package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;

public class Server {

  @SerializedName("ssid") private String ssid;
  @SerializedName("ip") private String ip;
  @SerializedName("version") private String version;
  @SerializedName("seed") private String seed;
  @SerializedName("world_name") private String worldName;
  @SerializedName("player_name") private String playerName;

  public Server(String ssid, String ip, String version, String seed, String worldName,
      String playerName) {
    this.ssid = ssid;
    this.ip = ip;
    this.version = version;
    this.seed = seed;
    this.worldName = worldName;
    this.playerName = playerName;
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

  public String getSeed() {
    return seed;
  }

  public String getWorldName() {
    return worldName;
  }

  public String getPlayerName() {
    return playerName;
  }
}

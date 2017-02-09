package com.zireck.remotecraft.server.mock.protocol.data;

import com.google.gson.annotations.SerializedName;

public class ServerProtocol {

  @SerializedName("ssid") private String ssid;
  @SerializedName("ip") private String ip;
  @SerializedName("hostname") private String hostname;
  @SerializedName("os") private String os;
  @SerializedName("version") private String version;
  @SerializedName("seed") private String seed;
  @SerializedName("world_name") private String worldName;
  @SerializedName("player_name") private String playerName;

  public ServerProtocol(String ssid, String ip, String hostname, String os, String version,
      String seed, String worldName, String playerName) {
    this.ssid = ssid;
    this.ip = ip;
    this.hostname = hostname;
    this.os = os;
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

  public String getHostname() {
    return hostname;
  }

  public String getOs() {
    return os;
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

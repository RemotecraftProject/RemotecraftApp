package com.zireck.remotecraft.server.mock.protocol;

import com.google.gson.annotations.SerializedName;

public class DiscoveryData extends BaseData {

  @SerializedName("ssid") private String ssid;
  @SerializedName("ip") private String ip;
  @SerializedName("version") private String version;
  @SerializedName("seed") private String seed;
  @SerializedName("world_name") private String worldName;
  @SerializedName("player_name") private String playerName;

  public DiscoveryData() {

  }

  public String getSsid() {
    return ssid;
  }

  public DiscoveryData setSsid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  public String getIp() {
    return ip;
  }

  public DiscoveryData setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public String getVersion() {
    return version;
  }

  public DiscoveryData setVersion(String version) {
    this.version = version;
    return this;
  }

  public String getSeed() {
    return seed;
  }

  public DiscoveryData setSeed(String seed) {
    this.seed = seed;
    return this;
  }

  public String getWorldName() {
    return worldName;
  }

  public DiscoveryData setWorldName(String worldName) {
    this.worldName = worldName;
    return this;
  }

  public String getPlayerName() {
    return playerName;
  }

  public DiscoveryData setPlayerName(String playerName) {
    this.playerName = playerName;
    return this;
  }
}
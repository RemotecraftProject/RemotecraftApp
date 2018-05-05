package com.remotecraft.app.infrastructure.protocol.base.type;

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
  @SerializedName("encoded_world_image") private String encodedWorldImage;

  private ServerProtocol(Builder builder) {
    this.ssid = builder.ssid;
    this.ip = builder.ip;
    this.hostname = builder.hostname;
    this.os = builder.os;
    this.version = builder.version;
    this.seed = builder.seed;
    this.worldName = builder.worldName;
    this.playerName = builder.playerName;
    this.encodedWorldImage = builder.encodedWorldImage;
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

  public String getEncodedWorldImage() {
    return encodedWorldImage;
  }

  public static class Builder {
    private String ssid;
    private String ip;
    private String hostname;
    private String os;
    private String version;
    private String seed;
    private String worldName;
    private String playerName;
    private String encodedWorldImage;

    public Builder() {

    }

    public ServerProtocol build() {
      return new ServerProtocol(this);
    }

    public Builder ssid(String ssid) {
      this.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      this.ip = ip;
      return this;
    }

    public Builder hostname(String hostname) {
      this.hostname = hostname;
      return this;
    }

    public Builder os(String os) {
      this.os = os;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder seed(String seed) {
      this.seed = seed;
      return this;
    }

    public Builder worldName(String worldName) {
      this.worldName = worldName;
      return this;
    }

    public Builder playerName(String playerName) {
      this.playerName = playerName;
      return this;
    }

    public Builder encodedWorldImage(String encodedWorldImage) {
      this.encodedWorldImage = encodedWorldImage;
      return this;
    }
  }
}

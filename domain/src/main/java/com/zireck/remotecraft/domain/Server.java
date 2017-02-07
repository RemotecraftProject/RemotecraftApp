package com.zireck.remotecraft.domain;

public class Server {

  private final String ssid;
  private final String ip;
  private final String version;
  private final String seed;
  private final String worldName;
  private final String playerName;

  private Server(Builder builder) {
    this.ssid = builder.ssid;
    this.ip = builder.ip;
    this.version = builder.version;
    this.seed = builder.seed;
    this.worldName = builder.worldName;
    this.playerName = builder.playerName;
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

  public static class Builder {

    private String ssid;
    private String ip;
    private String version;
    private String seed;
    private String worldName;
    private String playerName;

    public Builder() {

    }

    public Server build() {
      return new Server(this);
    }

    public Builder ssid(String ssid) {
      this.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      this.ip = ip;
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
  }
}

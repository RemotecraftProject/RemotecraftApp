package com.zireck.remotecraft.infrastructure.entity;

public class ServerEntity {

  private final String ssid;
  private final String ip;
  private final String hostname;
  private final String os;
  private final String version;
  private final String seed;
  private final String worldName;
  private final String playerName;

  private ServerEntity(Builder builder) {
    this.ssid = builder.ssid;
    this.ip = builder.ip;
    this.hostname = builder.hostname;
    this.os = builder.os;
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

  public static class Builder {
    private String ssid;
    private String ip;
    private String hostname;
    private String os;
    private String version;
    private String seed;
    private String worldName;
    private String playerName;

    public Builder() {

    }

    public ServerEntity build() {
      return new ServerEntity(this);
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
  }
}

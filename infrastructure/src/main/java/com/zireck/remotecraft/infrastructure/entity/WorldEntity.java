package com.zireck.remotecraft.infrastructure.entity;

public final class WorldEntity {
  private String ssid;
  private String ip;
  private String version;
  private String seed;
  private String name;
  private String player;

  private WorldEntity() {

  }

  public static class Builder {
    private WorldEntity worldEntity;

    public Builder() {
      worldEntity = new WorldEntity();
    }

    public WorldEntity build() {
      return worldEntity;
    }

    public Builder ssid(String ssid) {
      worldEntity.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      worldEntity.ip = ip;
      return this;
    }

    public Builder version(String version) {
      worldEntity.version = version;
      return this;
    }

    public Builder seed(String seed) {
      worldEntity.seed = seed;
      return this;
    }

    public Builder name(String name) {
      worldEntity.name = name;
      return this;
    }

    public Builder player(String player) {
      worldEntity.player = player;
      return this;
    }
  }
}

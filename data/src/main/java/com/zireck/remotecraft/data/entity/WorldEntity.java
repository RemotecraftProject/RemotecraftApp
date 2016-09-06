package com.zireck.remotecraft.data.entity;

public final class WorldEntity {
  private String version;
  private String ssid;
  private String ip;
  private String name;
  private String player;

  private WorldEntity() {

  }

  public static class Builder {
    private WorldEntity worldEntity;

    Builder() {
      worldEntity = new WorldEntity();
    }

    public WorldEntity build() {
      return worldEntity;
    }

    public Builder version(String version) {
      worldEntity.version = version;
      return this;
    }

    public Builder ssid(String ssid) {
      worldEntity.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      worldEntity.ip = ip;
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

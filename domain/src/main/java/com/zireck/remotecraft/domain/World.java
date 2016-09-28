package com.zireck.remotecraft.domain;

public class World {
  private String version;
  private String ssid;
  private String ip;
  private String name;
  private String player;

  private World() {

  }

  public static class Builder {
    private World world;

    public Builder() {
      world = new World();
    }

    public World build() {
      return world;
    }

    public Builder version(String version) {
      world.version = version;
      return this;
    }

    public Builder ssid(String ssid) {
      world.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      world.ip = ip;
      return this;
    }

    public Builder name(String name) {
      world.name = name;
      return this;
    }

    public Builder player(String player) {
      world.player = player;
      return this;
    }
  }
}

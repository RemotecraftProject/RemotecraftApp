package com.zireck.remotecraft.infrastructure.entity;

public class NetworkAddressEntity {

  private final String ip;
  private final int port;

  private NetworkAddressEntity(Builder builder) {
    this.ip = builder.ip;
    this.port = builder.port;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public static class Builder {
    private String ip;
    private int port;

    public Builder() {

    }

    public NetworkAddressEntity build() {
      return new NetworkAddressEntity(this);
    }

    public Builder with(String ip) {
      this.ip = ip;
      return this;
    }

    public Builder and(int port) {
      this.port = port;
      return this;
    }
  }
}

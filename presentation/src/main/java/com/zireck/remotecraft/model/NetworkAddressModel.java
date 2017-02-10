package com.zireck.remotecraft.model;

public class NetworkAddressModel {

  private final String ip;
  private final String port;

  public NetworkAddressModel(Builder builder) {
    this.ip = builder.ip;
    this.port = builder.port;
  }

  public String getIp() {
    return ip;
  }

  public String getPort() {
    return port;
  }

  public static class Builder {
    private String ip;
    private String port;

    public Builder() {

    }

    public NetworkAddressModel build() {
      return new NetworkAddressModel(this);
    }

    public Builder with(String ip) {
      this.ip = ip;
      return this;
    }

    public Builder and(String port) {
      this.port = port;
      return this;
    }
  }
}

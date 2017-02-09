package com.zireck.remotecraft.infrastructure.provider;

public class ServerSearchSettings {

  private final int port;
  private final String broadcastAddress;
  private final int retryCount;
  private final int retryDelayMultiplier;
  private final int responseBufferSize;
  private final int timeout;

  private ServerSearchSettings(Builder builder) {
    this.port = builder.port;
    this.broadcastAddress = builder.broadcastAddress;
    this.retryCount = builder.retryCount;
    this.retryDelayMultiplier = builder.retryDelayMultiplier;
    this.responseBufferSize = builder.responseBufferSize;
    this.timeout = builder.timeout;
  }

  public int getPort() {
    return port;
  }

  public String getBroadcastAddress() {
    return broadcastAddress;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public int getRetryDelayMultiplier() {
    return retryDelayMultiplier;
  }

  public int getResponseBufferSize() {
    return responseBufferSize;
  }

  public int getTimeout() {
    return timeout;
  }

  public static class Builder {
    private int port = 9998;
    private String broadcastAddress = "255.255.255.255";
    private int retryCount = 5;
    private int retryDelayMultiplier = 1;
    private int responseBufferSize = 15000;
    private int timeout = 1000;

    public Builder() {

    }

    public ServerSearchSettings build() {
      return new ServerSearchSettings(this);
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder broadcastAddress(String broadcastAddress) {
      this.broadcastAddress = broadcastAddress;
      return this;
    }

    public Builder retryCount(int retryCount) {
      this.retryCount = retryCount;
      return this;
    }

    public Builder retryDelayMultiplier(int retryDelayMultiplier) {
      this.retryDelayMultiplier = retryDelayMultiplier;
      return this;
    }

    public Builder responseBufferSize(int responseBufferSize) {
      this.responseBufferSize = responseBufferSize;
      return this;
    }

    public Builder timeout(int timeout) {
      this.timeout = timeout;
      return this;
    }
  }
}

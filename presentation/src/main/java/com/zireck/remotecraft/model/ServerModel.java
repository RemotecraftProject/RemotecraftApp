package com.zireck.remotecraft.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerModel implements Parcelable {

  private final String ssid;
  private final String ip;
  private final String version;
  private final String seed;
  private final String worldName;
  private final String playerName;

  private ServerModel(Builder builder) {
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

    public ServerModel build() {
      return new ServerModel(this);
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

  protected ServerModel(Parcel in) {
    ssid = in.readString();
    ip = in.readString();
    version = in.readString();
    seed = in.readString();
    worldName = in.readString();
    playerName = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(ssid);
    dest.writeString(ip);
    dest.writeString(version);
    dest.writeString(seed);
    dest.writeString(worldName);
    dest.writeString(playerName);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<ServerModel> CREATOR = new Parcelable.Creator<ServerModel>() {
    @Override
    public ServerModel createFromParcel(Parcel in) {
      return new ServerModel(in);
    }

    @Override
    public ServerModel[] newArray(int size) {
      return new ServerModel[size];
    }
  };
}
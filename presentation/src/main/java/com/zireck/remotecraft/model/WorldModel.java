package com.zireck.remotecraft.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WorldModel implements Parcelable {
  private String version;
  private String ssid;
  private String ip;
  private String name;
  private String player;

  private WorldModel() {

  }

  public String getVersion() {
    return version;
  }

  public String getSsid() {
    return ssid;
  }

  public String getIp() {
    return ip;
  }

  public String getName() {
    return name;
  }

  public String getPlayer() {
    return player;
  }

  public static class Builder {
    private WorldModel worldModel;

    public Builder() {
      worldModel = new WorldModel();
    }

    public WorldModel build() {
      return worldModel;
    }

    public Builder version(String version) {
      worldModel.version = version;
      return this;
    }

    public Builder ssid(String ssid) {
      worldModel.ssid = ssid;
      return this;
    }

    public Builder ip(String ip) {
      worldModel.ip = ip;
      return this;
    }

    public Builder name(String name) {
      worldModel.name = name;
      return this;
    }

    public Builder player(String player) {
      worldModel.player = player;
      return this;
    }
  }

  protected WorldModel(Parcel in) {
    version = in.readString();
    ssid = in.readString();
    ip = in.readString();
    name = in.readString();
    player = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(version);
    dest.writeString(ssid);
    dest.writeString(ip);
    dest.writeString(name);
    dest.writeString(player);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<WorldModel> CREATOR = new Parcelable.Creator<WorldModel>() {
    @Override
    public WorldModel createFromParcel(Parcel in) {
      return new WorldModel(in);
    }

    @Override
    public WorldModel[] newArray(int size) {
      return new WorldModel[size];
    }
  };
}
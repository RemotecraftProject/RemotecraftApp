package com.zireck.remotecraft.infrastructure.protocol;

import com.google.gson.annotations.SerializedName;

public abstract class BaseJson {
  @SerializedName("app") protected String app = "Remotecraft";
  @SerializedName("type") protected String type = "request";

  public String getApp() {
    return app;
  }

  public String getType() {
    return type;
  }
}

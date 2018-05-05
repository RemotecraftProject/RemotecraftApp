package com.remotecraft.app.domain;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue public abstract class Server {

  public abstract String ssid();
  public abstract String ip();
  public abstract String hostname();
  public abstract String os();
  public abstract String version();
  public abstract String seed();
  public abstract String worldName();
  public abstract String playerName();
  public abstract String encodedWorldImage();

  public Builder toBuilder() {
    return new AutoValue_Server.Builder();
  }

  public static Builder builder() {
    return new AutoValue_Server.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder ssid(String ssid);
    public abstract Builder ip(String ip);
    public abstract Builder hostname(String hostname);
    public abstract Builder os(String os);
    public abstract Builder version(String version);
    public abstract Builder seed(String seed);
    public abstract Builder worldName(String worldName);
    public abstract Builder playerName(String playerName);
    public abstract Builder encodedWorldImage(String encodedWorldImage);
    public abstract Server build();
  }

  public static TypeAdapter<Server> typeAdapter(Gson gson) {
    return new AutoValue_Server.GsonTypeAdapter(gson);
  }
}

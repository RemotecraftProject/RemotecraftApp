package com.remotecraft.app.infrastructure.entity;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class ServerEntity {

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
    return new AutoValue_ServerEntity.Builder(                     );
  }

  public static Builder builder() {
    return new AutoValue_ServerEntity.Builder();
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
    public abstract ServerEntity build();
  }
}

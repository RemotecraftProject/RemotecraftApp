package com.remotecraft.app.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ServerModel implements Parcelable {

  public abstract @NonNull String ssid();
  public abstract @NonNull String ip();
  public abstract @NonNull String hostname();
  public abstract @NonNull String os();
  public abstract @NonNull String version();
  public abstract @NonNull String seed();
  public abstract @NonNull String worldName();
  public abstract @NonNull String playerName();
  public abstract @Nullable String encodedWorldImage();

  public @NonNull Builder toBuilder() {
    return new AutoValue_ServerModel.Builder();
  }

  public static @NonNull Builder builder() {
    return new AutoValue_ServerModel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract @NonNull Builder ssid(@NonNull String ssid);
    public abstract @NonNull Builder ip(@NonNull String ip);
    public abstract @NonNull Builder hostname(@NonNull String hostname);
    public abstract @NonNull Builder os(@NonNull String os);
    public abstract @NonNull Builder version(@NonNull String version);
    public abstract @NonNull Builder seed(@NonNull String seed);
    public abstract @NonNull Builder worldName(@NonNull String worldName);
    public abstract @NonNull Builder playerName(@NonNull String playerName);
    public abstract @Nullable Builder encodedWorldImage(@Nullable String encodedWorldImage);
    public abstract @NonNull ServerModel build();
  }
}

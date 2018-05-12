package com.remotecraft.app.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WifiStateModel implements Parcelable {

  public abstract @NonNull Integer strenghtLevel();
  public abstract @NonNull String strenght();
  public abstract @NonNull String strenghtDescription();
  public abstract @NonNull Integer iconResource();

  public static @NonNull Builder builder() {
    return new AutoValue_WifiStateModel.Builder();
  }

  public @NonNull Builder toBuilder() {
    return new AutoValue_WifiStateModel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract @NonNull Builder strenghtLevel(@NonNull Integer strenghtLevel);
    public abstract @NonNull Builder strenght(@NonNull String strenght);
    public abstract @NonNull Builder strenghtDescription(@NonNull String strenghtDescription);
    public abstract @NonNull Builder iconResource(@NonNull Integer iconResource);
    public abstract @NonNull WifiStateModel build();
  }
}

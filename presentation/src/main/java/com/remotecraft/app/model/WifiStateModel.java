package com.remotecraft.app.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WifiStateModel implements Parcelable {

  public abstract @NonNull int strenghtLevel();
  public abstract @NonNull String strenghtDescription();
  public abstract @NonNull int iconResource();

  public @NonNull Builder toBuilder() {
    return new AutoValue_WifiStateModel.Builder();
  }

  public static @NonNull Builder builder() {
    return new AutoValue_WifiStateModel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract @NonNull Builder strenghtLevel(@NonNull int strenghtLevel);
    public abstract @NonNull Builder strenghtLevel(@NonNull String strenghtDescription);
    public abstract @NonNull Builder iconResource(@NonNull int iconResource);
  }
}

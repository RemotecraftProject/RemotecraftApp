package com.zireck.remotecraft.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class PermissionModel implements Parcelable {

  public abstract @NonNull String permission();
  public abstract @NonNull String rationaleTitle();
  public abstract @NonNull String rationaleMessage();
  public abstract @NonNull String deniedMessage();

  public @NonNull Builder toBuilder() {
    return new AutoValue_PermissionModel.Builder();
  }

  public static @NonNull Builder builder() {
    return new AutoValue_PermissionModel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract @NonNull Builder permission(@NonNull String permission);
    public abstract @NonNull Builder rationaleTitle(@NonNull String rationaleTitle);
    public abstract @NonNull Builder rationaleMessage(@NonNull String rationaleMessage);
    public abstract @NonNull Builder deniedMessage(@NonNull String deniedMessage);
    public abstract @NonNull PermissionModel build();
  }
}

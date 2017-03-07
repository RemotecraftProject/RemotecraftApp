package com.zireck.remotecraft.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class NetworkAddressModel implements Parcelable {

  public abstract @NonNull String ip();
  public abstract int port();

  public @NonNull Builder toBuilder() {
    return new AutoValue_NetworkAddressModel.Builder(this);
  }

  public static @NonNull Builder builder() {
    return new AutoValue_NetworkAddressModel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract @NonNull Builder ip(@NonNull String ip);
    public abstract @NonNull Builder port(int port);
    public abstract @NonNull NetworkAddressModel build();
  }
}

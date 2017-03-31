package com.zireck.remotecraft.infrastructure.entity;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class NetworkAddressEntity {

  public abstract String ip();
  public abstract int port();

  public Builder toBuilder() {
    return new AutoValue_NetworkAddressEntity.Builder(this);
  }

  public static Builder builder() {
    return new AutoValue_NetworkAddressEntity.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder ip(String ip);
    public abstract Builder port(int port);
    public abstract NetworkAddressEntity build();
  }
}

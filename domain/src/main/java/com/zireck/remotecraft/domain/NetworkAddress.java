package com.zireck.remotecraft.domain;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class NetworkAddress {

  public abstract String ip();
  public abstract int port();

  public Builder toBuilder() {
    return new AutoValue_NetworkAddress.Builder(this);
  }

  public static Builder builder() {
    return new AutoValue_NetworkAddress.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder ip(String ip);
    public abstract Builder port(int port);
    public abstract NetworkAddress build();
  }
}

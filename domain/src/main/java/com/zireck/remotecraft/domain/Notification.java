package com.zireck.remotecraft.domain;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class Notification {

  public abstract String title();
  public abstract String content();
  public abstract String deeplink();

  public Builder toBuilder() {
    return new AutoValue_Notification.Builder(this);
  }

  public static Builder builder() {
    return new AutoValue_Notification.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder title(String title);
    public abstract Builder content(String content);
    public abstract Builder deeplink(String deeplink);
    public abstract Notification build();
  }
}

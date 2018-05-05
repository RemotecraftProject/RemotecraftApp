package com.remotecraft.app.domain;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class Permission {

  public abstract String permission();
  public abstract String rationaleTitle();
  public abstract String rationaleMessage();
  public abstract String deniedMessage();

  public Builder toBuilder() {
    return new AutoValue_Permission.Builder();
  }

  public static Builder builder() {
    return new AutoValue_Permission.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder permission(String permission);
    public abstract Builder rationaleTitle(String rationaleTitle);
    public abstract Builder rationaleMessage(String rationaleMessage);
    public abstract Builder deniedMessage(String deniedMessage);
    public abstract Permission build();
  }
}

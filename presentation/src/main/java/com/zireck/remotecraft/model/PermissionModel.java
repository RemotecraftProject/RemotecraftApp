package com.zireck.remotecraft.model;

public class PermissionModel {

  private final String permission;
  private final String rationaleTitle;
  private final String rationaleMessage;
  private final String deniedMessage;

  private PermissionModel(Builder builder) {
    this.permission = builder.permission;
    this.rationaleTitle = builder.rationaleTitle;
    this.rationaleMessage = builder.rationaleMessage;
    this.deniedMessage = builder.deniedMessage;
  }

  public String getPermission() {
    return permission;
  }

  public String getRationaleTitle() {
    return rationaleTitle;
  }

  public String getRationaleMessage() {
    return rationaleMessage;
  }

  public String getDeniedMessage() {
    return deniedMessage;
  }

  public static class Builder {
    private String permission;
    private String rationaleTitle;
    private String rationaleMessage;
    private String deniedMessage;

    public PermissionModel build() {
      return new PermissionModel(this);
    }

    public Builder permission(String permission) {
      this.permission = permission;
      return this;
    }

    public Builder rationaleTitle(String rationaleTitle) {
      this.rationaleTitle = rationaleTitle;
      return this;
    }

    public Builder rationaleMessage(String rationaleMessage) {
      this.rationaleMessage = rationaleMessage;
      return this;
    }

    public Builder deniedMessage(String deniedMessage) {
      this.deniedMessage = deniedMessage;
      return this;
    }
  }
}

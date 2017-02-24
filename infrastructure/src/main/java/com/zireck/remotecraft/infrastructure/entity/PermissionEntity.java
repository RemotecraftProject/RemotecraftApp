package com.zireck.remotecraft.infrastructure.entity;

public class PermissionEntity {

  private final String name;

  public PermissionEntity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getRationaleTitle() {
    return "Permission Request";
  }

  public String getRationaleMessage() {
    return "You need to allow the camera permission";
  }

  public String getDeniedMessage() {
    return "Allow camera permission in app settings.";
  }
}

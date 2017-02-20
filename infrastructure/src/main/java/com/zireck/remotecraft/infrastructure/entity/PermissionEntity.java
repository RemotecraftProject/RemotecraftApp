package com.zireck.remotecraft.infrastructure.entity;

public class PermissionEntity {

  private final String name;

  public PermissionEntity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

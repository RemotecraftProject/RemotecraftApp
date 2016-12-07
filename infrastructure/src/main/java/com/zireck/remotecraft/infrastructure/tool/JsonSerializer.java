package com.zireck.remotecraft.infrastructure.tool;

public interface JsonSerializer {
  <T> T fromJson(String json, Class<T> targetClass);
  String toJson(Object object);
}

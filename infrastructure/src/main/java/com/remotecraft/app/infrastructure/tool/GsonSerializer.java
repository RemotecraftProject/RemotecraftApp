package com.remotecraft.app.infrastructure.tool;

import com.google.gson.Gson;

public class GsonSerializer implements JsonSerializer {

  private final Gson gson;

  public GsonSerializer(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> T fromJson(String json, Class<T> targetClass) {
    return gson.fromJson(json, targetClass);
  }

  @Override
  public String toJson(Object object) {
    return gson.toJson(object);
  }
}

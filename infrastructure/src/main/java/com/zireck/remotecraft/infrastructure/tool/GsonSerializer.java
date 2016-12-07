package com.zireck.remotecraft.infrastructure.tool;

import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GsonSerializer implements JsonSerializer {

  private Gson gson;

  public GsonSerializer() {
    gson = new Gson();
  }

  @Override public <T> T fromJson(String json, Class<T> targetClass) {
    return gson.fromJson(json, buildType(targetClass));
  }

  @Override public String toJson(Object object) {
    return gson.toJson(object);
  }

  private ParameterizedType buildType(Class clazz) {
    return new ParameterizedType() {
      @Override public Type[] getActualTypeArguments() {
        return new Type[0];
      }

      @Override public Type getRawType() {
        return clazz;
      }

      @Override public Type getOwnerType() {
        return null;
      }
    };
  }
}

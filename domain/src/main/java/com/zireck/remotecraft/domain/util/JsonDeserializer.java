package com.zireck.remotecraft.domain.util;

public interface JsonDeserializer<T> {
  T deserialize(String serializedJson);
}

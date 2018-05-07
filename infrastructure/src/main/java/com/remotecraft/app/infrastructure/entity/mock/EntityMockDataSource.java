package com.remotecraft.app.infrastructure.entity.mock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.remotecraft.app.infrastructure.R;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.tool.ImageDecoder;

public class EntityMockDataSource {

  private final Context context;
  private final ImageDecoder imageDecoder;

  public EntityMockDataSource(Context context, ImageDecoder imageDecoder) {
    this.context = context;
    this.imageDecoder = imageDecoder;
  }

  public ServerEntity getServerEntity() {
    Bitmap mockWorldImage =
        BitmapFactory.decodeResource(context.getResources(), R.drawable.mock_world_image);
    String mockEncodedWorldImage = imageDecoder.encode(mockWorldImage, 10);

    return ServerEntity.builder()
        .ssid("MOVISTAR_C64C")
        .ip("85.215.47.129")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.8.14")
        .seed("4346234563458034")
        .worldName("Reign of Giants")
        .playerName("Etho")
        .encodedWorldImage(mockEncodedWorldImage)
        .build();
  }
}

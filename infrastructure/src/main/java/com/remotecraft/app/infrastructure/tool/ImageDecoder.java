package com.remotecraft.app.infrastructure.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import javax.inject.Inject;

public class ImageDecoder {

  @Inject
  public ImageDecoder() {

  }

  public String encode(Bitmap image) {
    return encode(image, 95);
  }

  public String encode(Bitmap image, int qualityPercentage) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.JPEG, qualityPercentage, byteArrayOutputStream);
    byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
    return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
  }

  public Bitmap decode(String encodedImage) {
    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
  }
}

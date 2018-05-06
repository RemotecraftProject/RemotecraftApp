package com.remotecraft.app.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import javax.inject.Inject;

public class ImageDecoder {

  @Inject
  public ImageDecoder() {

  }

  public Bitmap decode(String encodedImage) {
    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
  }
}

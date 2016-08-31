package com.zireck.remotecraft.imageloader;

import android.widget.ImageView;
import java.io.File;

public interface ImageLoader {
  void load(String url, ImageView imageView);
  void load(File file, ImageView imageView);
  void load(int resource, ImageView imageView);
}
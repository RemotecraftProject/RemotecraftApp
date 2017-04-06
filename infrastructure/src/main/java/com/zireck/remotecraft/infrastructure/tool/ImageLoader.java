package com.zireck.remotecraft.infrastructure.tool;

import android.widget.ImageView;
import com.squareup.picasso.Target;
import java.io.File;

public interface ImageLoader {
  void load(String url, ImageView imageView);
  void load(File file, ImageView imageView);
  void load(int resource, ImageView imageView);
  void load(String url, Target target);
}
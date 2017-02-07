package com.zireck.remotecraft.imageloader;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;

public class PicassoImageLoader implements ImageLoader {

  private final Picasso picasso;

  public PicassoImageLoader(Picasso picasso) {
    this.picasso = picasso;
  }

  @Override public void load(String url, ImageView imageView) {
    picasso.load(url).into(imageView);
  }

  @Override public void load(File file, ImageView imageView) {
    picasso.load(file).into(imageView);
  }

  @Override public void load(int resource, ImageView imageView) {
    picasso.load(resource).into(imageView);
  }
}
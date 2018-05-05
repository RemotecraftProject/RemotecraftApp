package com.remotecraft.app.infrastructure.tool;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
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

  @Override public void load(String url, Target target) {
    picasso.load(url).into(target);
  }
}
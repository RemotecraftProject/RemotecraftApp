package com.zireck.remotecraft.imageloader;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;

public class PicassoImageLoader implements ImageLoader {

  private Context context;

  public PicassoImageLoader(Context context) {
    this.context = context;
  }

  @Override public void load(String url, ImageView imageView) {
    Picasso.with(context).load(url).into(imageView);
  }

  @Override public void load(File file, ImageView imageView) {
    Picasso.with(context).load(file).into(imageView);
  }

  @Override public void load(int resource, ImageView imageView) {
    Picasso.with(context).load(resource).into(imageView);
  }
}
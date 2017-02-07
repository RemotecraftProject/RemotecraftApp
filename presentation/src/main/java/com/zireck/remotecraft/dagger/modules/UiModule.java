package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.imageloader.PicassoImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

  public UiModule() {

  }

  @Provides @PerActivity Picasso providePicasso(Context context) {
    return Picasso.with(context);
  }

  @Provides @PerActivity
  ImageLoader provideImageLoader(Picasso picasso) {
    return new PicassoImageLoader(picasso);
  }
}

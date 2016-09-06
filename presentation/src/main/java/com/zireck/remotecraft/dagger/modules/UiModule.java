package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.imageloader.PicassoImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

  public UiModule() {

  }

  @Provides @PerActivity
  ImageLoader provideImageLoader(Context context) {
    return new PicassoImageLoader(context);
  }
}

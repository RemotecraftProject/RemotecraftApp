package com.zireck.remotecraft.dagger.modules;

import android.app.Activity;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.imageloader.PicassoImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity Activity activity() {
    return this.activity;
  }

  @Provides @PerActivity ImageLoader provideImageLoader() {
    return new PicassoImageLoader(activity);
  }
}

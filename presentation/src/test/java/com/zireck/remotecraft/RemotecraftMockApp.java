package com.zireck.remotecraft;

import android.app.Activity;
import com.zireck.remotecraft.dagger.ActivityComponentBuilder;
import java.util.HashMap;
import java.util.Map;

public class RemotecraftMockApp extends RemotecraftApp {

  public void putActivityComponentBuilder(ActivityComponentBuilder builder,
      Class<? extends Activity> clazz) {
    Map<Class<? extends Activity>, ActivityComponentBuilder> activityComponentBuilders =
        new HashMap<>(this.activityComponentBuilders);
    activityComponentBuilders.put(clazz, builder);
    this.activityComponentBuilders = activityComponentBuilders;
  }
}

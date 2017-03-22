package com.zireck.remotecraft.dagger;

import android.app.Activity;

public interface HasActivitySubcomponentBuilders {
  ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass);
}

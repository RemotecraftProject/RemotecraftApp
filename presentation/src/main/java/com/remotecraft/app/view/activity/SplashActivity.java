package com.remotecraft.app.view.activity;

import android.os.Bundle;
import android.os.Handler;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.HasActivitySubcomponentBuilders;
import com.remotecraft.app.dagger.components.SplashComponent;
import com.remotecraft.app.dagger.modules.activitymodules.SplashModule;

public class SplashActivity extends BaseActivity {

  private static final int TIMEOUT_IN_SECONDS = 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    initUi();
    initTimeout();
  }

  @Override
  protected void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders) {
    ((SplashComponent.Builder) hasActivitySubcomponentBuilders.getActivityComponentBuilder(
        SplashActivity.class))
        .activityModule(new SplashModule(this))
        .build()
        .injectMembers(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
  }

  private void initTimeout() {
    new Handler().postDelayed(() -> {
      navigator.navigateToServerSearchActivity(SplashActivity.this);
      finish();
    }, TIMEOUT_IN_SECONDS * 1000);
  }
}

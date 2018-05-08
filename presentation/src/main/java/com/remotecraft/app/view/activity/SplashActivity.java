package com.remotecraft.app.view.activity;

import android.os.Bundle;
import android.os.Handler;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.HasActivitySubcomponentBuilders;
import com.remotecraft.app.dagger.components.SplashComponent;
import com.remotecraft.app.dagger.modules.activitymodules.SplashModule;

public class SplashActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    initUi();

    int timeoutInMillis = getResources().getInteger(R.integer.activity_splash_timeout_in_millis);
    initTimeout(timeoutInMillis);
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

  private void initTimeout(int timeoutInMillis) {
    new Handler().postDelayed(() -> {
      navigator.navigateToServerSearchActivity(SplashActivity.this);
      finish();
    }, timeoutInMillis);
  }
}

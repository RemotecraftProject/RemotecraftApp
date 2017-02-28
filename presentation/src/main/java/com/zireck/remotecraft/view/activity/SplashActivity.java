package com.zireck.remotecraft.view.activity;

import android.os.Bundle;
import android.os.Handler;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.components.DaggerActivityComponent;

public class SplashActivity extends BaseActivity {

  private static final int TIMEOUT_IN_SECONDS = 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    initInjector();
    initUi();
    initTimeout();
  }

  private void initInjector() {
    DaggerActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
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

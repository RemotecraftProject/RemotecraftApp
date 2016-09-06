package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.components.DaggerUiComponent;
import com.zireck.remotecraft.dagger.components.UiComponent;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.presenter.SearchPresenter;
import javax.inject.Inject;

public class SearchActivity extends BaseActivity {

  private UiComponent uiComponent;

  @Inject SearchPresenter presenter;
  @Inject ImageLoader imageLoader;

  @BindView(R.id.background) ImageView background;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, SearchActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    initInjector();
    initUi();
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }

  private void initInjector() {
    uiComponent = DaggerUiComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .uiModule(new UiModule())
        .build();

    uiComponent.inject(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    imageLoader.load(R.drawable.mesa, background);
  }
}

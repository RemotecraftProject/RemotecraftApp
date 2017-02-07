package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.HasComponent;
import com.zireck.remotecraft.dagger.components.DaggerSearchServerComponent;
import com.zireck.remotecraft.dagger.components.SearchServerComponent;
import com.zireck.remotecraft.dagger.modules.InteractorsModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.exception.ErrorMessageFactory;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.presenter.SearchServerPresenter;
import com.zireck.remotecraft.view.SearchServerView;
import javax.inject.Inject;
import timber.log.Timber;

public class SearchServerActivity extends BaseActivity
    implements HasComponent<SearchServerComponent>, SearchServerView {

  @Inject SearchServerPresenter presenter;
  @Inject ImageLoader imageLoader;
  @BindView(R.id.background) ImageView background;
  @BindView(R.id.found) TextView found;
  @BindView(R.id.ssid) TextView ssid;
  @BindView(R.id.ip) TextView ip;
  @BindView(R.id.version) TextView version;
  @BindView(R.id.world) TextView world;
  @BindView(R.id.player) TextView player;
  private SearchServerComponent searchServerComponent;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, SearchServerActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_server);

    initInjector();
    initUi();
    presenter.setView(this);
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

  @Override public SearchServerComponent getComponent() {
    return searchServerComponent;
  }

  private void initInjector() {
    searchServerComponent = DaggerSearchServerComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .uiModule(new UiModule())
        .interactorsModule(new InteractorsModule())
        .build();

    searchServerComponent.inject(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
  }

  @Override public void navigateToServerDetail(ServerModel serverModel) {
    navigator.navigateToServerFoundActivity(this, serverModel);
  }

  @Override public void showError(Exception exception) {
    String errorMessage = ErrorMessageFactory.create(this, exception);
    Timber.e(errorMessage);
    Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
  }
}

package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.HasComponent;
import com.zireck.remotecraft.dagger.components.DaggerSearchComponent;
import com.zireck.remotecraft.dagger.components.SearchComponent;
import com.zireck.remotecraft.dagger.modules.InteractorsModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.presenter.SearchPresenter;
import com.zireck.remotecraft.view.SearchView;
import javax.inject.Inject;

public class SearchActivity extends BaseActivity implements HasComponent<SearchComponent>,
    SearchView {

  private SearchComponent searchComponent;

  @Inject SearchPresenter presenter;
  @Inject ImageLoader imageLoader;

  @BindView(R.id.background) ImageView background;
  @BindView(R.id.found) TextView found;
  @BindView(R.id.ssid) TextView ssid;
  @BindView(R.id.ip) TextView ip;
  @BindView(R.id.version) TextView version;
  @BindView(R.id.world) TextView world;
  @BindView(R.id.player) TextView player;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, SearchActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

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

  @Override public SearchComponent getComponent() {
    return searchComponent;
  }

  private void initInjector() {
    searchComponent = DaggerSearchComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .uiModule(new UiModule())
        .interactorsModule(new InteractorsModule())
        .build();

    searchComponent.inject(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
  }

  @Override public void renderWorld(World world) {
    if (world == null) {
      found.setText("Not Found!");
      return;
    }

    found.setText("Found!");
    version.setText(world.getVersion());
    ssid.setText(world.getSsid());
    ip.setText(world.getIp());
    this.world.setText(world.getName());
    player.setText(world.getPlayer());

  }
}

package com.zireck.remotecraft.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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
  @BindView(R.id.menu) FloatingActionMenu floatingActionMenu;
  @BindView(R.id.fab_wifi) FloatingActionButton floatingActionButtonWifi;
  @BindView(R.id.fab_qrcode) FloatingActionButton floatingActionButtonQrCode;
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

  @Override public void navigateToServerDetail(ServerModel serverModel) {
    navigator.navigateToServerFoundActivity(this, serverModel);
  }

  @Override public void showError(Exception exception) {
    String errorMessage = ErrorMessageFactory.create(this, exception);
    Timber.e(errorMessage);
    Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.fab_wifi) public void onClickFabWifi(View view) {
    presenter.onClickWifi();
  }

  @OnClick(R.id.fab_qrcode) public void onClickFabQrCode(View view) {
    Snackbar.make(findViewById(android.R.id.content), "Currently unavailable",
        Snackbar.LENGTH_SHORT).show();
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

    floatingActionMenu.setClosedOnTouchOutside(true);
    setFloatingActionMenuAnimation();
  }

  private void setFloatingActionMenuAnimation() {
    AnimatorSet set = new AnimatorSet();

    ObjectAnimator scaleOutX =
        ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
    ObjectAnimator scaleOutY =
        ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

    ObjectAnimator scaleInX =
        ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
    ObjectAnimator scaleInY =
        ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

    scaleOutX.setDuration(50);
    scaleOutY.setDuration(50);

    scaleInX.setDuration(150);
    scaleInY.setDuration(150);

    scaleInX.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        floatingActionMenu.getMenuIconView()
            .setImageResource(floatingActionMenu.isOpened() ? R.drawable.ic_magnify_white
                : R.drawable.ic_close_white);
      }
    });

    set.play(scaleOutX).with(scaleOutY);
    set.play(scaleInX).with(scaleInY).after(scaleOutX);
    set.setInterpolator(new OvershootInterpolator(2));

    floatingActionMenu.setIconToggleAnimatorSet(set);
  }
}

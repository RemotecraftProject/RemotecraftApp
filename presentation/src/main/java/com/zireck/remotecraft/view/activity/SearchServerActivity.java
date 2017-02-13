package com.zireck.remotecraft.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.ybq.android.spinkit.SpinKitView;
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

public class SearchServerActivity extends BaseActivity
    implements HasComponent<SearchServerComponent>, SearchServerView {

  @Inject SearchServerPresenter presenter;
  @Inject ImageLoader imageLoader;

  @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
  @BindView(R.id.loading) SpinKitView loadingView;
  @BindView(R.id.dimmer_view) View dimmerView;
  @BindView(R.id.close_camera_button) ImageButton closeCameraButton;
  @BindView(R.id.qrCodeReaderView) QRCodeReaderView qrCodeReaderView;
  @BindView(R.id.menu) FloatingActionMenu floatingActionMenu;
  @BindView(R.id.fab_wifi) FloatingActionButton floatingActionButtonWifi;
  @BindView(R.id.fab_qrcode) FloatingActionButton floatingActionButtonQrCode;
  @BindView(R.id.fab_ip) FloatingActionButton floatingActionButtonIp;
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
    presenter.attachView(this);
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

  @Override public void onBackPressed() {
    super.onBackPressed();
  }

  @Override public SearchServerComponent getComponent() {
    return searchServerComponent;
  }

  @Override public void navigateToServerDetail(ServerModel serverModel) {
    navigator.navigateToServerFoundActivity(this, serverModel);
  }

  @Override public void showMessage(String message) {
    displayMessage(message);
  }

  @Override public void showError(Exception exception) {
    String errorMessage = ErrorMessageFactory.create(this, exception);
    displayMessage(errorMessage);
  }

  @Override public void closeMenu() {
    floatingActionMenu.close(true);
  }

  @Override public void showLoading() {
    loadingView.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    loadingView.setVisibility(View.GONE);
  }

  @Override public void startQrScanner() {
    closeCameraButton.setVisibility(View.VISIBLE);
    qrCodeReaderView.setVisibility(View.VISIBLE);
    qrCodeReaderView.startCamera();
  }

  @Override public void stopQrScanner() {
    qrCodeReaderView.stopCamera();
    qrCodeReaderView.setVisibility(View.GONE);
    closeCameraButton.setVisibility(View.GONE);
  }

  @Override public void showEnterNetworkAddressDialog() {
    AlertDialog.Builder enterNetworkAddressDialog = new AlertDialog.Builder(this);
    LayoutInflater layoutInflater = getLayoutInflater();
    View enterNetworkAddressDialogView =
        layoutInflater.inflate(R.layout.dialog_enter_network_address, null);
    enterNetworkAddressDialog.setView(enterNetworkAddressDialogView);
    enterNetworkAddressDialog.setPositiveButton("Done", (dialogInterface, i) -> {
      EditText ip = (EditText) enterNetworkAddressDialogView.findViewById(R.id.ip);
      EditText port = (EditText) enterNetworkAddressDialogView.findViewById(R.id.port);
      presenter.onEnterNetworkAddress(ip.getText().toString(), port.getText().toString());
    });
    enterNetworkAddressDialog.setNegativeButton("Cancel",
        (dialogInterface, i) -> dialogInterface.dismiss());

    enterNetworkAddressDialog.show();
  }

  @OnClick(R.id.fab_wifi) public void onClickFabWifi(View view) {
    presenter.onClickScanWifi();
  }

  @OnClick(R.id.fab_qrcode) public void onClickFabQrCode(View view) {
    presenter.onClickScanQrCode();
  }

  @OnClick(R.id.fab_ip) public void onClickFabIp(View view) {
    presenter.onClickEnterNetworkAddress();
  }

  @OnClick(R.id.close_camera_button) public void onClickCloseCamera(View view) {
    presenter.onClickCloseCamera();
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

    enableFloatingActionMenuAnimation();
    floatingActionMenu.setClosedOnTouchOutside(true);
    floatingActionMenu.setOnMenuToggleListener(this::dimeBackground);
    dimmerView.setOnClickListener(view -> {
      floatingActionMenu.close(true);
    });

    setupQrCodeReader();
  }

  private void setupQrCodeReader() {
    qrCodeReaderView.setQRDecodingEnabled(true);
    qrCodeReaderView.setAutofocusInterval(2000L);
    qrCodeReaderView.setBackCamera();
    qrCodeReaderView.setOnQRCodeReadListener(((text, points) -> {
      presenter.onReadQrCode(text);
    }));
  }

  private void enableFloatingActionMenuAnimation() {
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

  private void dimeBackground(boolean shouldDime) {
    final float targetAlpha = shouldDime ? 1f : 0;
    final int endVisibility = shouldDime ? View.VISIBLE : View.GONE;
    dimmerView.setVisibility(View.VISIBLE);
    dimmerView.animate()
        .alpha(targetAlpha)
        .setDuration(250)
        .withEndAction(() -> dimmerView.setVisibility(endVisibility))
        .start();
  }

  private void displayMessage(String message) {
    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
  }
}

package com.remotecraft.app.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.ybq.android.spinkit.SpinKitView;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.HasActivitySubcomponentBuilders;
import com.remotecraft.app.dagger.components.ServerSearchComponent;
import com.remotecraft.app.dagger.modules.activitymodules.ServerSearchModule;
import com.remotecraft.app.exception.ErrorMessageFactory;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.model.ServerModel;
import com.remotecraft.app.presenter.ServerSearchPresenter;
import com.remotecraft.app.view.ServerSearchView;
import javax.inject.Inject;

public class ServerSearchActivity extends BaseActivity implements ServerSearchView {

  private static final String KEY_SERVER_FOUND = "key_server_found";
  private static final String KEY_DOMAIN_SERVER_FOUND_SERIALIZED =
      "key_domain_server_found_serialized";

  @Inject ServerSearchPresenter presenter;
  @Inject ImageLoader imageLoader;

  @BindView(R.id.layout_root) CoordinatorLayout rootLayout;
  @BindView(R.id.view_dimmer) View dimmerView;
  @BindView(R.id.view_loading) SpinKitView loadingView;
  @BindView(R.id.view_qr_reader) QRCodeReaderView qrReaderView;
  @BindView(R.id.button_fab_menu) FloatingActionMenu fabMenuView;
  @BindView(R.id.button_fab_wifi) FloatingActionButton fabWifiView;
  @BindView(R.id.button_fab_qr) FloatingActionButton fabQrView;
  @BindView(R.id.button_fab_ip) FloatingActionButton fabIpView;
  @BindView(R.id.button_close_camera) ImageButton closeCameraView;

  @NonNull
  public static Intent getCallingIntent(Context context) {
    return new Intent(context, ServerSearchActivity.class);
  }

  @Deprecated
  public static Intent getCallingIntent(Context context, ServerModel serverModel) {
    Intent intent = new Intent(context, ServerSearchActivity.class);

    Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_SERVER_FOUND, serverModel);
    intent.putExtras(bundle);

    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_server_search);

    initUi();
    presenter.attachView(this);
    mapExtras(getIntent());
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.stop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.destroy();
    presenter.detachView();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    mapExtras(intent);
    super.onNewIntent(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    boolean isSuccess = resultCode == RESULT_OK;
    if (!isSuccess) return;

    ServerModel serverModel = data.getExtras().getParcelable(ServerFoundActivity.KEY_SERVER);
    presenter.onNavigationResult(requestCode, isSuccess, serverModel);
  }

  @Override
  protected void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders) {
    ((ServerSearchComponent.Builder) hasActivitySubcomponentBuilders.getActivityComponentBuilder(
        ServerSearchActivity.class))
        .activityModule(new ServerSearchModule(this))
        .build()
        .injectMembers(this);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public void navigateToServerDetail(ServerModel serverModel) {
    navigator.navigateToServerFoundActivity(this, serverModel);
  }

  @Override
  public void navigateToMainScreen(ServerModel serverModel) {
    Toast.makeText(this, "Connecting to server: " + serverModel.worldName(), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void showMessage(String message) {
    displayMessage(message);
  }

  @Override
  public void showError(Exception exception) {
    String errorMessage = ErrorMessageFactory.create(this, exception);
    displayMessage(errorMessage);
  }

  @Override
  public void closeMenu() {
    fabMenuView.close(true);
  }

  @Override
  public void showLoading() {
    loadingView.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    loadingView.setVisibility(View.GONE);
  }

  @Override
  public void startQrScanner() {
    closeCameraView.setVisibility(View.VISIBLE);
    qrReaderView.setVisibility(View.VISIBLE);
    qrReaderView.startCamera();
  }

  @Override
  public void stopQrScanner() {
    qrReaderView.stopCamera();
    qrReaderView.setVisibility(View.GONE);
    closeCameraView.setVisibility(View.GONE);
  }

  @Override
  public void showEnterNetworkAddressDialog() {
    AlertDialog.Builder enterNetworkAddressDialog = new AlertDialog.Builder(this);
    LayoutInflater layoutInflater = getLayoutInflater();
    View enterNetworkAddressDialogView =
        layoutInflater.inflate(R.layout.dialog_enter_network_address, null);
    enterNetworkAddressDialog.setView(enterNetworkAddressDialogView);
    enterNetworkAddressDialog.setTitle(R.string.enter_network_address_dialog_title);
    enterNetworkAddressDialog.setPositiveButton(R.string.enter_network_address_dialog_button_accept,
        (dialogInterface, i) -> {
          EditText ip = enterNetworkAddressDialogView.findViewById(R.id.ip);
          EditText port = enterNetworkAddressDialogView.findViewById(R.id.port);
          presenter.onEnterNetworkAddress(ip.getText().toString(), port.getText().toString());
        });
    enterNetworkAddressDialog.setNegativeButton(R.string.enter_network_address_dialog_button_cancel,
        (dialogInterface, i) -> dialogInterface.dismiss());

    enterNetworkAddressDialog.show();
  }

  @OnClick(R.id.button_fab_wifi)
  public void onFabWifiButtonClick(View view) {
    presenter.onClickScanWifi();
  }

  @OnClick(R.id.button_fab_qr)
  public void onFabQrButtonClick(View view) {
    presenter.onClickScanQrCode();
  }

  @OnClick(R.id.button_fab_ip)
  public void onFabIpButtonClick(View view) {
    presenter.onClickEnterNetworkAddress();
  }

  @OnClick(R.id.button_close_camera)
  public void onCloseCameraButtonClick(View view) {
    presenter.onClickCloseCamera();
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    initFabMenuAndButtonColors();
    enableFloatingActionMenuAnimation();
    fabMenuView.setClosedOnTouchOutside(true);
    fabMenuView.setOnMenuToggleListener(this::dimeBackground);
    dimmerView.setOnClickListener(view -> fabMenuView.close(true));

    setupQrCodeReader();
  }

  private void initFabMenuAndButtonColors() {
    fabMenuView.setMenuButtonColorNormal(getResources().getColor(R.color.fab_menu_normal));
    fabMenuView.setMenuButtonColorPressed(getResources().getColor(R.color.fab_menu_pressed));
    fabMenuView.setMenuButtonColorRipple(getResources().getColor(R.color.fab_menu_ripple));

    int fabButtonColorNormal = getResources().getColor(R.color.fab_button_normal);
    int fabButtonColorPressed = getResources().getColor(R.color.fab_button_pressed);
    int fabButtonColorRipple = getResources().getColor(R.color.fab_button_ripple);
    fabIpView.setButtonSize(FloatingActionButton.SIZE_NORMAL);
    fabIpView.setColorNormal(fabButtonColorNormal);
    fabIpView.setColorPressed(fabButtonColorPressed);
    fabIpView.setColorRipple(fabButtonColorRipple);

    fabQrView.setButtonSize(FloatingActionButton.SIZE_NORMAL);
    fabQrView.setColorNormal(fabButtonColorNormal);
    fabQrView.setColorPressed(fabButtonColorPressed);
    fabQrView.setColorRipple(fabButtonColorRipple);

    fabWifiView.setButtonSize(FloatingActionButton.SIZE_NORMAL);
    fabWifiView.setColorNormal(fabButtonColorNormal);
    fabWifiView.setColorPressed(fabButtonColorPressed);
    fabWifiView.setColorRipple(fabButtonColorRipple);
  }

  private void mapExtras(Intent intent) {
    if (intent == null || intent.getExtras() == null) {
      return;
    }

    Bundle extras = intent.getExtras();
    if (extras.getParcelable(KEY_SERVER_FOUND) != null) {
      ServerModel serverModel = extras.getParcelable(KEY_SERVER_FOUND);
      presenter.onServerFound(serverModel);
    }

    if (extras.getString(KEY_DOMAIN_SERVER_FOUND_SERIALIZED) != null) {
      String serializedServer = extras.getString(KEY_DOMAIN_SERVER_FOUND_SERIALIZED);
      presenter.onSerializedDomainServerFound(serializedServer);
    }
  }

  private void setupQrCodeReader() {
    qrReaderView.setQRDecodingEnabled(true);
    qrReaderView.setAutofocusInterval(2000L);
    qrReaderView.setBackCamera();
    qrReaderView.setOnQRCodeReadListener(((text, points) -> presenter.onReadQrCode(text)));
  }

  private void enableFloatingActionMenuAnimation() {
    AnimatorSet animatorSet = new AnimatorSet();

    ObjectAnimator scaleOutX =
        ObjectAnimator.ofFloat(fabMenuView.getMenuIconView(), "scaleX", 1.0f, 0.2f);
    ObjectAnimator scaleOutY =
        ObjectAnimator.ofFloat(fabMenuView.getMenuIconView(), "scaleY", 1.0f, 0.2f);

    ObjectAnimator scaleInX =
        ObjectAnimator.ofFloat(fabMenuView.getMenuIconView(), "scaleX", 0.2f, 1.0f);
    ObjectAnimator scaleInY =
        ObjectAnimator.ofFloat(fabMenuView.getMenuIconView(), "scaleY", 0.2f, 1.0f);

    scaleOutX.setDuration(50);
    scaleOutY.setDuration(50);

    scaleInX.setDuration(150);
    scaleInY.setDuration(150);

    scaleInX.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        int fabMenuIcon =
            fabMenuView.isOpened() ? R.drawable.ic_magnify_white : R.drawable.ic_close_white;
        fabMenuView.getMenuIconView().setImageResource(fabMenuIcon);
      }
    });

    animatorSet.play(scaleOutX).with(scaleOutY);
    animatorSet.play(scaleInX).with(scaleInY).after(scaleOutX);
    animatorSet.setInterpolator(new OvershootInterpolator(2));

    fabMenuView.setIconToggleAnimatorSet(animatorSet);
  }

  private void dimeBackground(boolean shouldDime) {
    final float targetAlpha = shouldDime ? 1f : 0;
    final int endVisibility = shouldDime ? View.VISIBLE : View.GONE;
    dimmerView.setVisibility(View.VISIBLE);
    dimmerView.animate()
        .alpha(targetAlpha)
        .setDuration(shouldDime ? 250 : 200)
        .withEndAction(() -> dimmerView.setVisibility(endVisibility))
        .start();
  }

  private void displayMessage(String message) {
    Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show();
  }
}

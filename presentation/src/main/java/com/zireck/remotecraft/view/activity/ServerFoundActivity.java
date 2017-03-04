package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.components.DaggerServerFoundComponent;
import com.zireck.remotecraft.dagger.components.ServerFoundComponent;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import com.zireck.remotecraft.view.ServerFoundView;
import javax.inject.Inject;

public class ServerFoundActivity extends BaseActivity implements ServerFoundView {

  public static final String KEY_SERVER = "server";

  @Inject ServerFoundPresenter presenter;
  private ServerFoundComponent serverFoundComponent;

  @BindView(R.id.world_name) TextView worldNameView;
  @BindView(R.id.player_name) TextView playerNameView;
  @BindView(R.id.network_info) TextView networkInfoView;
  @BindView(R.id.button_accept) Button acceptButton;
  @BindView(R.id.button_cancel) Button cancelButton;

  public static Intent getCallingIntent(Context context, ServerModel serverModel) {
    Intent intent = new Intent(context, ServerFoundActivity.class);

    Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_SERVER, serverModel);
    intent.putExtras(bundle);

    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_server_found);

    initInjector();
    initUi();
    presenter.attachView(this);
    mapExtras();
  }

  private void initInjector() {
    serverFoundComponent = DaggerServerFoundComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .presentersModule(new PresentersModule())
        .build();

    serverFoundComponent.inject(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  private void mapExtras() {
    if (getIntent() != null && getIntent().getExtras() != null) {
      ServerModel serverModel = getIntent().getExtras().getParcelable(KEY_SERVER);
      presenter.setServer(serverModel);
    }
  }

  @Override public void renderWorldName(String worldName) {
    worldNameView.setText(worldName);
  }

  @Override public void renderPlayerName(String playerName) {
    playerNameView.setText(playerName);
  }

  @Override public void renderNetworkInfo(String networkInfo) {
    networkInfoView.setText(networkInfo);
  }

  @Override public void navigateBack(boolean isSuccess, ServerModel serverModel) {
    navigator.finishActivity(this, isSuccess, KEY_SERVER, serverModel);
  }

  @OnClick(R.id.button_accept) public void onClickAccept(View view) {
    presenter.onClickAccept();
  }

  @OnClick(R.id.button_cancel) public void onClickCancel(View view) {
    presenter.onClickCancel();
  }
}

package com.remotecraft.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.HasActivitySubcomponentBuilders;
import com.remotecraft.app.dagger.components.ServerFoundComponent;
import com.remotecraft.app.dagger.modules.activitymodules.ServerFoundModule;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarSize;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarUrl;
import com.remotecraft.app.infrastructure.tool.ImageDecoder;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.model.ServerModel;
import com.remotecraft.app.presenter.ServerFoundPresenter;
import com.remotecraft.app.tools.OsIconProvider;
import com.remotecraft.app.view.ServerFoundView;
import com.remotecraft.app.view.custom.ServerInfoView;
import javax.inject.Inject;

public class ServerFoundActivity extends BaseActivity implements ServerFoundView {

  public static final String KEY_SERVER = "server";

  @Inject ServerFoundPresenter presenter;
  @Inject ImageLoader imageLoader;
  @Inject ImageDecoder imageDecoder;
  @Inject OsIconProvider osIconProvider;
  @Inject @PlayerAvatarUrl String playerAvatarUrl;
  @Inject @PlayerAvatarSize int playerAvatarSize;

  @BindView(R.id.view_server_info) ServerInfoView serverInfoView;

  public static Intent getCallingIntent(Context context, ServerModel serverModel) {
    Intent intent = new Intent(context, ServerFoundActivity.class);

    Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_SERVER, serverModel);
    intent.putExtras(bundle);

    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_server_found);

    initUi();
    presenter.attachView(this);
    mapExtras();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override
  protected void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders) {
    ((ServerFoundComponent.Builder) hasActivitySubcomponentBuilders.getActivityComponentBuilder(
        ServerFoundActivity.class))
        .activityModule(new ServerFoundModule(this))
        .build()
        .injectMembers(this);
  }

  private void initUi() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(getString(R.string.server_found_title));
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  private void mapExtras() {
    if (getIntent() != null && getIntent().getExtras() != null) {
      ServerModel serverModel = getIntent().getExtras().getParcelable(KEY_SERVER);
      presenter.setServer(serverModel);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        presenter.onClickCancel();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void renderServer(@NonNull ServerModel serverModel) {
    Bitmap worldImage = imageDecoder.decode(serverModel.encodedWorldImage());
    int osIconResource = osIconProvider.getIconForOs(serverModel.os());
    serverInfoView.renderServer(this, serverModel, imageLoader, worldImage, playerAvatarUrl,
        playerAvatarSize, osIconResource);
  }

  @Override
  public void showError(@NonNull String errorMessage) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
  }

  @Override
  public void navigateBack(boolean isSuccess, ServerModel serverModel) {
    navigator.finishActivity(this, isSuccess, KEY_SERVER, serverModel);
  }

  @OnClick(R.id.button_connect)
  public void onConnectButtonClick(View view) {
    presenter.onClickAccept();
  }
}

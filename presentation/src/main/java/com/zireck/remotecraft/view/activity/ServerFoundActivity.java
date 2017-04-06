package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.HasActivitySubcomponentBuilders;
import com.zireck.remotecraft.dagger.components.ServerFoundComponent;
import com.zireck.remotecraft.dagger.modules.activitymodules.ServerFoundModule;
import com.zireck.remotecraft.dagger.qualifiers.PlayerAvatarSize;
import com.zireck.remotecraft.dagger.qualifiers.PlayerAvatarUrl;
import com.zireck.remotecraft.infrastructure.tool.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import com.zireck.remotecraft.view.ServerFoundView;
import com.zireck.remotecraft.view.custom.ServerInfoView;
import javax.inject.Inject;

public class ServerFoundActivity extends BaseActivity implements ServerFoundView {

  public static final String KEY_SERVER = "server";

  @Inject ServerFoundPresenter presenter;
  @Inject ImageLoader imageLoader;
  @Inject @PlayerAvatarUrl String playerAvatarUrl;
  @Inject @PlayerAvatarSize int playerAvatarSize;

  @BindView(R.id.server_info) ServerInfoView serverInfoView;

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

    initUi();
    presenter.attachView(this);
    mapExtras();
  }

  @Override protected void onDestroy() {
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
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  private void mapExtras() {
    if (getIntent() != null && getIntent().getExtras() != null) {
      ServerModel serverModel = getIntent().getExtras().getParcelable(KEY_SERVER);
      presenter.setServer(serverModel);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        presenter.onClickCancel();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void renderServer(ServerModel serverModel) {
    serverInfoView.renderServer(this, serverModel, imageLoader, playerAvatarUrl, playerAvatarSize);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(serverModel.worldName());
    }
  }

  @Override public void showError(String errorMessage) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
  }

  @Override public void navigateBack(boolean isSuccess, ServerModel serverModel) {
    navigator.finishActivity(this, isSuccess, KEY_SERVER, serverModel);
  }

  @OnClick(R.id.button_connect) public void onClickAccept(View view) {
    presenter.onClickAccept();
  }
}

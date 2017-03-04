package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.components.DaggerServerFoundComponent;
import com.zireck.remotecraft.dagger.components.ServerFoundComponent;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import com.zireck.remotecraft.view.ServerFoundView;
import com.zireck.remotecraft.view.custom.ServerInfoView;
import javax.inject.Inject;

public class ServerFoundActivity extends BaseActivity implements ServerFoundView {

  public static final String KEY_SERVER = "server";

  private ServerFoundComponent serverFoundComponent;

  @Inject ServerFoundPresenter presenter;
  @Inject ImageLoader imageLoader;

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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        presenter.onClickCancel();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void renderServer(ServerModel serverModel) {
    serverInfoView.renderServer(this, serverModel, imageLoader);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(serverModel.getWorldName());
    }
  }

  @Override public void showError(String errorMessage) {
    Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
  }

  @Override public void navigateBack(boolean isSuccess, ServerModel serverModel) {
    navigator.finishActivity(this, isSuccess, KEY_SERVER, serverModel);
  }

  @OnClick(R.id.button_connect) public void onClickAccept(View view) {
    presenter.onClickAccept();
  }
}

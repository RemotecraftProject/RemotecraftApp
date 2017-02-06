package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.dagger.components.DaggerWorldFoundComponent;
import com.zireck.remotecraft.dagger.components.WorldFoundComponent;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.model.WorldModel;
import com.zireck.remotecraft.presenter.WorldFoundPresenter;
import com.zireck.remotecraft.view.WorldFoundView;
import javax.inject.Inject;

public class WorldFoundActivity extends BaseActivity implements WorldFoundView {

  public static final String KEY_WORLD = "world";

  @Inject WorldFoundPresenter presenter;
  private WorldFoundComponent worldFoundComponent;

  @BindView(R.id.world_name) TextView worldNameView;
  @BindView(R.id.player_name) TextView playerNameView;
  @BindView(R.id.network_info) TextView networkInfoView;
  @BindView(R.id.button_accept) Button acceptButton;
  @BindView(R.id.button_cancel) Button cancelButton;

  public static Intent getCallingIntent(Context context, WorldModel worldModel) {
    Intent intent = new Intent(context, WorldFoundActivity.class);

    Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_WORLD, worldModel);
    intent.putExtras(bundle);

    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_world_found);

    initInjector();
    presenter.setView(this);
    mapExtras();
  }

  private void initInjector() {
    worldFoundComponent = DaggerWorldFoundComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .presentersModule(new PresentersModule())
        .build();

    worldFoundComponent.inject(this);
  }

  private void mapExtras() {
    if (getIntent() != null && getIntent().getExtras() != null) {
      WorldModel worldModel = getIntent().getExtras().getParcelable(KEY_WORLD);
      presenter.setWorld(worldModel);
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
}

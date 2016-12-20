package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.model.WorldModel;
import com.zireck.remotecraft.view.WorldFoundView;

public class WorldFoundPresenter implements Presenter<WorldFoundView> {

  private WorldFoundView view;
  private WorldModel worldModel;

  public WorldFoundPresenter() {

  }

  public void setWorld(WorldModel worldModel) {
    this.worldModel = worldModel;
    showWorldInView(worldModel);
  }

  @Override public void setView(@NonNull WorldFoundView view) {
    this.view = view;
  }

  @Override public void resume() {
    showWorldInView(worldModel);
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  private void showWorldInView(WorldModel worldModel) {
    // TODO Check if it's a valid World as well
    if (worldModel == null) {
      showErrorWorldInView();
    } else {
      showValidWorldInView(worldModel);
    }
  }

  private void showErrorWorldInView() {
    view.renderWorldName("Invalid World");
    view.renderPlayerName("");
    view.renderNetworkInfo("0.0.0.0");
  }

  private void showValidWorldInView(WorldModel worldModel) {
    view.renderWorldName(worldModel.getName());
    view.renderPlayerName(worldModel.getPlayer());
    view.renderNetworkInfo(String.format("%s @ %s", worldModel.getIp(), worldModel.getSsid()));
  }
}

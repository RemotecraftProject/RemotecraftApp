package com.zireck.remotecraft.view;

import com.zireck.remotecraft.model.ServerModel;

public interface ServerFoundView extends BaseView {
  void renderWorldName(String worldName);
  void renderPlayerName(String playerName);
  void renderNetworkInfo(String networkInfo);
  void navigateBack(boolean isSuccess, ServerModel serverModel);
}

package com.zireck.remotecraft.view;

public interface ServerFoundView extends BaseView {
  void renderWorldName(String worldName);
  void renderPlayerName(String playerName);
  void renderNetworkInfo(String networkInfo);
}

package com.zireck.remotecraft.view;

public interface WorldFoundView extends BaseView {
  void renderWorldName(String worldName);
  void renderPlayerName(String playerName);
  void renderNetworkInfo(String networkInfo);
}

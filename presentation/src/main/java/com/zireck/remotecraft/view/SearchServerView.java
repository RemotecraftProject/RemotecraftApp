package com.zireck.remotecraft.view;

import com.zireck.remotecraft.model.ServerModel;

public interface SearchServerView extends BaseView {
  void navigateToServerDetail(ServerModel serverModel);

  void showError(Exception exception);
}

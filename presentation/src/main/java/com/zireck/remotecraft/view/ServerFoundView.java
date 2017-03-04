package com.zireck.remotecraft.view;

import com.zireck.remotecraft.model.ServerModel;

public interface ServerFoundView extends BaseView {
  void renderServer(ServerModel serverModel);
  void showError(String errorMessage);
  void navigateBack(boolean isSuccess, ServerModel serverModel);
}

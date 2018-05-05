package com.remotecraft.app.view;

import com.remotecraft.app.model.ServerModel;

public interface ServerFoundView extends BaseView {
  void renderServer(ServerModel serverModel);
  void showError(String errorMessage);
  void navigateBack(boolean isSuccess, ServerModel serverModel);
}

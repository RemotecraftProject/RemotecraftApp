package com.remotecraft.app.view;

import com.remotecraft.app.model.ServerModel;

public interface ServerSearchView extends BaseView {
  void navigateToServerDetail(ServerModel serverModel);
  void navigateToMainScreen(ServerModel serverModel);
  void showMessage(String message);
  void showError(Exception exception);
  void closeMenu();
  void showLoading();
  void hideLoading();
  void startQrScanner();
  void stopQrScanner();
  void showEnterNetworkAddressDialog();
}

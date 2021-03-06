package com.remotecraft.app.presenter;

import android.support.annotation.NonNull;
import com.remotecraft.app.model.ServerModel;
import com.remotecraft.app.view.ServerFoundView;

public class ServerFoundPresenter extends BasePresenter<ServerFoundView> {

  private ServerModel serverModel;

  public ServerFoundPresenter() {

  }

  public void setServer(ServerModel serverModel) {
    this.serverModel = serverModel;
    showServerInView(serverModel);
  }

  public void resume() {
    showServerInView(serverModel);
  }

  public void onClickAccept() {
    checkViewAttached();
    getView().navigateBack(true, serverModel);
  }

  public void onClickCancel() {
    checkViewAttached();
    getView().navigateBack(false, serverModel);
  }

  private void showServerInView(ServerModel serverModel) {
    if (serverModel == null) {
      showErrorServerInView();
    } else {
      showValidServerInView(serverModel);
    }
  }

  private void showErrorServerInView() {
    checkViewAttached();
    getView().showError("Invalid Server");
  }

  private void showValidServerInView(@NonNull ServerModel serverModel) {
    checkViewAttached();
    getView().renderServer(serverModel);
  }
}

package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.view.SearchServerView;
import timber.log.Timber;

public class SearchServerPresenter implements Presenter<SearchServerView> {

  private SearchServerView view;
  private MaybeInteractor getWifiStateInteractor;
  private MaybeInteractor searchServerInteractor;
  private ServerModelDataMapper serverModelDataMapper;
  private boolean isScanningWifi = false;
  private boolean isScanningQr = false;

  public SearchServerPresenter(MaybeInteractor getWifiStateInteractor,
      MaybeInteractor searchServerInteractor, ServerModelDataMapper serverModelDataMapper) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.serverModelDataMapper = serverModelDataMapper;
  }

  @Override public void setView(@NonNull SearchServerView view) {
    this.view = view;
  }

  @Override public void resume() {
    if (!isScanningWifi && !isScanningQr) {
      view.hideLoading();
    }

    if (isScanningQr) {
      view.startQrScanner();
    }
  }

  @Override public void pause() {
    view.stopQrScanner();
  }

  @Override public void destroy() {
    searchServerInteractor.dispose();
  }

  public void onClickCloseCamera() {
    if (isScanningQr) {
      isScanningQr = false;
      view.hideLoading();
      view.stopQrScanner();
    }
  }

  public void onClickWifi() {
    if (isScanningWifi || isScanningQr) {
      return;
    }

    view.closeMenu();
    view.showLoading();
    isScanningWifi = true;
    searchServerInteractor.execute(new SearchServerObserver());
  }

  public void onClickQrCode() {
    if (isScanningWifi || isScanningQr) {
      return;
    }

    view.closeMenu();
    view.showLoading();
    isScanningQr = true;
    view.startQrScanner();
  }

  public void onReadQrCode(String qrCode) {
    isScanningQr = false;
    view.hideLoading();
    view.stopQrScanner();
    view.showMessage("Read code: " + qrCode);
  }

  private final class SearchServerObserver extends DefaultMaybeObserver<Server> {
    @Override public void onSuccess(Server server) {
      isScanningWifi = false;
      Timber.d("Received Server: %s", server.getWorldName());

      view.hideLoading();
      ServerModel serverModel = serverModelDataMapper.transform(server);
      view.navigateToServerDetail(serverModel);
    }

    @Override public void onError(Throwable e) {
      isScanningWifi = false;
      view.hideLoading();
      view.showError((Exception) e);
    }
  }
}

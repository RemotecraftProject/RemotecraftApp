package com.zireck.remotecraft.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.SearchServerForIpInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.model.NetworkAddressModel;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.tools.UriParser;
import com.zireck.remotecraft.view.SearchServerView;
import timber.log.Timber;

public class SearchServerPresenter implements Presenter<SearchServerView> {

  private SearchServerView view;
  private final MaybeInteractor getWifiStateInteractor;
  private final SearchServerInteractor searchServerInteractor;
  private final SearchServerForIpInteractor searchServerForIpInteractor;
  private final ServerModelDataMapper serverModelDataMapper;
  private final UriParser uriParser;
  private boolean isScanningWifi = false;
  private boolean isScanningQr = false;

  public SearchServerPresenter(MaybeInteractor getWifiStateInteractor,
      SearchServerInteractor searchServerInteractor,
      SearchServerForIpInteractor searchServerForIpInteractor,
      ServerModelDataMapper serverModelDataMapper, UriParser uriParser) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.searchServerForIpInteractor = searchServerForIpInteractor;
    this.serverModelDataMapper = serverModelDataMapper;
    this.uriParser = uriParser;
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
    scanWifi();
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

  public void onClickIp() {
    view.showNetworkAddressDialog();
  }

  public void onReadQrCode(String qrCode) {
    isScanningQr = false;
    view.hideLoading();
    view.stopQrScanner();

    if (qrCode == null || qrCode.length() <= 0) {
      view.showError(new IllegalArgumentException("Couldn't read QR Code"));
    }

    NetworkAddressModel networkAddressModel = processReadQrCode(qrCode);
    if (networkAddressModel == null) {
      view.showError(new RuntimeException("Couldn't determine server host"));
    } else {
      scanWifi(networkAddressModel);
    }
  }

  private void scanWifi() {
    scanWifi(null);
  }

  private void scanWifi(NetworkAddressModel networkAddressModel) {
    if (isScanningWifi || isScanningQr) {
      return;
    }

    view.closeMenu();
    view.showLoading();
    isScanningWifi = true;
    if (networkAddressModel == null) {
      searchServerInteractor.execute(new SearchServerObserver());
    } else {
      // TODO pass over NetworkAddress to the domain layer
      searchServerForIpInteractor.setIpAddress(networkAddressModel.getIp());
      searchServerForIpInteractor.execute(new SearchServerObserver());
    }
  }

  private NetworkAddressModel processReadQrCode(String qrCode) {
    Uri uri = uriParser.parse(qrCode);
    String ip = uriParser.getQueryParameter(uri, "ip");
    String port = uriParser.getQueryParameter(uri, "port");
    if (ip == null || ip.length() <= 0 || port == null || port.length() <= 0) {
      return null;
    }

    return new NetworkAddressModel.Builder()
        .with(ip)
        .and(port)
        .build();
  }

  public void onEnterNetworkAddress(String ip, String port) {
    if (isScanningWifi || isScanningQr) {
      return;
    }

    view.closeMenu();
    view.showLoading();
    isScanningWifi = true;
    searchServerForIpInteractor.setIpAddress(ip);
    searchServerForIpInteractor.execute(new SearchServerObserver());
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

package com.zireck.remotecraft.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.zireck.remotecraft.domain.interactor.RequestPermissionInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerForIpInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.domain.observer.DefaultSingleObserver;
import com.zireck.remotecraft.mapper.NetworkAddressModelDataMapper;
import com.zireck.remotecraft.mapper.PermissionModelDataMapper;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.model.NetworkAddressModel;
import com.zireck.remotecraft.model.PermissionModel;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.navigation.Navigator;
import com.zireck.remotecraft.tools.UriParser;
import com.zireck.remotecraft.view.ServerSearchView;
import timber.log.Timber;

public class ServerSearchPresenter implements Presenter<ServerSearchView> {

  private static final String QUERY_PARAMETER_IP = "ip";
  private static final String QUERY_PARAMETER_PORT = "port";

  private ServerSearchView view;
  private final MaybeInteractor getWifiStateInteractor;
  private final SearchServerInteractor searchServerInteractor;
  private final SearchServerForIpInteractor searchServerForIpInteractor;
  private final CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor;
  private final RequestPermissionInteractor requestPermissionInteractor;
  private final PermissionModel cameraPermissionModel;
  private final ServerModelDataMapper serverModelDataMapper;
  private final NetworkAddressModelDataMapper networkAddressModelDataMapper;
  private final PermissionModelDataMapper permissionModelDataMapper;
  private final UriParser uriParser;
  private boolean isScanningWifi = false;
  private boolean isScanningQr = false;

  public ServerSearchPresenter(MaybeInteractor getWifiStateInteractor,
      SearchServerInteractor searchServerInteractor,
      SearchServerForIpInteractor searchServerForIpInteractor,
      CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor, RequestPermissionInteractor requestPermissionInteractor,
      PermissionModel cameraPermissionModel, ServerModelDataMapper serverModelDataMapper,
      NetworkAddressModelDataMapper networkAddressModelDataMapper,
      PermissionModelDataMapper permissionModelDataMapper, UriParser uriParser) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.searchServerForIpInteractor = searchServerForIpInteractor;
    this.checkIfPermissionGrantedInteractor = checkIfPermissionGrantedInteractor;
    this.requestPermissionInteractor = requestPermissionInteractor;
    this.cameraPermissionModel = cameraPermissionModel;
    this.serverModelDataMapper = serverModelDataMapper;
    this.networkAddressModelDataMapper = networkAddressModelDataMapper;
    this.permissionModelDataMapper = permissionModelDataMapper;
    this.uriParser = uriParser;
  }

  @Override public void attachView(@NonNull ServerSearchView view) {
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
    view.closeMenu();
    view.stopQrScanner();
  }

  @Override public void destroy() {
    searchServerInteractor.dispose();
    searchServerForIpInteractor.dispose();
  }

  public void onNavigationResult(int requestCode, boolean isSuccess, ServerModel serverModel) {
    if (requestCode == Navigator.RequestCode.SERVER_FOUND) {
      if (isSuccess) {
        view.navigateToMainScreen(serverModel);
      }
    }
  }

  public void onClickScanWifi() {
    scanWifi(null);
  }

  public void onClickScanQrCode() {
    if (anyActionCurrentlyInProgress()) {
      view.showError(new RuntimeException("Action already in progress."));
      return;
    }

    view.closeMenu();
    checkIfPermissionGranted(cameraPermissionModel);
  }

  public void onClickEnterNetworkAddress() {
    view.closeMenu();
    view.showEnterNetworkAddressDialog();
  }

  public void onClickCloseCamera() {
    if (isScanningQr) {
      isScanningQr = false;
      stopQrScanning();
    }
  }

  public void onReadQrCode(String qrCode) {
    isScanningQr = false;
    stopQrScanning();

    if (qrCode == null || qrCode.isEmpty()) {
      view.showError(new IllegalArgumentException("Couldn't read QR Code."));
      return;
    }

    NetworkAddressModel networkAddressModel = getNetworkAddressFromQrCode(qrCode);
    if (networkAddressModel == null) {
      view.showError(new RuntimeException("Couldn't determine server address."));
    } else {
      scanWifi(networkAddressModel);
    }
  }

  public void onEnterNetworkAddress(String ip, String port) {
    if (anyActionCurrentlyInProgress()) {
      view.showError(new RuntimeException("Action already in progress."));
      return;
    }

    if (!areValidIpAndPort(ip, port)) {
      return;
    }

    NetworkAddressModel networkAddressModel = new NetworkAddressModel.Builder()
        .with(ip)
        .and(Integer.parseInt(port))
        .build();

    view.closeMenu();
    view.showLoading();
    isScanningWifi = true;

    NetworkAddress networkAddress =
        networkAddressModelDataMapper.transformInverse(networkAddressModel);
    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    searchServerForIpInteractor.execute(new SearchServerObserver(), params);
  }

  private void scanWifi(NetworkAddressModel networkAddressModel) {
    if (anyActionCurrentlyInProgress()) {
      view.showError(new RuntimeException("Action already in progress."));
      return;
    }

    view.closeMenu();
    view.showLoading();
    isScanningWifi = true;

    if (networkAddressModel == null) {
      searchServerInteractor.execute(new SearchServerObserver(), null);
    } else {
      NetworkAddress networkAddress =
          networkAddressModelDataMapper.transformInverse(networkAddressModel);
      SearchServerForIpInteractor.Params params =
          SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
      searchServerForIpInteractor.execute(new SearchServerObserver(), params);
    }
  }

  private NetworkAddressModel getNetworkAddressFromQrCode(String qrCode) {
    Uri uri = uriParser.parse(qrCode);
    String ip = uriParser.getQueryParameter(uri, QUERY_PARAMETER_IP);
    String port = uriParser.getQueryParameter(uri, QUERY_PARAMETER_PORT);

    if (!areValidIpAndPort(ip, port)) {
      return null;
    }

    return new NetworkAddressModel.Builder()
        .with(ip)
        .and(Integer.parseInt(port))
        .build();
  }

  private boolean anyActionCurrentlyInProgress() {
    return isScanningWifi || isScanningQr;
  }

  private boolean areValidIpAndPort(String ip, String port) {
    if (ip == null || port == null) {
      view.showError(new IllegalArgumentException("Invalid Network Address."));
      return false;
    }

    ip = ip.trim();
    port = port.trim();
    if (ip.isEmpty() || port.isEmpty()) {
      view.showError(new IllegalArgumentException("Invalid Network Address."));
      return false;
    }

    try {
      Integer.parseInt(port);
    } catch (NumberFormatException e) {
      Timber.e(e.getMessage());
      view.showError(new IllegalArgumentException("Invalid port."));
      return false;
    }

    return true;
  }

  private void startQrScanning() {
    view.closeMenu();
    view.showLoading();
    isScanningQr = true;
    view.startQrScanner();
  }

  private void stopQrScanning() {
    view.hideLoading();
    view.stopQrScanner();
  }

  private void checkIfPermissionGranted(PermissionModel permissionModel) {
    view.showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    CheckIfPermissionGrantedInteractor.Params params =
        CheckIfPermissionGrantedInteractor.Params.forPermission(permission);
    checkIfPermissionGrantedInteractor.execute(new IsPermissionGrantedObserver(), params);
  }

  private void requestPermission(PermissionModel permissionModel) {
    view.showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    RequestPermissionInteractor.Params params = RequestPermissionInteractor.Params.forPermission(permission);
    requestPermissionInteractor.execute(new RequestPermissionObserver(), params);
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

  private final class IsPermissionGrantedObserver extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (granted) {
        startQrScanning();
      } else {
        requestPermission(cameraPermissionModel);
      }
    }

    @Override public void onError(Throwable e) {
      stopQrScanning();
      view.showError((Exception) e);
    }
  }

  private final class RequestPermissionObserver extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (granted) {
        startQrScanning();
      } else {
        view.hideLoading();
        //view.showMessage("Error. Permission not granted");
      }
    }

    @Override public void onError(Throwable e) {
      view.hideLoading();
      view.showError((Exception) e);
    }
  }
}

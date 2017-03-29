package com.zireck.remotecraft.presenter;

import android.net.Uri;
import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.zireck.remotecraft.domain.interactor.RequestPermissionInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultObservableObserver;
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

public class ServerSearchPresenter extends BasePresenter<ServerSearchView> {

  private static final String QUERY_PARAMETER_IP = "ip";
  private static final String QUERY_PARAMETER_PORT = "port";

  private final MaybeInteractor getWifiStateInteractor;
  private final SearchServerInteractor searchServerInteractor;
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
      CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor,
      RequestPermissionInteractor requestPermissionInteractor,
      PermissionModel cameraPermissionModel, ServerModelDataMapper serverModelDataMapper,
      NetworkAddressModelDataMapper networkAddressModelDataMapper,
      PermissionModelDataMapper permissionModelDataMapper, UriParser uriParser) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.checkIfPermissionGrantedInteractor = checkIfPermissionGrantedInteractor;
    this.requestPermissionInteractor = requestPermissionInteractor;
    this.cameraPermissionModel = cameraPermissionModel;
    this.serverModelDataMapper = serverModelDataMapper;
    this.networkAddressModelDataMapper = networkAddressModelDataMapper;
    this.permissionModelDataMapper = permissionModelDataMapper;
    this.uriParser = uriParser;
  }

  public void resume() {
    checkViewAttached();

    if (!isScanningWifi && !isScanningQr) {
      getView().hideLoading();
    }

    if (isScanningQr) {
      getView().startQrScanner();
    }
  }

  public void pause() {
    checkViewAttached();
    getView().closeMenu();
    getView().stopQrScanner();
  }

  public void stop() {
    searchServerInteractor.dispose();
  }

  public void destroy() {

  }

  public void onNavigationResult(int requestCode, boolean isSuccess, ServerModel serverModel) {
    if (requestCode == Navigator.RequestCode.SERVER_FOUND) {
      if (isSuccess) {
        checkViewAttached();
        getView().navigateToMainScreen(serverModel);
      }
    }
  }

  public void onClickScanWifi() {
    scanWifi(null);
  }

  public void onClickScanQrCode() {
    checkViewAttached();

    if (anyActionCurrentlyInProgress()) {
      getView().showError(new RuntimeException("Action already in progress."));
      return;
    }

    getView().closeMenu();
    checkIfPermissionGranted(cameraPermissionModel);
  }

  public void onClickEnterNetworkAddress() {
    checkViewAttached();
    getView().closeMenu();
    getView().showEnterNetworkAddressDialog();
  }

  public void onClickCloseCamera() {
    if (isScanningQr) {
      isScanningQr = false;
      stopQrScanning();
    }
  }

  public void onReadQrCode(String qrCode) {
    checkViewAttached();

    isScanningQr = false;
    stopQrScanning();

    if (qrCode == null || qrCode.isEmpty()) {
      getView().showError(new IllegalArgumentException("Couldn't read QR Code."));
      return;
    }

    NetworkAddressModel networkAddressModel = getNetworkAddressFromQrCode(qrCode);
    if (networkAddressModel == null) {
      getView().showError(new RuntimeException("Couldn't determine server address."));
    } else {
      scanWifi(networkAddressModel);
    }
  }

  public void onEnterNetworkAddress(String ip, String port) {
    checkViewAttached();

    if (anyActionCurrentlyInProgress()) {
      getView().showError(new RuntimeException("Action already in progress."));
      return;
    }

    if (!areValidIpAndPort(ip, port)) {
      return;
    }

    NetworkAddressModel networkAddressModel = NetworkAddressModel.builder()
        .ip(ip)
        .port(Integer.parseInt(port))
        .build();

    getView().closeMenu();
    getView().showLoading();
    isScanningWifi = true;

    NetworkAddress networkAddress =
        networkAddressModelDataMapper.transformInverse(networkAddressModel);
    SearchServerInteractor.Params params =
        SearchServerInteractor.Params.forNetworkAddress(networkAddress);
    searchServerInteractor.execute(new SearchServerObserver(), params);
  }

  private void scanWifi(NetworkAddressModel networkAddressModel) {
    checkViewAttached();

    if (anyActionCurrentlyInProgress()) {
      getView().showError(new RuntimeException("Action already in progress."));
      return;
    }

    getView().closeMenu();
    getView().showLoading();
    isScanningWifi = true;

    SearchServerInteractor.Params params;
    if (networkAddressModel == null) {
      params = SearchServerInteractor.Params.empty();
    } else {
      NetworkAddress networkAddress =
          networkAddressModelDataMapper.transformInverse(networkAddressModel);
      params = SearchServerInteractor.Params.forNetworkAddress(networkAddress);
    }
    searchServerInteractor.execute(new SearchServerObserver(), params);
  }

  private NetworkAddressModel getNetworkAddressFromQrCode(String qrCode) {
    Uri uri = uriParser.parse(qrCode);
    String ip = uriParser.getQueryParameter(uri, QUERY_PARAMETER_IP);
    String port = uriParser.getQueryParameter(uri, QUERY_PARAMETER_PORT);

    if (!areValidIpAndPort(ip, port)) {
      return null;
    }

    return NetworkAddressModel.builder()
        .ip(ip)
        .port(Integer.parseInt(port))
        .build();
  }

  private boolean anyActionCurrentlyInProgress() {
    return isScanningWifi || isScanningQr;
  }

  private boolean areValidIpAndPort(String ip, String port) {
    checkViewAttached();

    if (ip == null || port == null) {
      getView().showError(new IllegalArgumentException("Invalid Network Address."));
      return false;
    }

    ip = ip.trim();
    port = port.trim();
    if (ip.isEmpty() || port.isEmpty()) {
      getView().showError(new IllegalArgumentException("Invalid Network Address."));
      return false;
    }

    try {
      Integer.parseInt(port);
    } catch (NumberFormatException e) {
      Timber.e(e.getMessage());
      getView().showError(new IllegalArgumentException("Invalid port."));
      return false;
    }

    return true;
  }

  private void startQrScanning() {
    checkViewAttached();
    getView().closeMenu();
    getView().showLoading();
    isScanningQr = true;
    getView().startQrScanner();
  }

  private void stopQrScanning() {
    checkViewAttached();
    getView().hideLoading();
    getView().stopQrScanner();
  }

  private void checkIfPermissionGranted(PermissionModel permissionModel) {
    checkViewAttached();
    getView().showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    CheckIfPermissionGrantedInteractor.Params params =
        CheckIfPermissionGrantedInteractor.Params.forPermission(permission);
    checkIfPermissionGrantedInteractor.execute(new IsPermissionGrantedObserver(), params);
  }

  private void requestPermission(PermissionModel permissionModel) {
    checkViewAttached();
    getView().showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    RequestPermissionInteractor.Params params = RequestPermissionInteractor.Params.forPermission(permission);
    requestPermissionInteractor.execute(new RequestPermissionObserver(), params);
  }

  private final class SearchServerObserver extends DefaultObservableObserver<Server> {
    @Override public void onNext(Server server) {
      isScanningWifi = false;
      Timber.d("Received Server: %s", server.worldName());

      if (!isViewAttached()) {
        return;
      }

      getView().hideLoading();
      ServerModel serverModel = serverModelDataMapper.transform(server);
      getView().navigateToServerDetail(serverModel);
    }

    @Override public void onError(Throwable e) {
      isScanningWifi = false;

      if (!isViewAttached()) {
        return;
      }
      getView().hideLoading();
      getView().showError((Exception) e);
    }
  }

  private final class IsPermissionGrantedObserver extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (!isViewAttached()) {
        return;
      }

      if (granted) {
        startQrScanning();
      } else {
        requestPermission(cameraPermissionModel);
      }
    }

    @Override public void onError(Throwable e) {
      if (!isViewAttached()) {
        return;
      }
      stopQrScanning();
      getView().showError((Exception) e);
    }
  }

  private final class RequestPermissionObserver extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (!isViewAttached()) {
        return;
      }

      if (granted) {
        startQrScanning();
      } else {
        getView().hideLoading();
        //view.showMessage("Error. Permission not granted");
      }
    }

    @Override public void onError(Throwable e) {
      if (!isViewAttached()) {
        return;
      }
      getView().hideLoading();
      getView().showError((Exception) e);
    }
  }
}

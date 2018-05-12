package com.remotecraft.app.presenter;

import android.net.Uri;
import com.remotecraft.app.dagger.qualifiers.PermissionAccessWifiState;
import com.remotecraft.app.dagger.qualifiers.PermissionCamera;
import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.remotecraft.app.domain.interactor.GetWifiStateInteractor;
import com.remotecraft.app.domain.interactor.RequestPermissionInteractor;
import com.remotecraft.app.domain.interactor.SearchServerInteractor;
import com.remotecraft.app.domain.interactor.base.MaybeInteractor;
import com.remotecraft.app.domain.observer.DefaultMaybeObserver;
import com.remotecraft.app.domain.observer.DefaultObservableObserver;
import com.remotecraft.app.domain.observer.DefaultSingleObserver;
import com.remotecraft.app.domain.util.JsonDeserializer;
import com.remotecraft.app.mapper.NetworkAddressModelDataMapper;
import com.remotecraft.app.mapper.PermissionModelDataMapper;
import com.remotecraft.app.mapper.ServerModelDataMapper;
import com.remotecraft.app.model.NetworkAddressModel;
import com.remotecraft.app.model.PermissionModel;
import com.remotecraft.app.model.ServerModel;
import com.remotecraft.app.navigation.Navigator;
import com.remotecraft.app.tools.UriParser;
import com.remotecraft.app.view.ServerSearchView;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public class ServerSearchPresenter extends BasePresenter<ServerSearchView> {

  private static final String QUERY_PARAMETER_IP = "ip";
  private static final String QUERY_PARAMETER_PORT = "port";

  private final MaybeInteractor getWifiStateInteractor;
  private final SearchServerInteractor searchServerInteractor;
  private final CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor;
  private final RequestPermissionInteractor requestPermissionInteractor;
  @PermissionAccessWifiState private final PermissionModel accessWifiStatePermissionModel;
  @PermissionCamera private final PermissionModel cameraPermissionModel;
  private final JsonDeserializer<Server> serverDeserializer;
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
      PermissionModel accessWifiStatePermissionModel, PermissionModel cameraPermissionModel,
      JsonDeserializer<Server> serverDeserializer, ServerModelDataMapper serverModelDataMapper,
      NetworkAddressModelDataMapper networkAddressModelDataMapper,
      PermissionModelDataMapper permissionModelDataMapper, UriParser uriParser) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.checkIfPermissionGrantedInteractor = checkIfPermissionGrantedInteractor;
    this.requestPermissionInteractor = requestPermissionInteractor;
    this.accessWifiStatePermissionModel = accessWifiStatePermissionModel;
    this.cameraPermissionModel = cameraPermissionModel;
    this.serverDeserializer = serverDeserializer;
    this.serverModelDataMapper = serverModelDataMapper;
    this.networkAddressModelDataMapper = networkAddressModelDataMapper;
    this.permissionModelDataMapper = permissionModelDataMapper;
    this.uriParser = uriParser;
  }

  public void resume() {
    checkViewAttached();

    checkIfPermissionGranted(accessWifiStatePermissionModel,
        new IsAccessWifiStatePermissionGrantedObserver());

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
    getWifiStateInteractor.dispose();
    searchServerInteractor.dispose();
    checkIfPermissionGrantedInteractor.dispose();
    requestPermissionInteractor.dispose();
  }

  public void onNavigationResult(int requestCode, boolean isSuccess, ServerModel serverModel) {
    if (requestCode == Navigator.RequestCode.SERVER_FOUND) {
      if (isSuccess) {
        onServerFound(serverModel);
      }
    }
  }

  public void onServerFound(ServerModel serverModel) {
    checkViewAttached();
    getView().navigateToMainScreen(serverModel);
  }

  public void onSerializedDomainServerFound(String serializedDomainServer) {
    checkViewAttached();

    Server server = serverDeserializer.deserialize(serializedDomainServer);

    ServerModel serverModel = serverModelDataMapper.transform(server);
    getView().navigateToServerDetail(serverModel);
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
    checkIfPermissionGranted(cameraPermissionModel, new IsCameraPermissionGrantedObserver());
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

  private void checkIfPermissionGranted(PermissionModel permissionModel,
      DisposableSingleObserver observer) {
    checkViewAttached();
    getView().showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    CheckIfPermissionGrantedInteractor.Params params =
        CheckIfPermissionGrantedInteractor.Params.forPermission(permission);
    checkIfPermissionGrantedInteractor.execute(observer, params);
  }

  private void requestPermission(PermissionModel permissionModel,
      DisposableSingleObserver observer) {
    checkViewAttached();
    getView().showLoading();
    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);
    RequestPermissionInteractor.Params params =
        RequestPermissionInteractor.Params.forPermission(permission);
    requestPermissionInteractor.execute(observer, params);
  }

  private void checkWifiStrenght() {
    getWifiStateInteractor.execute(new CheckWifiStrenghtObserver(),
        GetWifiStateInteractor.Params.emptyParams());
  }

  private final class SearchServerObserver extends DefaultObservableObserver<Server> {
    @Override
    public void onNext(Server server) {
      isScanningWifi = false;
      Timber.d("Received Server: %s", server.worldName());

      if (!isViewAttached()) {
        return;
      }

      getView().hideLoading();
      ServerModel serverModel = serverModelDataMapper.transform(server);
      getView().navigateToServerDetail(serverModel);
    }

    @Override
    public void onError(Throwable e) {
      isScanningWifi = false;

      if (!isViewAttached()) {
        return;
      }
      getView().hideLoading();
      getView().showError((Exception) e);
    }
  }

  private final class IsAccessWifiStatePermissionGrantedObserver
      extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (!isViewAttached()) return;

      if (granted) {
        checkWifiStrenght();
      } else {
        requestPermission(accessWifiStatePermissionModel,
            new RequestAccessWifiStatePermissionObserver());
      }
    }

    @Override public void onError(Throwable e) {
      if (!isViewAttached()) {
        return;
      }
      getView().showError((Exception) e);
    }
  }

  private final class IsCameraPermissionGrantedObserver extends DefaultSingleObserver<Boolean> {
    @Override
    public void onSuccess(Boolean granted) {
      if (!isViewAttached()) return;

      if (granted) {
        startQrScanning();
      } else {
        requestPermission(cameraPermissionModel, new RequestCameraPermissionObserver());
      }
    }

    @Override
    public void onError(Throwable e) {
      if (!isViewAttached()) {
        return;
      }
      stopQrScanning();
      getView().showError((Exception) e);
    }
  }

  private final class RequestAccessWifiStatePermissionObserver
      extends DefaultSingleObserver<Boolean> {
    @Override public void onSuccess(Boolean granted) {
      if (!isViewAttached()) return;

      if (granted) {
        checkWifiStrenght();
      } else {
        getView().hideLoading();
        //view.showMessage("Error. Permission not granted");
      }
    }

    @Override public void onError(Throwable e) {
      if (!isViewAttached()) return;

      getView().hideLoading();
      getView().showError((Exception) e);
    }
  }

  private final class RequestCameraPermissionObserver extends DefaultSingleObserver<Boolean> {
    @Override
    public void onSuccess(Boolean granted) {
      if (!isViewAttached()) return;

      if (granted) {
        startQrScanning();
      } else {
        getView().hideLoading();
        //view.showMessage("Error. Permission not granted");
      }
    }

    @Override
    public void onError(Throwable e) {
      if (!isViewAttached()) return;

      getView().hideLoading();
      getView().showError((Exception) e);
    }
  }

  private final class CheckWifiStrenghtObserver extends DefaultMaybeObserver<Integer> {
    @Override public void onSuccess(Integer wifiStrenght) {
      if (!isViewAttached()) return;

      getView().showWifiState(wifiStrenght);
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }
  }
}

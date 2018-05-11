package com.remotecraft.app.dagger.modules;

import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.dagger.qualifiers.PermissionAccessWifiState;
import com.remotecraft.app.dagger.qualifiers.PermissionCamera;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.remotecraft.app.domain.interactor.GetWifiStateInteractor;
import com.remotecraft.app.domain.interactor.RequestPermissionInteractor;
import com.remotecraft.app.domain.interactor.SearchServerInteractor;
import com.remotecraft.app.domain.util.JsonDeserializer;
import com.remotecraft.app.mapper.NetworkAddressModelDataMapper;
import com.remotecraft.app.mapper.PermissionModelDataMapper;
import com.remotecraft.app.mapper.ServerModelDataMapper;
import com.remotecraft.app.model.PermissionModel;
import com.remotecraft.app.presenter.ServerFoundPresenter;
import com.remotecraft.app.presenter.ServerSearchPresenter;
import com.remotecraft.app.tools.UriParser;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

  public PresentersModule() {

  }

  @Provides @PerActivity ServerSearchPresenter provideServerSearchPresenter(
      GetWifiStateInteractor getWifiStateInteractor, SearchServerInteractor searchServerInteractor,
      CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor,
      RequestPermissionInteractor requestPermissionInteractor,
      @PermissionAccessWifiState PermissionModel accessWifiStatePermissionModel,
      @PermissionCamera PermissionModel cameraPermissionModel, JsonDeserializer<Server> serverDeserializer,
      ServerModelDataMapper serverModelDataMapper, NetworkAddressModelDataMapper networkAddressModelDataMapper,
      PermissionModelDataMapper permissionModelDataMapper, UriParser uriParser) {
    return new ServerSearchPresenter(getWifiStateInteractor, searchServerInteractor,
        checkIfPermissionGrantedInteractor, requestPermissionInteractor, accessWifiStatePermissionModel,
        cameraPermissionModel, serverDeserializer, serverModelDataMapper, networkAddressModelDataMapper,
        permissionModelDataMapper, uriParser);
  }

  @Provides @PerActivity ServerFoundPresenter provideServerFoundPresenter() {
    return new ServerFoundPresenter();
  }
}

package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.qualifiers.PerActivity;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.RequestPermissionInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.domain.util.JsonDeserializer;
import com.zireck.remotecraft.mapper.NetworkAddressModelDataMapper;
import com.zireck.remotecraft.mapper.PermissionModelDataMapper;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.model.PermissionModel;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import com.zireck.remotecraft.presenter.ServerSearchPresenter;
import com.zireck.remotecraft.tools.UriParser;
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
      PermissionModel cameraPermissionModel, JsonDeserializer<Server> serverDeserializer,
      ServerModelDataMapper serverModelDataMapper,
      NetworkAddressModelDataMapper networkAddressModelDataMapper,
      PermissionModelDataMapper permissionModelDataMapper, UriParser uriParser) {
    return new ServerSearchPresenter(getWifiStateInteractor, searchServerInteractor,
        checkIfPermissionGrantedInteractor, requestPermissionInteractor, cameraPermissionModel,
        serverDeserializer, serverModelDataMapper, networkAddressModelDataMapper,
        permissionModelDataMapper, uriParser);
  }

  @Provides @PerActivity ServerFoundPresenter provideServerFoundPresenter() {
    return new ServerFoundPresenter();
  }
}

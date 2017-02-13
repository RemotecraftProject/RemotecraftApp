package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerForIpInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.mapper.NetworkAddressModelDataMapper;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.presenter.SearchServerPresenter;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import com.zireck.remotecraft.tools.UriParser;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

  public PresentersModule() {

  }

  @Provides @PerActivity SearchServerPresenter provideSearchServerPresenter(
      GetWifiStateInteractor getWifiStateInteractor, SearchServerInteractor searchServerInteractor,
      SearchServerForIpInteractor searchServerForIpInteractor,
      ServerModelDataMapper serverModelDataMapper,
      NetworkAddressModelDataMapper networkAddressModelDataMapper, UriParser uriParser) {
    return new SearchServerPresenter(getWifiStateInteractor, searchServerInteractor,
        searchServerForIpInteractor, serverModelDataMapper, networkAddressModelDataMapper,
        uriParser);
  }

  @Provides @PerActivity ServerFoundPresenter provideServerFoundPresenter() {
    return new ServerFoundPresenter();
  }
}

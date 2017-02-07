package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.presenter.SearchServerPresenter;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

  public PresentersModule() {

  }

  @Provides @PerActivity SearchServerPresenter provideSearchWorldPresenter(
      GetWifiStateInteractor getWifiStateInteractor, SearchServerInteractor searchServerInteractor,
      ServerModelDataMapper serverModelDataMapper) {
    return new SearchServerPresenter(getWifiStateInteractor, searchServerInteractor,
        serverModelDataMapper);
  }

  @Provides @PerActivity ServerFoundPresenter provideWorldFoundPresenter() {
    return new ServerFoundPresenter();
  }
}

package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.SearchWorldInteractor;
import com.zireck.remotecraft.presenter.SearchWorldPresenter;
import com.zireck.remotecraft.presenter.WorldFoundPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

  public PresentersModule() {

  }

  @Provides @PerActivity SearchWorldPresenter provideSearchWorldPresenter(
      GetWifiStateInteractor getWifiStateInteractor,
      SearchWorldInteractor searchWorldInteractor) {
    return new SearchWorldPresenter(getWifiStateInteractor, searchWorldInteractor);
  }

  @Provides @PerActivity WorldFoundPresenter provideWorldFoundPresenter() {
    return new WorldFoundPresenter();
  }
}

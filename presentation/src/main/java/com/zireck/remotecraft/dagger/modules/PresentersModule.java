package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.SearchWorldInteractor;
import com.zireck.remotecraft.presenter.SearchPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

  public PresentersModule() {

  }

  @Provides @PerActivity
  SearchPresenter provideSearchPresenter(
      GetWifiStateInteractor getWifiStateInteractor,
      SearchWorldInteractor searchWorldInteractor) {
    return new SearchPresenter(getWifiStateInteractor, searchWorldInteractor);
  }
}

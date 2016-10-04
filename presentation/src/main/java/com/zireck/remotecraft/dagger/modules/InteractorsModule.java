package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.Interactor;
import com.zireck.remotecraft.domain.interactor.SearchWorldInteractor;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module
public class InteractorsModule {

  public InteractorsModule() {

  }

  @Provides @PerActivity
  @Named("wifiState") Interactor provideGetWifiStateInteractor(ReceiversProvider receiversProvider,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetWifiStateInteractor(receiversProvider, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity
  @Named("searchWorld")
  Interactor provideSearchWorldInteractor(NetworkDataProvider networkDataProvider,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new SearchWorldInteractor(networkDataProvider, threadExecutor, postExecutionThread);
  }
}

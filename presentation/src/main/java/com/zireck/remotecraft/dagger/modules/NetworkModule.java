package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.Interactor;
import com.zireck.remotecraft.domain.interactor.SearchWorldInteractor;
import com.zireck.remotecraft.domain.repository.NetworkRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module
public class NetworkModule {

  public NetworkModule() {

  }

  @Provides @PerActivity
  @Named("searchWorld")
  Interactor provideSearchWorldInteractor(NetworkRepository networkRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new SearchWorldInteractor(networkRepository, threadExecutor, postExecutionThread);
  }
}

package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.UiThread;
import com.zireck.remotecraft.data.executor.JobExecutor;
import com.zireck.remotecraft.data.repository.NetworkDataRepository;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.repository.NetworkRepository;
import com.zireck.remotecraft.navigation.Navigator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {
  private final RemotecraftApp application;

  public ApplicationModule(RemotecraftApp application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UiThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  Navigator provideNavigator() {
    return new Navigator();
  }

  @Provides @Singleton
  NetworkRepository provideNetworkRepository(NetworkDataRepository networkDataRepository) {
    return networkDataRepository;
  }
}

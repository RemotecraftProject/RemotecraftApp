package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.UiThread;
import com.zireck.remotecraft.data.executor.JobExecutor;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.manager.NetworkManager;
import com.zireck.remotecraft.domain.manager.ReceiversManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkDataManager;
import com.zireck.remotecraft.infrastructure.manager.ReceiversDataManager;
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

  @Provides @Singleton NetworkManager provideNetworkRepository(NetworkDataManager networkDataManager) {
    return networkDataManager;
  }

  @Provides @Singleton ReceiversManager provideReceiversManager(ReceiversDataManager receiversDataManager) {
    return receiversDataManager;
  }
}

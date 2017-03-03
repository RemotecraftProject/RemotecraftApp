package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.UiThread;
import com.zireck.remotecraft.data.executor.JobExecutor;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import com.zireck.remotecraft.infrastructure.provider.ReceiversDataProvider;
import com.zireck.remotecraft.navigation.Navigator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@SuppressWarnings("WeakerAccess")
@Module
public class ApplicationModule {
  private final RemotecraftApp application;

  public ApplicationModule(RemotecraftApp application) {
    this.application = application;
  }

  @Provides @Singleton public Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton public PostExecutionThread providePostExecutionThread(UiThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton public Navigator provideNavigator() {
    return new Navigator();
  }

  @Provides @Singleton
  public ReceiversProvider provideReceiversProvider(ReceiversDataProvider receiversDataProvider) {
    return receiversDataProvider;
  }
}

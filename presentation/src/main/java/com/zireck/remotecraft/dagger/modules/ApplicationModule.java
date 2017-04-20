package com.zireck.remotecraft.dagger.modules;

import android.content.Context;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.UiThread;
import com.zireck.remotecraft.dagger.qualifiers.PlayerAvatarSize;
import com.zireck.remotecraft.dagger.qualifiers.PlayerAvatarUrl;
import com.zireck.remotecraft.dagger.qualifiers.ServerSearchActivityClass;
import com.zireck.remotecraft.data.executor.JobExecutor;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NotificationActionProvider;
import com.zireck.remotecraft.domain.provider.ReceiverActionProvider;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.AndroidNotificationManager;
import com.zireck.remotecraft.infrastructure.provider.NotificationDataProvider;
import com.zireck.remotecraft.infrastructure.provider.ReceiverDataProvider;
import com.zireck.remotecraft.infrastructure.tool.ImageLoader;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import com.zireck.remotecraft.navigation.Navigator;
import com.zireck.remotecraft.view.activity.ServerSearchActivity;
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

  @Provides @Singleton @ServerSearchActivityClass public Class provideServerSearchActivityClass() {
    return ServerSearchActivity.class;
  }

  @Provides @Singleton public ReceiverActionProvider provideReceiversProvider(
      ReceiverDataProvider receiversDataProvider) {
    return receiversDataProvider;
  }

  @Provides @Singleton NotificationActionProvider provideNotificationProvider(
      NotificationDataProvider notificationDataProvider) {
    return notificationDataProvider;
  }

  @Provides @Singleton AndroidNotificationManager provideAndroidNotificationManager(Context context,
      ImageLoader imageLoader, @ServerSearchActivityClass Class serverSearchActivityClass,
      ServerEntityDataMapper serverEntityDataMapper, JsonSerializer jsonSerializer,
      @PlayerAvatarUrl String playerAvatarUrl, @PlayerAvatarSize int playerAvatarSize) {
    return new AndroidNotificationManager(context, imageLoader, serverSearchActivityClass,
        serverEntityDataMapper, jsonSerializer, playerAvatarUrl, playerAvatarSize);
  }
}

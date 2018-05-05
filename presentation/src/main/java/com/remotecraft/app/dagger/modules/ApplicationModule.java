package com.remotecraft.app.dagger.modules;

import android.content.Context;
import com.remotecraft.app.RemotecraftApp;
import com.remotecraft.app.UiThread;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarSize;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarUrl;
import com.remotecraft.app.dagger.qualifiers.ServerSearchActivityClass;
import com.remotecraft.app.data.executor.JobExecutor;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.provider.NotificationActionProvider;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import com.remotecraft.app.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.remotecraft.app.infrastructure.manager.AndroidNotificationManager;
import com.remotecraft.app.infrastructure.provider.NotificationDataProvider;
import com.remotecraft.app.infrastructure.provider.ReceiverDataProvider;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import com.remotecraft.app.navigation.Navigator;
import com.remotecraft.app.view.activity.ServerSearchActivity;
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

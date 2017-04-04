package com.zireck.remotecraft.dagger.components;

import android.content.Context;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.dagger.modules.activitymodules.ActivityBindingModule;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.dagger.modules.MappersModule;
import com.zireck.remotecraft.dagger.modules.NetworkModule;
import com.zireck.remotecraft.dagger.modules.ToolsModule;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NotificationActionProvider;
import com.zireck.remotecraft.domain.provider.ReceiverActionProvider;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import com.zireck.remotecraft.navigation.Navigator;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    ActivityBindingModule.class,
    NetworkModule.class,
    ToolsModule.class,
    MappersModule.class
})
public interface ApplicationComponent {
  RemotecraftApp inject(RemotecraftApp remotecraftApp);

  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  Navigator navigator();
  ReceiverActionProvider receiversProvider();
  NotificationActionProvider notificationProvider();
  ServerSearchManager serverSearchManager();
}

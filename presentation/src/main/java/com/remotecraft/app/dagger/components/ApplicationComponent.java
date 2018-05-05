package com.remotecraft.app.dagger.components;

import android.content.Context;
import com.remotecraft.app.RemotecraftApp;
import com.remotecraft.app.dagger.modules.MocksModule;
import com.remotecraft.app.dagger.modules.activitymodules.ActivityBindingModule;
import com.remotecraft.app.dagger.modules.ApplicationModule;
import com.remotecraft.app.dagger.modules.MappersModule;
import com.remotecraft.app.dagger.modules.NetworkModule;
import com.remotecraft.app.dagger.modules.ToolsModule;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.provider.NotificationActionProvider;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import com.remotecraft.app.infrastructure.manager.ServerSearchManager;
import com.remotecraft.app.navigation.Navigator;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    ActivityBindingModule.class,
    NetworkModule.class,
    ToolsModule.class,
    MappersModule.class,
    MocksModule.class
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

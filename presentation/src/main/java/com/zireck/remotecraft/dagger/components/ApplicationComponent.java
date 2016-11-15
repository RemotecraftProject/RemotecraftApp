package com.zireck.remotecraft.dagger.components;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.dagger.modules.MappersModule;
import com.zireck.remotecraft.dagger.modules.NetworkModule;
import com.zireck.remotecraft.dagger.modules.ToolsModule;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkInterfaceManager;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.navigation.Navigator;
import com.zireck.remotecraft.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    NetworkModule.class,
    ToolsModule.class,
    MappersModule.class
})
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  // Exposed to subgraph
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  Navigator navigator();
  NetworkProvider networkProvider();
  ReceiversProvider receiversManager();
  NetworkInterfaceManager networkInterfaceManager();
  NetworkDiscoveryManager networkDiscoveryManager();
  Gson gson();
  GsonBuilder gsonBuilder();
  MessageJsonMapper messageJsonMapper();
}

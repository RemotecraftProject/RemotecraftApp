package com.zireck.remotecraft.dagger.components;

import android.content.Context;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.repository.NetworkRepository;
import com.zireck.remotecraft.navigation.Navigator;
import com.zireck.remotecraft.view.activity.BaseActivity;
import com.zireck.remotecraft.view.activity.SearchActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  // Exposed to subgraph
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  Navigator navigator();
  NetworkRepository networkRepository();
}

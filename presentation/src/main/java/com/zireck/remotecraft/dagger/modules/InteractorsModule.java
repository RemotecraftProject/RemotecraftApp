package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.zireck.remotecraft.domain.interactor.GetWifiStateInteractor;
import com.zireck.remotecraft.domain.interactor.RequestPermissionInteractor;
import com.zireck.remotecraft.domain.interactor.SearchServerInteractor;
import com.zireck.remotecraft.domain.provider.ReceiverActionProvider;
import com.zireck.remotecraft.domain.service.NotifyServerFoundService;
import com.zireck.remotecraft.domain.service.SearchServerService;
import com.zireck.remotecraft.domain.validation.NetworkAddressValidator;
import com.zireck.remotecraft.infrastructure.provider.PermissionDataProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {

  public InteractorsModule() {

  }

  @Provides @PerActivity GetWifiStateInteractor provideGetWifiStateInteractor(
      ReceiverActionProvider receiverActionProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetWifiStateInteractor(receiverActionProvider, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity SearchServerInteractor provideSearchServerInteractor(
      SearchServerService searchServerService, NotifyServerFoundService notifyServerFoundService,
      NetworkAddressValidator networkAddressValidator, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new SearchServerInteractor(searchServerService, notifyServerFoundService,
        networkAddressValidator, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity CheckIfPermissionGrantedInteractor provideCheckIfPermissionGranted(
      PermissionDataProvider permissionDataProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new CheckIfPermissionGrantedInteractor(permissionDataProvider, threadExecutor,
        postExecutionThread);
  }

  @Provides @PerActivity RequestPermissionInteractor provideRequestPermission(
      PermissionDataProvider permissionDataProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new RequestPermissionInteractor(permissionDataProvider, threadExecutor,
        postExecutionThread);
  }
}

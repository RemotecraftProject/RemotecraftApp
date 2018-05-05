package com.remotecraft.app.dagger.modules;

import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.CheckIfPermissionGrantedInteractor;
import com.remotecraft.app.domain.interactor.GetWifiStateInteractor;
import com.remotecraft.app.domain.interactor.RequestPermissionInteractor;
import com.remotecraft.app.domain.interactor.SearchServerInteractor;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import com.remotecraft.app.domain.service.NotifyServerFoundService;
import com.remotecraft.app.domain.service.SearchServerService;
import com.remotecraft.app.domain.validation.NetworkAddressValidator;
import com.remotecraft.app.infrastructure.provider.PermissionDataProvider;
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

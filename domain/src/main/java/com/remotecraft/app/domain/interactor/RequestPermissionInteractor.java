package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.base.SingleInteractor;
import com.remotecraft.app.domain.interactor.params.BaseParams;
import com.remotecraft.app.domain.provider.PermissionActionProvider;
import io.reactivex.Single;

public class RequestPermissionInteractor
    extends SingleInteractor<Boolean, RequestPermissionInteractor.Params> {

  private final PermissionActionProvider permissionActionProvider;

  public RequestPermissionInteractor(PermissionActionProvider permissionActionProvider,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.permissionActionProvider = permissionActionProvider;
  }

  @Override protected Single<Boolean> buildReactiveStream(Params params) {
    if (params == null) {
      return Single.error(new IllegalArgumentException("Invalid Permission"));
    }

    return permissionActionProvider.request(params.permission);
  }

  public static final class Params implements BaseParams {
    private final Permission permission;

    private Params(Permission permission) {
      this.permission = permission;
    }

    public static Params forPermission(Permission permission) {
      return new Params(permission);
    }
  }
}

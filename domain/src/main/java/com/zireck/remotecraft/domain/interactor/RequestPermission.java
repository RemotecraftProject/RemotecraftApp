package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.base.SingleInteractor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import io.reactivex.Single;

public class RequestPermission extends SingleInteractor<Boolean, RequestPermission.Params> {

  private final PermissionProvider permissionProvider;

  public RequestPermission(PermissionProvider permissionProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.permissionProvider = permissionProvider;
  }

  @Override protected Single<Boolean> buildReactiveStream(Params params) {
    if (params == null) {
      return Single.error(new IllegalArgumentException("Invalid Permission"));
    }

    return permissionProvider.request(params.permission);
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

package com.remotecraft.app.domain.provider;

import com.remotecraft.app.domain.Permission;
import io.reactivex.Single;

public interface PermissionActionProvider {
  Single<Boolean> isGranted(Permission permission);
  Single<Boolean> request(Permission permission);
}

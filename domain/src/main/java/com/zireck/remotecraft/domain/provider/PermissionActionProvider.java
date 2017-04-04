package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.Permission;
import io.reactivex.Single;

public interface PermissionActionProvider {
  Single<Boolean> isGranted(Permission permission);
  Single<Boolean> request(Permission permission);
}

package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.Permission;
import io.reactivex.Single;

public interface PermissionProvider {
  Single<Boolean> isGranted(Permission permission);
}

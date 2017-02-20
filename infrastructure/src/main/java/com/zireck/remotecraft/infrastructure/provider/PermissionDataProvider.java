package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.PermissionEntityDataMapper;
import io.reactivex.Single;
import javax.inject.Inject;

public class PermissionDataProvider implements PermissionProvider {

  @Inject Context context;
  private final PermissionEntityDataMapper permissionEntityDataMapper;

  @Inject public PermissionDataProvider(PermissionEntityDataMapper permissionEntityDataMapper) {
    this.permissionEntityDataMapper = permissionEntityDataMapper;
  }

  @Override public Single<Boolean> isGranted(Permission permission) {
    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    return Single.just(ContextCompat.checkSelfPermission(context, permissionEntity.getName()))
        .map(selfPermission -> selfPermission == PackageManager.PERMISSION_GRANTED);
  }
}

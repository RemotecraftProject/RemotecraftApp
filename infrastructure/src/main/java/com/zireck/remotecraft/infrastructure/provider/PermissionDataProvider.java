package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import com.zireck.remotecraft.infrastructure.exception.PermissionNotGrantedException;
import io.reactivex.Single;
import javax.inject.Inject;

public class PermissionDataProvider implements PermissionProvider {

  @Inject Context context;

  @Inject public PermissionDataProvider() {

  }

  @Override public Single<Boolean> isGranted(Permission permission) {
    PermissionEntity permissionEntity = new PermissionEntity(permission.getName());

    int selfPermission = ContextCompat.checkSelfPermission(context, permissionEntity.getName());
    boolean isGranted = (selfPermission == PackageManager.PERMISSION_GRANTED);

    return isGranted ? Single.just(true)
        : Single.error(new PermissionNotGrantedException("Permission not granted"));
  }
}

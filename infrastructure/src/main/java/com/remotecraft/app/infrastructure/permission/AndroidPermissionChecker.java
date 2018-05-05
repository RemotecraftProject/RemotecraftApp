package com.remotecraft.app.infrastructure.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.remotecraft.app.infrastructure.entity.PermissionEntity;
import javax.inject.Inject;

public class AndroidPermissionChecker implements PermissionChecker {

  @Inject public AndroidPermissionChecker() {

  }

  @Override public int checkSelfPermission(Context context, PermissionEntity permissionEntity) {
    return ContextCompat.checkSelfPermission(context, permissionEntity.getPermission());
  }

  @Override public boolean isGranted(int checkedSelfPermission) {
    return checkedSelfPermission == PackageManager.PERMISSION_GRANTED;
  }
}

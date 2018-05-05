package com.remotecraft.app.infrastructure.permission;

import android.content.Context;
import com.remotecraft.app.infrastructure.entity.PermissionEntity;

public interface PermissionChecker {
  int checkSelfPermission(Context context, PermissionEntity permissionEntity);
  boolean isGranted(int checkedSelfPermission);
}

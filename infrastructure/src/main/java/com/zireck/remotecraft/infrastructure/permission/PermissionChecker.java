package com.zireck.remotecraft.infrastructure.permission;

import android.content.Context;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;

public interface PermissionChecker {
  int checkSelfPermission(Context context, PermissionEntity permissionEntity);
  boolean isGranted(int checkedSelfPermission);
}

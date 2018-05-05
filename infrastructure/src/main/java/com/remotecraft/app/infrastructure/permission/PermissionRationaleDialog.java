package com.remotecraft.app.infrastructure.permission;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import com.remotecraft.app.infrastructure.entity.PermissionEntity;

public class PermissionRationaleDialog {

  private final AlertDialog.Builder alertDialog;

  public PermissionRationaleDialog(Context context, PermissionEntity permissionEntity,
      RationaleResponse rationaleResponse) {
    this.alertDialog = new AlertDialog.Builder(context)
        .setTitle(permissionEntity.getRationaleTitle())
        .setMessage(permissionEntity.getRationaleMessage())
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
          dialog.dismiss();
          rationaleResponse.continuePermissionRequest();
        })
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
          dialog.dismiss();
          rationaleResponse.cancelPermissionRequest();
        })
        .setOnDismissListener(dialog -> rationaleResponse.cancelPermissionRequest());
  }

  public void show() {
    alertDialog.show();
  }
}

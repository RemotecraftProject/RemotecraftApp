package com.remotecraft.app.dagger.modules;

import android.Manifest;
import android.content.res.Resources;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.model.PermissionModel;
import dagger.Module;
import dagger.Provides;

@Module
public class PermissionsModule {

  public PermissionsModule() {

  }

  @Provides @PerActivity PermissionModel provideCameraPermissionModel(Resources resources) {
    return PermissionModel.builder()
        .permission(Manifest.permission.CAMERA)
        .rationaleTitle(resources.getString(R.string.permission_camera_rationale_title))
        .rationaleMessage(resources.getString(R.string.permission_camera_rationale_message))
        .deniedMessage(resources.getString(R.string.permission_camera_denied_message))
        .build();
  }
}

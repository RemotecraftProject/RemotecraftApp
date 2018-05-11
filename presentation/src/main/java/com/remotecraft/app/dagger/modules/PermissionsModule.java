package com.remotecraft.app.dagger.modules;

import android.Manifest;
import android.content.res.Resources;
import com.remotecraft.app.R;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.dagger.qualifiers.PermissionAccessWifiState;
import com.remotecraft.app.dagger.qualifiers.PermissionCamera;
import com.remotecraft.app.model.PermissionModel;
import dagger.Module;
import dagger.Provides;

@Module
public class PermissionsModule {

  public PermissionsModule() {

  }

  @Provides @PerActivity
  @PermissionAccessWifiState
  PermissionModel provideAccessWifiStatePermissionModel(Resources resources) {
    return PermissionModel.builder()
        .permission(Manifest.permission.ACCESS_WIFI_STATE)
        .rationaleTitle(resources.getString(R.string.permission_access_wifi_state_rationale_title))
        .rationaleMessage(
            resources.getString(R.string.permission_access_wifi_state_rationale_message))
        .deniedMessage(resources.getString(R.string.permission_access_wifi_state_denied_message))
        .build();
  }

  @Provides @PerActivity
  @PermissionCamera
  PermissionModel provideCameraPermissionModel(Resources resources) {
    return PermissionModel.builder()
        .permission(Manifest.permission.CAMERA)
        .rationaleTitle(resources.getString(R.string.permission_camera_rationale_title))
        .rationaleMessage(resources.getString(R.string.permission_camera_rationale_message))
        .deniedMessage(resources.getString(R.string.permission_camera_denied_message))
        .build();
  }
}

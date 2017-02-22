package com.zireck.remotecraft.infrastructure.provider;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.PermissionEntityDataMapper;
import io.reactivex.Observable;
import io.reactivex.Single;
import javax.inject.Inject;

public class PermissionDataProvider implements PermissionProvider {

  @Inject Context context;
  @Inject Activity activity;
  private final PermissionEntityDataMapper permissionEntityDataMapper;

  @Inject public PermissionDataProvider(PermissionEntityDataMapper permissionEntityDataMapper) {
    this.permissionEntityDataMapper = permissionEntityDataMapper;
  }

  @Override public Single<Boolean> isGranted(Permission permission) {
    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    return Single.just(ContextCompat.checkSelfPermission(context, permissionEntity.getName()))
        .map(selfPermission -> selfPermission == PackageManager.PERMISSION_GRANTED);
  }

  @Override public Single<Boolean> request(Permission permission) {
    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    return Observable.<Boolean>create(emitter -> {
      Dexter.withActivity(activity)
          .withPermission(permissionEntity.getName())
          .withListener(new PermissionListener() {
            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
              emitter.onNext(true);
            }

            @Override public void onPermissionDenied(PermissionDeniedResponse response) {
              emitter.onNext(false);
            }

            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                PermissionToken token) {
              emitter.onNext(false);
            }
          });
    })
        .firstElement()
        .toSingle();
  }
}

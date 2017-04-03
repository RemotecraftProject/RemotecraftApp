package com.zireck.remotecraft.infrastructure.provider;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.PermissionEntityDataMapper;
import com.zireck.remotecraft.infrastructure.permission.AndroidPermissionChecker;
import com.zireck.remotecraft.infrastructure.permission.ContinuableSnackbarOnDeniedPermissionListener;
import com.zireck.remotecraft.infrastructure.permission.PermissionChecker;
import com.zireck.remotecraft.infrastructure.permission.PermissionRationaleDialog;
import com.zireck.remotecraft.infrastructure.permission.RationaleResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import javax.inject.Inject;

public class PermissionDataProvider implements PermissionProvider {

  @Inject Context context;
  @Inject Activity activity;
  private final PermissionChecker permissionChecker;
  private final PermissionEntityDataMapper permissionEntityDataMapper;

  @Inject public PermissionDataProvider(AndroidPermissionChecker androidPermissionChecker,
      PermissionEntityDataMapper permissionEntityDataMapper) {
    this.permissionChecker = androidPermissionChecker;
    this.permissionEntityDataMapper = permissionEntityDataMapper;
  }

  @Override public Single<Boolean> isGranted(Permission permission) {
    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    return Single.just(permissionChecker.checkSelfPermission(context, permissionEntity))
        .map(permissionChecker::isGranted);
  }

  @Override public Single<Boolean> request(Permission permission) {
    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    return Observable.<Boolean>create(emitter -> {
      PermissionListener permissionDeniedListener =
          getPermissionDeniedListener(activity, permissionEntity);
      PermissionListener permissionRequestListener =
          getPermissionRequestListener(permissionEntity, emitter);
      CompositePermissionListener compositePermissionListener =
          new CompositePermissionListener(permissionDeniedListener, permissionRequestListener);

      Dexter.withActivity(activity)
          .withPermission(permissionEntity.getPermission())
          .withListener(compositePermissionListener)
          .onSameThread()
          .check();
    })
        .firstElement()
        .toSingle();
  }

  private PermissionListener getPermissionDeniedListener(Activity activity,
      PermissionEntity permissionEntity) {
    ViewGroup view = (ViewGroup) activity.findViewById(android.R.id.content);


    return ContinuableSnackbarOnDeniedPermissionListener.Builder
        .with(view, permissionEntity.getDeniedMessage())
        .withDuration(Snackbar.LENGTH_INDEFINITE)
        .withOpenSettingsButton("Settings")
        .build();
  }

  private PermissionListener getPermissionRequestListener(PermissionEntity permissionEntity,
      ObservableEmitter<Boolean> emitter) {
    return new PermissionListener() {
      @Override public void onPermissionGranted(PermissionGrantedResponse response) {
        emitter.onNext(true);
      }

      @Override public void onPermissionDenied(PermissionDeniedResponse response) {
        emitter.onNext(false);
      }

      @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
          PermissionToken token) {
        new PermissionRationaleDialog(activity, permissionEntity, new RationaleResponse() {
          @Override public void continuePermissionRequest() {
            token.continuePermissionRequest();
          }

          @Override public void cancelPermissionRequest() {
            token.cancelPermissionRequest();
          }
        }).show();
      }
    };
  }
}

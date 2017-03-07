package com.zireck.remotecraft.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.model.PermissionModel;
import java.util.Collection;
import javax.inject.Inject;

@PerActivity public class PermissionModelDataMapper {

  @Inject public PermissionModelDataMapper() {

  }

  public PermissionModel transform(Permission permission) {
    if (permission == null) {
      throw new IllegalArgumentException("Cannot transform a null Permission object.");
    }

    return PermissionModel.builder()
        .permission(permission.getPermission())
        .rationaleTitle(permission.getRationaleTitle())
        .rationaleMessage(permission.getRationaleMessage())
        .deniedMessage(permission.getDeniedMessage())
        .build();
  }

  public Collection<PermissionModel> transform(Collection<Permission> permissionCollection) {
    return Stream.of(permissionCollection)
        .map(this::transform)
        .collect(Collectors.toList());
  }

  public Permission transformInverse(PermissionModel permissionModel) {
    if (permissionModel == null) {
      throw new IllegalArgumentException("Cannot transform a null PermissionModel object.");
    }

    return new Permission.Builder()
        .permission(permissionModel.permission())
        .rationaleTitle(permissionModel.rationaleTitle())
        .rationaleMessage(permissionModel.rationaleMessage())
        .deniedMessage(permissionModel.deniedMessage())
        .build();
  }

  public Collection<Permission> transformInverse(Collection<PermissionModel> permissionModels) {
    return Stream.of(permissionModels)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}

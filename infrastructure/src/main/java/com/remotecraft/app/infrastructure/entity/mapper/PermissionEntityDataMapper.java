package com.remotecraft.app.infrastructure.entity.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.infrastructure.entity.PermissionEntity;
import java.util.Collection;
import javax.inject.Inject;

public class PermissionEntityDataMapper {

  @Inject
  public PermissionEntityDataMapper() {

  }

  public Permission transform(PermissionEntity permissionEntity) {
    if (permissionEntity == null) {
      return null;
    }

    return Permission.builder()
        .permission(permissionEntity.getPermission())
        .rationaleTitle(permissionEntity.getRationaleTitle())
        .rationaleMessage(permissionEntity.getRationaleMessage())
        .deniedMessage(permissionEntity.getDeniedMessage())
        .build();
  }

  public Collection<Permission> transform(Collection<PermissionEntity> permissionEntities) {
    return Stream.of(permissionEntities)
        .map(this::transform)
        .collect(Collectors.toList());
  }

  public PermissionEntity transformInverse(Permission permission) {
    if (permission == null) {
      return null;
    }

    return new PermissionEntity.Builder()
        .permission(permission.permission())
        .rationaleTitle(permission.rationaleTitle())
        .rationaleMessage(permission.rationaleMessage())
        .deniedMessage(permission.deniedMessage())
        .build();
  }

  public Collection<PermissionEntity> transformInverse(Collection<Permission> permissions) {
    return Stream.of(permissions)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}

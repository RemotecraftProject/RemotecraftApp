package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import java.util.Collection;
import javax.inject.Inject;

public class PermissionEntityDataMapper {

  @Inject public PermissionEntityDataMapper() {

  }

  public Permission transform(PermissionEntity permissionEntity) {
    if (permissionEntity == null) {
      return null;
    }

    return new Permission(permissionEntity.getName());
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

    return new PermissionEntity(permission.getName());
  }

  public Collection<PermissionEntity> transformInverse(Collection<Permission> permissions) {
    return Stream.of(permissions)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}

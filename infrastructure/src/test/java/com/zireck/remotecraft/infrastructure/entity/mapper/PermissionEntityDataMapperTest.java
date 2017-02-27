package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class PermissionEntityDataMapperTest {

  private PermissionEntityDataMapper permissionEntityDataMapper;

  @Before public void setUp() throws Exception {
    permissionEntityDataMapper = new PermissionEntityDataMapper();
  }

  @Test public void shouldReturnNullValueGivenANullPermissionEntity() throws Exception {
    PermissionEntity permissionEntity = null;

    Permission permission = permissionEntityDataMapper.transform(permissionEntity);

    assertThat(permission, nullValue());
  }

  @Test public void shouldProperlyMapPermissionEntityIntoPermission() throws Exception {
    PermissionEntity permissionCameraEntity = new PermissionEntity.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission in order for this feature to work")
        .build();

    Permission permissionCamera = permissionEntityDataMapper.transform(permissionCameraEntity);

    assertThat(permissionCamera, notNullValue());
    assertThat(permissionCamera, instanceOf(Permission.class));
    assertThat(permissionCamera.getPermission(), notNullValue());
    assertThat(permissionCamera.getPermission(), is("CAMERA"));
    assertThat(permissionCamera.getRationaleTitle(), notNullValue());
    assertThat(permissionCamera.getRationaleTitle(), is("Permission Request"));
    assertThat(permissionCamera.getRationaleMessage(), notNullValue());
    assertThat(permissionCamera.getRationaleMessage(), is("You should allow this permission"));
    assertThat(permissionCamera.getDeniedMessage(), notNullValue());
    assertThat(permissionCamera.getDeniedMessage(),
        is("You must allow this permission in order for this feature to work"));
  }

  @Test public void shouldReturnNullValueGivenANullPermission() throws Exception {
    Permission permission = null;

    PermissionEntity permissionEntity = permissionEntityDataMapper.transformInverse(permission);

    assertThat(permissionEntity, nullValue());
  }

  @Test public void shouldProperlyMapPermissionIntoPermissionEntity() throws Exception {
    Permission permissionCamera = new Permission.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission in order for this feature to work")
        .build();

    PermissionEntity permissionCameraEntity =
        permissionEntityDataMapper.transformInverse(permissionCamera);

    assertThat(permissionCameraEntity, notNullValue());
    assertThat(permissionCameraEntity, instanceOf(PermissionEntity.class));
    assertThat(permissionCameraEntity.getPermission(), notNullValue());
    assertThat(permissionCameraEntity.getPermission(), is("CAMERA"));
    assertThat(permissionCameraEntity.getRationaleTitle(), notNullValue());
    assertThat(permissionCameraEntity.getRationaleTitle(), is("Permission Request"));
    assertThat(permissionCameraEntity.getRationaleMessage(), notNullValue());
    assertThat(permissionCameraEntity.getRationaleMessage(), is("You should allow this permission"));
    assertThat(permissionCameraEntity.getDeniedMessage(), notNullValue());
    assertThat(permissionCameraEntity.getDeniedMessage(),
        is("You must allow this permission in order for this feature to work"));
  }
}
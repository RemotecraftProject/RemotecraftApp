package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.infrastructure.entity.PermissionEntity;
import java.util.ArrayList;
import java.util.Collection;
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

  @Test public void shouldProperlyMapPermissionEntityCollectionIntoPermissionCollection()
      throws Exception {
    PermissionEntity cameraPermissionEntity = new PermissionEntity.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission in order for this feature to work")
        .build();
    PermissionEntity contactsPermissionEntity = new PermissionEntity.Builder()
        .permission("CONTACTS")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission")
        .build();
    ArrayList<PermissionEntity> permissionEntities = new ArrayList<>();
    permissionEntities.add(cameraPermissionEntity);
    permissionEntities.add(contactsPermissionEntity);

    Collection<Permission> permissions = permissionEntityDataMapper.transform(permissionEntities);

    assertThat(permissions, notNullValue());
    assertThat(permissions.size(), is(2));
    Permission permission1 = (Permission) permissions.toArray()[0];
    assertThat(permission1, notNullValue());
    assertThat(permission1.getPermission(), is("CAMERA"));
    Permission permission2 = (Permission) permissions.toArray()[1];
    assertThat(permission2, notNullValue());
    assertThat(permission2.getPermission(), is("CONTACTS"));
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

  @Test public void shouldProperlyMapPermissionCollectionIntoPermissionEntityCollection()
      throws Exception {
    Permission cameraPermission = new Permission.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission in order for this feature to work")
        .build();
    Permission contactsPermission = new Permission.Builder()
        .permission("CONTACTS")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission")
        .build();
    ArrayList<Permission> permissions = new ArrayList<>();
    permissions.add(cameraPermission);
    permissions.add(contactsPermission);

    Collection<PermissionEntity> permissionEntities =
        permissionEntityDataMapper.transformInverse(permissions);

    assertThat(permissionEntities, notNullValue());
    assertThat(permissionEntities.size(), is(2));
    PermissionEntity permissionEntity1 = (PermissionEntity) permissionEntities.toArray()[0];
    assertThat(permissionEntity1, notNullValue());
    assertThat(permissionEntity1.getPermission(), is("CAMERA"));
    PermissionEntity permissionEntity2 = (PermissionEntity) permissionEntities.toArray()[1];
    assertThat(permissionEntity2.getPermission(), is("CONTACTS"));
  }
}
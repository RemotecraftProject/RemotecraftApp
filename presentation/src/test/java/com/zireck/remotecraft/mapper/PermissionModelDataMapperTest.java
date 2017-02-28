package com.zireck.remotecraft.mapper;

import com.zireck.remotecraft.domain.Permission;
import com.zireck.remotecraft.model.PermissionModel;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class PermissionModelDataMapperTest {

  private PermissionModelDataMapper permissionModelDataMapper;

  @Before public void setUp() throws Exception {
    permissionModelDataMapper = new PermissionModelDataMapper();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionGivenNullPermission() throws Exception {
    Permission permission = null;

    permissionModelDataMapper.transform(permission);
  }

  @Test public void shouldProperlyMapPermissionIntoPermissionModel() throws Exception {
    Permission permission = new Permission.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();

    PermissionModel permissionModel = permissionModelDataMapper.transform(permission);

    assertThat(permissionModel, notNullValue());
    assertThat(permissionModel, instanceOf(PermissionModel.class));
    assertThat(permissionModel.getPermission(), notNullValue());
    assertThat(permissionModel.getPermission(), is("CAMERA"));
    assertThat(permissionModel.getRationaleTitle(), notNullValue());
    assertThat(permissionModel.getRationaleTitle(), is("Permission Request"));
    assertThat(permissionModel.getRationaleMessage(), notNullValue());
    assertThat(permissionModel.getRationaleMessage(), is("You should allow it"));
    assertThat(permissionModel.getDeniedMessage(), notNullValue());
    assertThat(permissionModel.getDeniedMessage(), is("Allow it!"));
  }

  @Test public void shouldProperlyMapPermissionCollectionIntoPermissionModelCollection()
      throws Exception {
    Permission permission1 = new Permission.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();
    Permission permission2 = new Permission.Builder()
        .permission("CONTACTS")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it, please")
        .deniedMessage("ALLOW IT!!")
        .build();
    ArrayList<Permission> permissions = new ArrayList<>();
    permissions.add(permission1);
    permissions.add(permission2);

    Collection<PermissionModel> permissionModels = permissionModelDataMapper.transform(permissions);

    assertThat(permissionModels, notNullValue());
    assertThat(permissionModels.size(), is(2));
    PermissionModel permissionModel1 = (PermissionModel) permissionModels.toArray()[0];
    assertThat(permissionModel1, notNullValue());
    assertThat(permissionModel1, instanceOf(PermissionModel.class));
    assertThat(permissionModel1.getPermission(), notNullValue());
    assertThat(permissionModel1.getPermission(), is("CAMERA"));
    PermissionModel permissionModel2 = (PermissionModel) permissionModels.toArray()[1];
    assertThat(permissionModel2, notNullValue());
    assertThat(permissionModel2, instanceOf(PermissionModel.class));
    assertThat(permissionModel2.getPermission(), notNullValue());
    assertThat(permissionModel2.getPermission(), is("CONTACTS"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionGivenNullPermissionModel() throws Exception {
    PermissionModel permissionModel = null;

    permissionModelDataMapper.transformInverse(permissionModel);
  }

  @Test public void shouldProperlyMapPermissionModelIntoPermission() throws Exception {
    PermissionModel permissionModel = new PermissionModel.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();

    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);

    assertThat(permission, notNullValue());
    assertThat(permission, instanceOf(Permission.class));
    assertThat(permission.getPermission(), notNullValue());
    assertThat(permission.getPermission(), is("CAMERA"));
    assertThat(permission.getRationaleTitle(), notNullValue());
    assertThat(permission.getRationaleTitle(), is("Permission Request"));
    assertThat(permission.getRationaleMessage(), notNullValue());
    assertThat(permission.getRationaleMessage(), is("You should allow it"));
    assertThat(permission.getDeniedMessage(), notNullValue());
    assertThat(permission.getDeniedMessage(), is("Allow it!"));
  }

  @Test public void shouldProperlyMapPermissionModelCollectionIntoPermissionCollection()
      throws Exception {
    PermissionModel permissionModel1 = new PermissionModel.Builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();
    PermissionModel permissionModel2 = new PermissionModel.Builder()
        .permission("CONTACTS")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it, please")
        .deniedMessage("ALLOW IT!!")
        .build();
    ArrayList<PermissionModel> permissionModels = new ArrayList<>();
    permissionModels.add(permissionModel1);
    permissionModels.add(permissionModel2);

    Collection<Permission> permissions =
        permissionModelDataMapper.transformInverse(permissionModels);

    assertThat(permissions, notNullValue());
    assertThat(permissions.size(), is(2));
    Permission permission1 = (Permission) permissions.toArray()[0];
    assertThat(permission1, notNullValue());
    assertThat(permission1, instanceOf(Permission.class));
    assertThat(permission1.getPermission(), notNullValue());
    assertThat(permission1.getPermission(), is("CAMERA"));
    Permission permission2 = (Permission) permissions.toArray()[1];
    assertThat(permission2, notNullValue());
    assertThat(permission2, instanceOf(Permission.class));
    assertThat(permission2.getPermission(), notNullValue());
    assertThat(permission2.getPermission(), is("CONTACTS"));
  }
}
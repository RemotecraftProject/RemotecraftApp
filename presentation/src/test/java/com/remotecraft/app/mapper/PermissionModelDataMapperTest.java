package com.remotecraft.app.mapper;

import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.model.PermissionModel;
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
    Permission permission = Permission.builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();

    PermissionModel permissionModel = permissionModelDataMapper.transform(permission);

    assertThat(permissionModel, notNullValue());
    assertThat(permissionModel, instanceOf(PermissionModel.class));
    assertThat(permissionModel.permission(), notNullValue());
    assertThat(permissionModel.permission(), is("CAMERA"));
    assertThat(permissionModel.rationaleTitle(), notNullValue());
    assertThat(permissionModel.rationaleTitle(), is("Permission Request"));
    assertThat(permissionModel.rationaleMessage(), notNullValue());
    assertThat(permissionModel.rationaleMessage(), is("You should allow it"));
    assertThat(permissionModel.deniedMessage(), notNullValue());
    assertThat(permissionModel.deniedMessage(), is("Allow it!"));
  }

  @Test public void shouldProperlyMapPermissionCollectionIntoPermissionModelCollection()
      throws Exception {
    Permission permission1 = Permission.builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();
    Permission permission2 = Permission.builder()
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
    assertThat(permissionModel1.permission(), notNullValue());
    assertThat(permissionModel1.permission(), is("CAMERA"));
    PermissionModel permissionModel2 = (PermissionModel) permissionModels.toArray()[1];
    assertThat(permissionModel2, notNullValue());
    assertThat(permissionModel2, instanceOf(PermissionModel.class));
    assertThat(permissionModel2.permission(), notNullValue());
    assertThat(permissionModel2.permission(), is("CONTACTS"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionGivenNullPermissionModel() throws Exception {
    PermissionModel permissionModel = null;

    permissionModelDataMapper.transformInverse(permissionModel);
  }

  @Test public void shouldProperlyMapPermissionModelIntoPermission() throws Exception {
    PermissionModel permissionModel = PermissionModel.builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();

    Permission permission = permissionModelDataMapper.transformInverse(permissionModel);

    assertThat(permission, notNullValue());
    assertThat(permission, instanceOf(Permission.class));
    assertThat(permission.permission(), notNullValue());
    assertThat(permission.permission(), is("CAMERA"));
    assertThat(permission.rationaleTitle(), notNullValue());
    assertThat(permission.rationaleTitle(), is("Permission Request"));
    assertThat(permission.rationaleMessage(), notNullValue());
    assertThat(permission.rationaleMessage(), is("You should allow it"));
    assertThat(permission.deniedMessage(), notNullValue());
    assertThat(permission.deniedMessage(), is("Allow it!"));
  }

  @Test public void shouldProperlyMapPermissionModelCollectionIntoPermissionCollection()
      throws Exception {
    PermissionModel permissionModel1 = PermissionModel.builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow it")
        .deniedMessage("Allow it!")
        .build();
    PermissionModel permissionModel2 = PermissionModel.builder()
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
    assertThat(permission1.permission(), notNullValue());
    assertThat(permission1.permission(), is("CAMERA"));
    Permission permission2 = (Permission) permissions.toArray()[1];
    assertThat(permission2, notNullValue());
    assertThat(permission2, instanceOf(Permission.class));
    assertThat(permission2.permission(), notNullValue());
    assertThat(permission2.permission(), is("CONTACTS"));
  }
}
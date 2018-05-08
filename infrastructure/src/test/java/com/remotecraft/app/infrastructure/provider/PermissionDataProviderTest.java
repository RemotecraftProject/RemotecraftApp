package com.remotecraft.app.infrastructure.provider;

import android.content.Context;
import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.domain.provider.PermissionActionProvider;
import com.remotecraft.app.infrastructure.entity.PermissionEntity;
import com.remotecraft.app.infrastructure.entity.mapper.PermissionEntityDataMapper;
import com.remotecraft.app.infrastructure.permission.AndroidPermissionChecker;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PermissionDataProviderTest {

  private PermissionActionProvider permissionActionProvider;

  @Mock private AndroidPermissionChecker mockAndroidPermissionChecker;
  @Mock private PermissionEntityDataMapper mockPermissionEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    permissionActionProvider =
        new PermissionDataProvider(mockAndroidPermissionChecker, mockPermissionEntityDataMapper);
  }

  @Test
  public void shouldNotifyPermissionIsGrantedWhenItsActuallyGranted() throws Exception {
    Permission permission = getPermission();
    PermissionEntity permissionEntity = getPermissionEntity();
    when(mockPermissionEntityDataMapper.transformInverse(permission)).thenReturn(permissionEntity);
    when(mockAndroidPermissionChecker.checkSelfPermission(any(Context.class),
        same(permissionEntity))).thenReturn(0);
    when(mockAndroidPermissionChecker.isGranted(0)).thenReturn(true);

    Single<Boolean> isGranted = permissionActionProvider.isGranted(permission);

    assertThat(isGranted).isNotNull();
    TestObserver<Boolean> testObserver = isGranted.test();
    testObserver.assertNoErrors();
    List<Boolean> streamValues = testObserver.values();
    assertThat(streamValues).isNotNull();
    assertThat(streamValues.size()).isEqualTo(1);
    assertThat(streamValues.get(0)).isTrue();
  }

  @Test
  public void shouldNotifyPermissionNotGrantedWhenItsActuallyNotGranted() throws Exception {
    Permission permission = getPermission();
    PermissionEntity permissionEntity = getPermissionEntity();
    when(mockPermissionEntityDataMapper.transformInverse(permission)).thenReturn(permissionEntity);
    when(mockAndroidPermissionChecker.checkSelfPermission(any(Context.class),
        same(permissionEntity))).thenReturn(-1);
    when(mockAndroidPermissionChecker.isGranted(-1)).thenReturn(false);

    Single<Boolean> isGranted = permissionActionProvider.isGranted(permission);

    assertThat(isGranted).isNotNull();
    TestObserver<Boolean> testObserver = isGranted.test();
    testObserver.assertNoErrors();
    List<Boolean> streamValues = testObserver.values();
    assertThat(streamValues).isNotNull();
    assertThat(streamValues.size()).isEqualTo(1);
    assertThat(streamValues.get(0)).isFalse();
  }

  private Permission getPermission() {
    return Permission.builder()
        .permission("CAMERA")
        .rationaleTitle("Camera")
        .rationaleMessage("Allow this permission")
        .deniedMessage("You must allow this permission")
        .build();
  }

  private PermissionEntity getPermissionEntity() {
    return new PermissionEntity.Builder()
        .permission("CAMERA")
        .build();
  }
}
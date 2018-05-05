package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.Permission;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.provider.PermissionActionProvider;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class CheckIfPermissionGrantedInteractorTest {

  private CheckIfPermissionGrantedInteractor checkIfPermissionGrantedInteractor;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private PermissionActionProvider mockPermissionActionProvider;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    checkIfPermissionGrantedInteractor =
        new CheckIfPermissionGrantedInteractor(mockPermissionActionProvider, mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test public void shouldReturnErrorWhenNullPermissionGiven() throws Exception {
    Single<Boolean> reactiveStream = checkIfPermissionGrantedInteractor.buildReactiveStream(null);

    assertThat(reactiveStream).isNotNull();
    TestObserver<Boolean> testObserver = reactiveStream.test();
    testObserver.assertError(IllegalArgumentException.class);
    testObserver.assertErrorMessage("Invalid Permission");
    testObserver.assertNotComplete();
  }

  @Test public void shouldConfirmGrantedPermissionGivenGrantedPermission() throws Exception {
    Permission permission = getPermission();
    CheckIfPermissionGrantedInteractor.Params params =
        CheckIfPermissionGrantedInteractor.Params.forPermission(permission);
    when(mockPermissionActionProvider.isGranted(permission)).thenReturn(Single.just(true));

    Single<Boolean> reactiveStream = checkIfPermissionGrantedInteractor.buildReactiveStream(params);

    assertThat(reactiveStream).isNotNull();
    TestObserver<Boolean> testObserver = reactiveStream.test();
    testObserver.assertNoErrors();
    testObserver.assertValue(true);
    testObserver.assertComplete();
  }

  @Test public void shouldDenyWhenGivenNotGrantedPermission() throws Exception {
    Permission permission = getPermission();
    CheckIfPermissionGrantedInteractor.Params params =
        CheckIfPermissionGrantedInteractor.Params.forPermission(permission);
    when(mockPermissionActionProvider.isGranted(permission)).thenReturn(Single.just(false));

    Single<Boolean> reactiveStream = checkIfPermissionGrantedInteractor.buildReactiveStream(params);

    assertThat(reactiveStream).isNotNull();
    TestObserver<Boolean> testObserver = reactiveStream.test();
    testObserver.assertNoErrors();
    testObserver.assertValue(false);
    testObserver.assertComplete();
  }

  private Permission getPermission() {
    return Permission.builder()
        .permission("CAMERA")
        .rationaleTitle("Permission Request")
        .rationaleMessage("You should allow this permission")
        .deniedMessage("You must allow this permission, otherwise it won't work")
        .build();
  }
}
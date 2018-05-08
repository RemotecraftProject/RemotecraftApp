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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
  public class RequestPermissionInteractorTest {

  private RequestPermissionInteractor requestPermissionInteractor;

  @Mock private PermissionActionProvider mockPermissionActionProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    requestPermissionInteractor =
        new RequestPermissionInteractor(mockPermissionActionProvider, mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test
  public void shouldReturnErrorGivenNullPermission() throws Exception {
    Single<Boolean> reactiveStream = requestPermissionInteractor.buildReactiveStream(null);

    assertThat(reactiveStream).isNotNull();
    TestObserver<Boolean> testObserver = reactiveStream.test();
    testObserver.assertError(IllegalArgumentException.class);
    testObserver.assertErrorMessage("Invalid Permission");
    testObserver.assertNotComplete();
  }

  @Test
  public void shouldConfirmGrantedPermissionWhenUserConfirms() throws Exception {
    Permission permission = getPermission();
    RequestPermissionInteractor.Params params =
        RequestPermissionInteractor.Params.forPermission(permission);
    when(mockPermissionActionProvider.request(permission)).thenReturn(Single.just(true));

    Single<Boolean> reactiveStream = requestPermissionInteractor.buildReactiveStream(params);

    assertThat(reactiveStream).isNotNull();
    TestObserver<Boolean> testObserver = reactiveStream.test();
    testObserver.assertNoErrors();
    testObserver.assertValue(true);
    testObserver.assertComplete();
  }

  @Test
  public void shouldNotConfirmPermissionWhenUserDoesNotConfirm() throws Exception {
    Permission permission = getPermission();
    RequestPermissionInteractor.Params params =
        RequestPermissionInteractor.Params.forPermission(permission);
    when(mockPermissionActionProvider.request(permission)).thenReturn(Single.just(false));

    Single<Boolean> reactiveStream = requestPermissionInteractor.buildReactiveStream(params);

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
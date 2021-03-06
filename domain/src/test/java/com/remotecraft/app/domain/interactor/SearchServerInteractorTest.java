package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.service.NotifyServerFoundService;
import com.remotecraft.app.domain.service.SearchServerService;
import com.remotecraft.app.domain.validation.NetworkAddressValidator;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SearchServerInteractorTest {

  private SearchServerInteractor searchServerInteractor;

  @Mock private SearchServerService searchServerService;
  @Mock private NotifyServerFoundService notifyServerFoundService;
  @Mock private NetworkAddressValidator mockNetworkAddressValidator;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchServerInteractor =
        new SearchServerInteractor(searchServerService, notifyServerFoundService,
            mockNetworkAddressValidator, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void shouldBuildReactiveStreamForEmptyParams() throws Exception {
    SearchServerInteractor.Params emptyParams = SearchServerInteractor.Params.empty();

    searchServerInteractor.buildReactiveStream(emptyParams);

    verify(searchServerService).searchServer();
    verifyNoMoreInteractions(searchServerService);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void shouldBuildReactiveStreamForValidNetworkAddress() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.44")
        .port(9998)
        .build();
    SearchServerInteractor.Params params =
        SearchServerInteractor.Params.forNetworkAddress(networkAddress);
    when(mockNetworkAddressValidator.isValid(networkAddress)).thenReturn(true);

    searchServerInteractor.buildReactiveStream(params);

    verify(searchServerService, times(1)).searchServer(networkAddress);
    verifyNoMoreInteractions(searchServerService);
  }

  @Test
  public void shouldNotBuildReactiveStreamGivenNullParams() throws Exception {
    Observable<Server> serverObservable = searchServerInteractor.buildReactiveStream(null);

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertErrorMessage("Params must be provided");
  }

  @Test
  public void shouldNotBuildReactiveStreamForInvalidNetworkAddress() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("")
        .port(-1)
        .build();
    SearchServerInteractor.Params params =
        SearchServerInteractor.Params.forNetworkAddress(networkAddress);
    when(mockNetworkAddressValidator.isValid(networkAddress)).thenReturn(false);

    Observable<Server> serverObservable = searchServerInteractor.buildReactiveStream(params);

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertErrorMessage("Invalid IP Address");
    verifyZeroInteractions(searchServerService);
  }

  @Test
  public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    SearchServerInteractor.Params emptyParams = SearchServerInteractor.Params.empty();
    when(searchServerService.searchServer()).thenReturn(getValidWorldReactiveStream());

    Observable reactiveStream = searchServerInteractor.buildReactiveStream(emptyParams);

    assertThat(reactiveStream).isNotNull();
  }

  @Test
  public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    SearchServerInteractor.Params emptyParams = SearchServerInteractor.Params.empty();
    when(searchServerService.searchServer()).thenReturn(Observable.empty());

    Observable reactiveStream = searchServerInteractor.buildReactiveStream(emptyParams);

    assertThat(reactiveStream).isNotNull();
  }

  @Test
  public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    SearchServerInteractor.Params emptyParams = SearchServerInteractor.Params.empty();
    when(searchServerService.searchServer()).thenReturn(Observable.empty());

    Observable reactiveStream = searchServerInteractor.buildReactiveStream(emptyParams);

    assertThat(getEmptyReactiveStream()).isEqualTo(reactiveStream);
  }

  private Observable<Server> getValidWorldReactiveStream() {
    return Observable.just(getFakeWorld());
  }

  private Server getFakeWorld() {
    return Server.builder()
        .ssid("WLAN_C33")
        .ip("192.168.1.44")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("v1.8")
        .seed("436465435")
        .worldName("Reign of Giants")
        .playerName("Etho")
        .encodedWorldImage("base64image")
        .build();
  }

  private Observable<Server> getEmptyReactiveStream() {
    return Observable.empty();
  }
}
package com.remotecraft.app.exception;

import android.content.Context;
import com.remotecraft.app.R;
import com.remotecraft.app.infrastructure.exception.InvalidServerException;
import com.remotecraft.app.infrastructure.exception.NoInternetConnectionException;
import com.remotecraft.app.infrastructure.exception.NoResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorMessageFactoryTest {

  @Mock private Context context;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnDefaultErrorMessageGivenDifferentExceptionWithoutMessage()
      throws Exception {
    when(context.getString(R.string.exception_generic)).thenReturn("Unknown error.");
    RuntimeException runtimeException = new RuntimeException("");

    String errorMessage = ErrorMessageFactory.create(context, runtimeException);

    verify(context, times(1)).getString(anyInt());
    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is("Unknown error."));
  }

  @Test
  public void shouldReturnSameErrorMessageAsDifferentExceptionGiven() throws Exception {
    RuntimeException runtimeException = new RuntimeException("This cannot possibly happen.");

    String errorMessage = ErrorMessageFactory.create(context, runtimeException);

    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is("This cannot possibly happen."));
  }

  @Test
  public void shouldReturnDefaultErrorMessageForNoInternetConnectionException()
      throws Exception {
    when(context.getString(R.string.exception_no_internet_connection)).thenReturn(
        "No Internet Connection Default Error Message");
    NoInternetConnectionException noInternetConnectionException =
        new NoInternetConnectionException();

    String errorMessage = ErrorMessageFactory.create(context, noInternetConnectionException);

    verify(context, times(1)).getString(R.string.exception_no_internet_connection);
    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is("No Internet Connection Default Error Message"));
  }

  @Test
  public void shouldReturnDefaultErrorMessageForNoResponseException() throws Exception {
    when(context.getString(R.string.exception_no_response)).thenReturn(
        "No Response Default Error Message");
    NoResponseException noResponseException = new NoResponseException();

    String errorMessage = ErrorMessageFactory.create(context, noResponseException);

    verify(context, times(1)).getString(R.string.exception_no_response);
    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is("No Response Default Error Message"));
  }

  @Test
  public void shouldReturnDefaultErrorMessageForInvalidServerException() throws Exception {
    when(context.getString(R.string.exception_invalid_server)).thenReturn(
        "Invalid Server Default Error Message");
    InvalidServerException invalidServerException = new InvalidServerException();

    String errorMessage = ErrorMessageFactory.create(context, invalidServerException);

    verify(context, times(1)).getString(R.string.exception_invalid_server);
    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is("Invalid Server Default Error Message"));
  }
}
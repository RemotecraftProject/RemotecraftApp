package com.remotecraft.app.infrastructure.exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InfrastructureErrorBundleTest {

  private InfrastructureErrorBundle infrastructureErrorBundle;

  @Mock private Exception mockException;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    infrastructureErrorBundle = new InfrastructureErrorBundle(mockException);
  }

  @Test
  public void shouldReturnTheRightException() throws Exception {
    Exception exception = infrastructureErrorBundle.getException();

    assertThat(exception, notNullValue());
    assertThat(exception, is(instanceOf(Exception.class)));
    assertThat(exception, is(mockException));
  }

  @Test
  public void shouldReturnTheRightMessageForTheGivenException() throws Exception {
    when(mockException.getMessage()).thenReturn("Error message!!");

    String errorMessage = infrastructureErrorBundle.getErrorMessage();

    verify(mockException, times(2)).getMessage();
    verifyNoMoreInteractions(mockException);
    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is(instanceOf(String.class)));
    assertThat(errorMessage, is("Error message!!"));
  }

  @Test
  public void shouldReturnNullExceptionGivenANullException() throws Exception {
    InfrastructureErrorBundle infrastructureErrorBundleWithNullException =
        new InfrastructureErrorBundle(null);
    Exception exception = infrastructureErrorBundleWithNullException.getException();

    assertThat(exception, nullValue());
  }

  @Test
  public void shouldReturnEmptyErrorMessageGivenANullException() throws Exception {
    InfrastructureErrorBundle infrastructureErrorBundleWithNullException =
        new InfrastructureErrorBundle(null);
    String errorMessage = infrastructureErrorBundleWithNullException.getErrorMessage();

    assertThat(errorMessage, is(""));
  }

  @Test
  public void shouldReturnEmptyErrorMessageGivenAnExceptionWithoutMessage() throws Exception {
    InfrastructureErrorBundle infrastructureErrorBundle =
        new InfrastructureErrorBundle(new NoInternetConnectionException());
    String errorMessage = infrastructureErrorBundle.getErrorMessage();

    assertThat(errorMessage, is(""));
  }
}
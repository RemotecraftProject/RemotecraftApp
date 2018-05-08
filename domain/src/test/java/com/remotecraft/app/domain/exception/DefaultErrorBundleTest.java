package com.remotecraft.app.domain.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultErrorBundleTest {

  private DefaultErrorBundle defaultErrorBundle;

  @Test
  public void shouldReturnNullExceptionGivenANullException() throws Exception {
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(null);

    Exception exception = defaultErrorBundle.getException();

    assertThat(exception).isNull();
  }

  @Test
  public void shouldReturnTheRightException() throws Exception {
    RuntimeException runtimeException = new RuntimeException();
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(runtimeException);

    Exception exception = defaultErrorBundle.getException();

    assertThat(exception).isNotNull();
    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  public void shouldReturnTheRightErrorMessageForAGivenException() throws Exception {
    NullPointerException nullPointerException = new NullPointerException("This cannot be null!");
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(nullPointerException);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage).isNotNull();
    assertThat(errorMessage).isInstanceOf(String.class);
    assertThat(errorMessage).isEqualTo("This cannot be null!");
  }

  @Test
  public void shouldReturnNullErrorMessageForAnExceptionWithoutMessage() throws Exception {
    NullPointerException nullPointerException = new NullPointerException();
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(nullPointerException);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage).isNullOrEmpty();
  }

  @Test
  public void shouldReturnDefaultErrorMessageGivenANullException() throws Exception {
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(null);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage).isNotNull();
    assertThat(errorMessage).isInstanceOf(String.class);
    assertThat(errorMessage).isEqualTo("Unknown error");
  }
}
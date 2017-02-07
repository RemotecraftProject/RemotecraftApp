package com.zireck.remotecraft.domain.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultErrorBundleTest {

  private DefaultErrorBundle defaultErrorBundle;

  @Test public void shouldReturnNullExceptionGivenANullException() throws Exception {
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(null);

    Exception exception = defaultErrorBundle.getException();

    assertThat(exception, nullValue());
  }

  @Test public void shouldReturnTheRightException() throws Exception {
    RuntimeException runtimeException = new RuntimeException();
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(runtimeException);

    Exception exception = defaultErrorBundle.getException();

    assertThat(exception, notNullValue());
    assertThat(exception, is(instanceOf(RuntimeException.class)));
  }

  @Test public void shouldReturnTheRightErrorMessageForAGivenException() throws Exception {
    NullPointerException nullPointerException = new NullPointerException("This cannot be null!");
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(nullPointerException);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is(instanceOf(String.class)));
    assertThat(errorMessage, is("This cannot be null!"));
  }

  @Test public void shouldReturnNullErrorMessageForAnExceptionWithoutMessage() throws Exception {
    NullPointerException nullPointerException = new NullPointerException();
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(nullPointerException);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage, nullValue());
  }

  @Test public void shouldReturnDefaultErrorMessageGivenANullException() throws Exception {
    DefaultErrorBundle defaultErrorBundle = new DefaultErrorBundle(null);

    String errorMessage = defaultErrorBundle.getErrorMessage();

    assertThat(errorMessage, notNullValue());
    assertThat(errorMessage, is(instanceOf(String.class)));
    assertThat(errorMessage, is("Unknown error"));
  }
}
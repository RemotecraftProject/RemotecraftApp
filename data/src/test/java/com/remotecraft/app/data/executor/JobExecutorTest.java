package com.remotecraft.app.data.executor;

import org.junit.Before;
import org.junit.Test;

public class JobExecutorTest {

  private JobExecutor jobExecutor;

  @Before public void setUp() throws Exception {
    jobExecutor = new JobExecutor();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGivenNullRunnable() throws Exception {
    jobExecutor.execute(null);
  }
}
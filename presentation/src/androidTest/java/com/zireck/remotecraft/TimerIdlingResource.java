package com.zireck.remotecraft;

import android.support.test.espresso.IdlingResource;

public class TimerIdlingResource implements IdlingResource {

  private final long startTime;
  private final long waitingTime;
  private ResourceCallback resourceCallback;

  public TimerIdlingResource(final long waitingTime) {
    this.startTime = System.currentTimeMillis();
    this.waitingTime = waitingTime;
  }

  @Override public String getName() {
    return TimerIdlingResource.class.getName() + ":" + waitingTime;
  }

  @Override public boolean isIdleNow() {
    long elapsed = System.currentTimeMillis() - startTime;
    boolean idle = (elapsed >= waitingTime);
    if (idle) {
      resourceCallback.onTransitionToIdle();
    }

    return idle;
  }

  @Override public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.resourceCallback = callback;
  }
}

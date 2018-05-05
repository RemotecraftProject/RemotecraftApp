package com.remotecraft.app;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UiThread implements PostExecutionThread {

  @Inject
  public UiThread() {

  }

  @Override public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}

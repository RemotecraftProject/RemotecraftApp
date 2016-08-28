package com.zireck.remotecraft;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Singleton
public class UiThread implements PostExecutionThread {

  @Inject
  public UiThread() {

  }

  @Override public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}

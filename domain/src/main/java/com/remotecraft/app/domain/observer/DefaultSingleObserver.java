package com.remotecraft.app.domain.observer;

import io.reactivex.observers.DisposableSingleObserver;

public class DefaultSingleObserver<T> extends DisposableSingleObserver<T> {

  @Override
  public void onSuccess(T value) {

  }

  @Override
  public void onError(Throwable e) {

  }
}

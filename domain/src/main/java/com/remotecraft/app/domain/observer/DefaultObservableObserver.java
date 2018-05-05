package com.remotecraft.app.domain.observer;

import io.reactivex.observers.DisposableObserver;

public class DefaultObservableObserver<T> extends DisposableObserver<T> {

  @Override public void onNext(T value) {

  }

  @Override public void onError(Throwable e) {

  }

  @Override public void onComplete() {

  }
}

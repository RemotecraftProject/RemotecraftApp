package com.remotecraft.app.domain.observer;

import io.reactivex.observers.DisposableMaybeObserver;

public class DefaultMaybeObserver<T> extends DisposableMaybeObserver<T> {

  @Override public void onSuccess(T value) {

  }

  @Override public void onError(Throwable e) {

  }

  @Override public void onComplete() {

  }
}

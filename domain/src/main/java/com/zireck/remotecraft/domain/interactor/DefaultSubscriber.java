package com.zireck.remotecraft.domain.interactor;

import rx.Subscriber;

public class DefaultSubscriber<T> extends Subscriber<T> {
  @Override public void onCompleted() {
    // no-op
  }

  @Override public void onError(Throwable e) {
    // no-op
  }

  @Override public void onNext(T t) {
    // no-op
  }
}

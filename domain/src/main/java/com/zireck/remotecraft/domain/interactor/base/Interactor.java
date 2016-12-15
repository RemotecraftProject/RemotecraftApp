package com.zireck.remotecraft.domain.interactor.base;

import io.reactivex.disposables.Disposable;

public interface Interactor<T extends Disposable> {
  void execute(T observer);
  void dispose();
}

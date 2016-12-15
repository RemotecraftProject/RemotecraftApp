package com.zireck.remotecraft.domain.interactor;

import io.reactivex.disposables.Disposable;

public interface BaseInteractor<T extends Disposable> {
  void execute(T observer);
  void dispose();
}

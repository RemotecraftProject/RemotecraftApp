package com.remotecraft.app.domain.interactor.base;

import com.remotecraft.app.domain.interactor.params.BaseParams;
import io.reactivex.disposables.Disposable;

public interface Interactor<T extends Disposable, P extends BaseParams> {
  void execute(T observer, P params);
  void dispose();
}

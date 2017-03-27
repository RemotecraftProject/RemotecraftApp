package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.view.BaseView;

public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {

  private V view;

  @Override public void attachView(@NonNull V view) {
    this.view = view;
  }

  @Override public void detachView() {
    this.view = null;
  }

  @Override public @NonNull V getView() {
    return view;
  }

  protected void checkViewAttached() {
    if (!isViewAttached()) {
      throw new ViewNotAttachedException();
    }
  }

  protected boolean isViewAttached() {
    return this.view != null;
  }

  private static class ViewNotAttachedException extends RuntimeException {
    public ViewNotAttachedException() {
      super("View must be attached before requesting data to the Presenter");
    }
  }
}

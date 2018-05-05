package com.remotecraft.app.presenter;

import android.support.annotation.NonNull;
import com.remotecraft.app.view.BaseView;

public interface Presenter<V extends BaseView> {
  void attachView(@NonNull V view);
  void detachView();
  V getView();
}

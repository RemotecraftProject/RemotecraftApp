package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.view.BaseView;

public interface Presenter<V extends BaseView> {
  void attachView(@NonNull V view);
  void resume();
  void pause();
  void destroy();
}

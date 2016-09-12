package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.zireck.remotecraft.domain.interactor.DefaultSubscriber;
import com.zireck.remotecraft.domain.interactor.Interactor;
import com.zireck.remotecraft.view.SearchView;
import javax.inject.Inject;
import javax.inject.Named;

public class SearchPresenter implements Presenter<SearchView> {

  private static final String TAG = SearchPresenter.class.getSimpleName();

  private SearchView view;
  private Interactor getWifiStateInteractor;

  @Inject
  public SearchPresenter(@Named("wifiState") Interactor getWifiStateInteractor) {
    this.getWifiStateInteractor = getWifiStateInteractor;
  }

  @Override public void setView(@NonNull SearchView view) {
    this.view = view;
  }

  @Override public void resume() {
    getWifiStateInteractor.execute(new GetWifiStateSubscriber());
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  private final class GetWifiStateSubscriber extends DefaultSubscriber<Integer> {
    @Override public void onNext(Integer integer) {
      super.onNext(integer);
      Log.d(TAG, "onNext: " + integer);
    }

    @Override public void onCompleted() {
      super.onCompleted();
      Log.d(TAG, "onCompleted");
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      Log.d(TAG, "onError");
    }
  }
}

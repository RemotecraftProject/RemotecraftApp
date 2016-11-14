package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.interactor.DefaultSubscriber;
import com.zireck.remotecraft.domain.interactor.Interactor;
import com.zireck.remotecraft.view.SearchView;
import timber.log.Timber;

public class SearchPresenter implements Presenter<SearchView> {

  private SearchView view;
  private Interactor getWifiStateInteractor;
  private Interactor searchWorldInteractor;

  public SearchPresenter(Interactor getWifiStateInteractor, Interactor searchWorldInteractor) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchWorldInteractor = searchWorldInteractor;
  }

  @Override public void setView(@NonNull SearchView view) {
    this.view = view;
  }

  @Override public void resume() {
    getWifiStateInteractor.execute(new GetWifiStateSubscriber());
    if (searchWorldInteractor != null) {
      searchWorldInteractor.execute(new SearchWorldSubscriber());
    }
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  private final class GetWifiStateSubscriber extends DefaultSubscriber<Integer> {
    @Override public void onNext(Integer integer) {
      super.onNext(integer);
    }

    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }
  }

  private final class SearchWorldSubscriber extends DefaultSubscriber<World> {

    @Override public void onNext(World world) {
      Timber.d("Received World: %s", world.getName());
      view.renderWorld(world);
    }

    @Override public void onError(Throwable e) {
      e.printStackTrace();
    }
  }
}

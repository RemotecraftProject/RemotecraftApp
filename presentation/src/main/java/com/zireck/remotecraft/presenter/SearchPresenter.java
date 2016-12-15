package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.interactor.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.view.SearchView;
import timber.log.Timber;

public class SearchPresenter implements Presenter<SearchView> {

  private SearchView view;
  private MaybeInteractor getWifiStateInteractor;
  private MaybeInteractor searchWorldInteractor;

  public SearchPresenter(MaybeInteractor getWifiStateInteractor, MaybeInteractor searchWorldInteractor) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchWorldInteractor = searchWorldInteractor;
  }

  @Override public void setView(@NonNull SearchView view) {
    this.view = view;
  }

  @Override public void resume() {
    if (searchWorldInteractor != null) {
      searchWorldInteractor.execute(new SearchWorldObserver());
    }
  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    searchWorldInteractor.dispose();
  }

  private final class SearchWorldObserver extends DefaultMaybeObserver<World> {
    @Override public void onSuccess(World world) {
      Timber.d("Received World: %s", world.getName());
      view.renderWorld(world);
    }

    @Override public void onError(Throwable e) {
      view.showError((Exception) e);
    }
  }
}

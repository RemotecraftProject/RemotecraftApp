package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.mapper.WorldModelDataMapper;
import com.zireck.remotecraft.model.WorldModel;
import com.zireck.remotecraft.view.SearchWorldView;
import timber.log.Timber;

public class SearchWorldPresenter implements Presenter<SearchWorldView> {

  private SearchWorldView view;
  private MaybeInteractor getWifiStateInteractor;
  private MaybeInteractor searchWorldInteractor;
  private WorldModelDataMapper worldModelDataMapper;

  public SearchWorldPresenter(MaybeInteractor getWifiStateInteractor,
      MaybeInteractor searchWorldInteractor, WorldModelDataMapper worldModelDataMapper) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchWorldInteractor = searchWorldInteractor;
    this.worldModelDataMapper = worldModelDataMapper;
  }

  @Override public void setView(@NonNull SearchWorldView view) {
    this.view = view;
  }

  @Override public void resume() {
    searchWorldInteractor.execute(new SearchWorldObserver());
  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    searchWorldInteractor.dispose();
  }

  private final class SearchWorldObserver extends DefaultMaybeObserver<World> {
    @Override public void onSuccess(World world) {
      Timber.d("Received World: %s", world.getName());

      WorldModel worldModel = worldModelDataMapper.transform(world);
      view.navigateToWorldDetail(worldModel);
    }

    @Override public void onError(Throwable e) {
      view.showError((Exception) e);
    }
  }
}

package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.observer.DefaultMaybeObserver;
import com.zireck.remotecraft.mapper.ServerModelDataMapper;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.view.SearchServerView;
import timber.log.Timber;

public class SearchServerPresenter implements Presenter<SearchServerView> {

  private SearchServerView view;
  private MaybeInteractor getWifiStateInteractor;
  private MaybeInteractor searchServerInteractor;
  private ServerModelDataMapper serverModelDataMapper;

  public SearchServerPresenter(MaybeInteractor getWifiStateInteractor,
      MaybeInteractor searchServerInteractor, ServerModelDataMapper serverModelDataMapper) {
    this.getWifiStateInteractor = getWifiStateInteractor;
    this.searchServerInteractor = searchServerInteractor;
    this.serverModelDataMapper = serverModelDataMapper;
  }

  @Override public void setView(@NonNull SearchServerView view) {
    this.view = view;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    searchServerInteractor.dispose();
  }

  public void onClickWifi() {
    view.closeMenu();
    view.showLoading();
    searchServerInteractor.execute(new SearchServerObserver());
  }

  public void onClickQrCode() {
    view.closeMenu();
    view.showError(new IllegalStateException("Currently unavailable"));
  }

  private final class SearchServerObserver extends DefaultMaybeObserver<Server> {
    @Override public void onSuccess(Server server) {
      Timber.d("Received Server: %s", server.getWorldName());

      view.hideLoading();
      ServerModel serverModel = serverModelDataMapper.transform(server);
      view.navigateToServerDetail(serverModel);
    }

    @Override public void onError(Throwable e) {
      view.hideLoading();
      view.showError((Exception) e);
    }
  }
}
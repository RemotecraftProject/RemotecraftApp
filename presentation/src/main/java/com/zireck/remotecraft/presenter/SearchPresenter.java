package com.zireck.remotecraft.presenter;

import android.support.annotation.NonNull;
import com.zireck.remotecraft.view.SearchView;
import javax.inject.Inject;

public class SearchPresenter implements Presenter<SearchView> {

  private SearchView view;

  @Inject
  public SearchPresenter() {

  }

  @Override public void setView(@NonNull SearchView view) {
    this.view = view;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }
}

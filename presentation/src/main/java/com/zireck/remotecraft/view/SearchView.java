package com.zireck.remotecraft.view;

import com.zireck.remotecraft.model.WorldModel;

public interface SearchView extends BaseView {
  void navigateToWorldDetail(WorldModel worldModel);

  void showError(Exception exception);
}

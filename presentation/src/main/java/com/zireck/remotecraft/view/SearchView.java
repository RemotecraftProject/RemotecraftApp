package com.zireck.remotecraft.view;

import com.zireck.remotecraft.domain.World;

public interface SearchView extends BaseView {
  void renderWorld(World world);

  void showError(Exception exception);
}

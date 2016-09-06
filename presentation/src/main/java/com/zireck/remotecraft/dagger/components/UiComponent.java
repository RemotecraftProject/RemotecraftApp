package com.zireck.remotecraft.dagger.components;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.ActivityModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.view.activity.SearchActivity;
import dagger.Component;

@PerActivity
@Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class,
        UiModule.class
    }
)
public interface UiComponent extends ActivityComponent {
  void inject(SearchActivity searchActivity);
}

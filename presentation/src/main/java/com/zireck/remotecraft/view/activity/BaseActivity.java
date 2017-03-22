package com.zireck.remotecraft.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.dagger.HasActivitySubcomponentBuilders;
import com.zireck.remotecraft.navigation.Navigator;
import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

  @Inject Navigator navigator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initInjector();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    bindViews();
  }

  protected abstract void injectMembers(
      HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders);

  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  private void initInjector() {
    injectMembers(RemotecraftApp.get(this));
  }

  private void bindViews() {
    ButterKnife.bind(this);
  }
}

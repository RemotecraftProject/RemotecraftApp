package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.model.WorldModel;

public class WorldFoundActivity extends BaseActivity {

  public static final String KEY_WORLD = "world";

  private WorldModel worldModel;

  public static Intent getCallingIntent(Context context, WorldModel worldModel) {
    Intent intent = new Intent(context, WorldFoundActivity.class);

    Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_WORLD, worldModel);
    intent.putExtras(bundle);

    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_world_found);
    mapExtras();
  }

  private void mapExtras() {
    if (getIntent() != null
        && getIntent().getExtras() != null
        && getIntent().getExtras().getParcelable(KEY_WORLD) != null) {
      worldModel = getIntent().getExtras().getParcelable(KEY_WORLD);
    }
  }
}

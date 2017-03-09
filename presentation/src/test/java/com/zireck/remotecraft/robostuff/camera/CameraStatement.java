package com.zireck.remotecraft.robostuff.camera;

import org.junit.runners.model.Statement;

public class CameraStatement extends Statement {

  private boolean mEnabled;
  private Statement mStatement;

  public CameraStatement(boolean enabled, Statement statement) {
    mEnabled = enabled;
    mStatement = statement;
  }

  @Override
  public void evaluate() throws Throwable {
    CameraHolder.CAMERA = mEnabled;
    mStatement.evaluate();
  }

}
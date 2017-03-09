package com.zireck.remotecraft.robostuff.camera;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class CameraRule implements MethodRule {

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    boolean camera = false;
    if (method.getAnnotation(EnableCamera.class) != null) {
      camera = true;
    }

    return new CameraStatement(camera, base);
  }

}

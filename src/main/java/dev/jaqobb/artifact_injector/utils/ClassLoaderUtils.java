package dev.jaqobb.artifact_injector.utils;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public final class ClassLoaderUtils {
  public static final Method ADD_URL_METHOD;

  static {
    try {
      ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      ADD_URL_METHOD.setAccessible(true);
    } catch (Exception exception) {
      throw new InternalError("Could not cache addURL method", exception);
    }
  }

  private ClassLoaderUtils() {
    throw new UnsupportedOperationException("Cannot create instance of this class");
  }
}

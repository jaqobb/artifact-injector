package dev.jaqobb.artifact_injector.dependency;

public class DependencyInjectException extends RuntimeException {
  private static final long serialVersionUID = -5867102724657644054L;

  public DependencyInjectException(String message) {
    super(message);
  }

  public DependencyInjectException(String message, Throwable cause) {
    super(message, cause);
  }
}

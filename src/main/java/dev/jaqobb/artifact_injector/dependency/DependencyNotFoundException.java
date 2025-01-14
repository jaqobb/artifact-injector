package dev.jaqobb.artifact_injector.dependency;

public class DependencyNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 7773321764575924026L;

  public DependencyNotFoundException(String message) {
    super(message);
  }

  public DependencyNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}

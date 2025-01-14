package dev.jaqobb.artifact_injector.dependency;

public class DependencyDownloadException extends RuntimeException {
  private static final long serialVersionUID = 8051335510492296025L;

  public DependencyDownloadException(String message) {
    super(message);
  }

  public DependencyDownloadException(String message, Throwable cause) {
    super(message, cause);
  }
}

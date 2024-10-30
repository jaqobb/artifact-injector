package dev.jaqobb.artifact_injector.dependency;

public class DependencyDownloadException extends RuntimeException {
    
    public DependencyDownloadException(String message) {
        super(message);
    }
    
    public DependencyDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}

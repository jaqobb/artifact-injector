package dev.jaqobb.artifact_injector.dependency;

public class DependencyInjectException extends RuntimeException {
    
    public DependencyInjectException(String message) {
        super(message);
    }
    
    public DependencyInjectException(String message, Throwable cause) {
        super(message, cause);
    }
}

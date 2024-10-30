package dev.jaqobb.artifact_injector;

import dev.jaqobb.artifact_injector.dependency.Dependency;
import dev.jaqobb.artifact_injector.dependency.DependencyDownloadException;
import dev.jaqobb.artifact_injector.dependency.DependencyInjectException;
import dev.jaqobb.artifact_injector.dependency.DependencyNotFoundException;
import dev.jaqobb.artifact_injector.repository.Repositories;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public class ArtifactInjector {
    
    private static final Method ADD_URL_METHOD;
    
    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        } catch (Exception exception) {
            throw new InternalError("Could not cache addURL method", exception);
        }
    }
    
    private final File dependenciesDirectory;
    
    private ArtifactInjector(File dependenciesDirectory) {
        if (dependenciesDirectory == null) {
            throw new NullPointerException("dependenciesDirectory");
        }
        this.dependenciesDirectory = dependenciesDirectory;
        if (!this.dependenciesDirectory.exists() && !this.dependenciesDirectory.mkdirs()) {
            throw new IllegalStateException("Could not create dependencies directory");
        }
    }
    
    public File getDependenciesDirectory() {
        return this.dependenciesDirectory;
    }
    
    public void inject(String groupId, String artifactId, String version, URLClassLoader classLoader) {
        this.inject(new Dependency(groupId, artifactId, version), Repositories.MAVEN_CENTRAL, classLoader);
    }
    
    public void inject(String groupId, String artifactId, String version, String repository, URLClassLoader classLoader) {
        this.inject(new Dependency(groupId, artifactId, version), repository, classLoader);
    }
    
    public void inject(Dependency dependency, URLClassLoader classLoader) {
        this.inject(dependency, Repositories.MAVEN_CENTRAL, classLoader);
    }
    
    public void inject(Dependency dependency, String repository, URLClassLoader classLoader) {
        if (dependency == null) {
            throw new NullPointerException("dependency");
        }
        if (repository == null) {
            throw new NullPointerException("repository");
        }
        if (classLoader == null) {
            throw new NullPointerException("classLoader");
        }
        File file = dependency.getFile(this.dependenciesDirectory);
        if (!file.exists()) {
            try {
                URL downloadURL = dependency.getDownloadURL(repository);
                try (InputStream inputStream = downloadURL.openStream()) {
                    Files.copy(inputStream, file.toPath());
                }
            } catch (IOException exception) {
                throw new DependencyDownloadException("Could not download dependency " + dependency.getName(), exception);
            }
        }
        if (!file.exists()) {
            throw new DependencyNotFoundException("Could not find dependency " + dependency.getName());
        }
        try {
            ADD_URL_METHOD.invoke(classLoader, file.toURI().toURL());
        } catch (MalformedURLException | IllegalAccessException | InvocationTargetException exception) {
            throw new DependencyInjectException("Could not inject dependency " + dependency.getName(), exception);
        }
    }
}

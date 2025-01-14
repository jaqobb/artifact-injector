package dev.jaqobb.artifact_injector;

import dev.jaqobb.artifact_injector.dependency.Dependency;
import dev.jaqobb.artifact_injector.repository.Repositories;

import java.io.File;
import java.net.URLClassLoader;

// TODO: Add downloading private dependencies.
// TODO: Add cleaning up older dependencies when injecting a newer version.
public class ArtifactInjector {
  private final File dependenciesDirectory;

  public ArtifactInjector(File dependenciesDirectory) {
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
    dependency.inject(repository, classLoader, this.dependenciesDirectory);
  }
}

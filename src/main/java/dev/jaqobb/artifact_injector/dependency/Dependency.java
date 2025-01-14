package dev.jaqobb.artifact_injector.dependency;

import dev.jaqobb.artifact_injector.utils.ClassLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Objects;

public class Dependency {
  private final String groupId;
  private final String artifactId;
  private final String version;

  public Dependency(String groupId, String artifactId, String version) {
    if (groupId == null) {
      throw new NullPointerException("groupId");
    }
    if (artifactId == null) {
      throw new NullPointerException("artifactId");
    }
    if (version == null) {
      throw new NullPointerException("version");
    }
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public String getArtifactId() {
    return this.artifactId;
  }

  public String getVersion() {
    return this.version;
  }

  public String getName() {
    return this.artifactId + "-" + this.version;
  }

  public File getFile(File parentDirectory) {
    return new File(parentDirectory, this.getFilePath() + File.separator + this.getFileName());
  }

  public String getFilePath() {
    return this.groupId.replace(".", File.separator) + File.separator + this.artifactId + File.separator + this.version;
  }

  public String getFileName() {
    return this.getName() + ".jar";
  }

  public URL getDownloadURL(String repository) throws MalformedURLException {
    String fixedRepository = repository.endsWith("/") ? repository : repository + "/";
    String artifactFilePath = this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version;
    String artifactFileName = this.artifactId + "-" + this.version + ".jar";
    return new URL(fixedRepository + artifactFilePath + "/" + artifactFileName);
  }

  public void inject(String repository, URLClassLoader classLoader, File dependenciesDirectory) {
    if (repository == null) {
      throw new NullPointerException("repository");
    }
    if (classLoader == null) {
      throw new NullPointerException("classLoader");
    }
    if (dependenciesDirectory == null) {
      throw new NullPointerException("dependenciesDirectory");
    }
    File file = this.getFile(dependenciesDirectory);
    if (!file.exists()) {
      try {
        URL downloadURL = this.getDownloadURL(repository);
        try (InputStream inputStream = downloadURL.openStream()) {
          Files.copy(inputStream, file.toPath());
        }
      } catch (IOException exception) {
        throw new DependencyDownloadException("Could not download dependency " + this.getName(), exception);
      }
    }
    if (!file.exists()) {
      throw new DependencyNotFoundException("Could not find dependency " + this.getName());
    }
    try {
      ClassLoaderUtils.ADD_URL_METHOD.invoke(classLoader, file.toURI().toURL());
    } catch (MalformedURLException | IllegalAccessException | InvocationTargetException exception) {
      throw new DependencyInjectException("Could not inject dependency " + this.getName(), exception);
    }
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || this.getClass() != object.getClass()) {
      return false;
    }
    Dependency other = (Dependency) object;
    return Objects.equals(this.groupId, other.groupId) && Objects.equals(this.artifactId, other.artifactId) && Objects.equals(this.version, other.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.groupId, this.artifactId, this.version);
  }

  @Override
  public String toString() {
    return "Dependency(groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ")";
  }
}

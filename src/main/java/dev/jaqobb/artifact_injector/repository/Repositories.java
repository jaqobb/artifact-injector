package dev.jaqobb.artifact_injector.repository;

public final class Repositories {
  public static final String MAVEN_CENTRAL = "https://repo.maven.apache.org/maven2";
  public static final String JAQOBB_RELEASES = "https://repository.jaqobb.dev/releases";
  public static final String JAQOBB_SNAPSHOTS = "https://repository.jaqobb.dev/snapshots";
  public static final String JAQOBB_PRIVATE = "https://repository.jaqobb.dev/private";
  public static final String JAQOBB_3RD_PARTY = "https://repository.jaqobb.dev/3rd-party";

  private Repositories() {
    throw new UnsupportedOperationException("Cannot create instance of this class");
  }
}

# Artifact Injector

Small, minimalistic, and easy-to-use Java library for injecting Maven-based artifacts without increasing the final JAR's size. This lightweight Java library can be used to download and cache Maven-based artifacts locally for them to be later injected at runtime to a `URLClassLoader` of your choice. This approach reduces the need for shading dependencies, which decreases the final JAR's size and is especially useful when file size limits are a concern or when multiple applications share the same dependencies.

## Adding to your project

### Maven

```xml

<repositories>
  <repository>
    <id>jaqobb-repository-releases</id>
    <name>jaqobb Repository</name>
    <url>https://repository.jaqobb.dev/releases</url>
  </repository>
</repositories>

<dependencies>
<dependency>
  <groupId>dev.jaqobb</groupId>
  <artifactId>artifact-injector</artifactId>
  <version>3.0.2</version>
</dependency>
</dependencies>
```

### Gradle (Kotlin)

```kotlin
repositories {
  maven {
    name = "jaqobbRepositoryReleases"
    url = uri("https://repository.jaqobb.dev/releases")
  }
}

dependencies {
  implementation("dev.jaqobb:artifact-injector:3.0.2")
}
```

### Gradle (Groovy)

```groovy
repositories {
  maven {
    name "jaqobbRepositoryReleases"
    url "https://repository.jaqobb.dev/releases"
  }
}

dependencies {
  implementation "dev.jaqobb:artifact-injector:3.0.2"
}
```

### Usage

Before you start, you first need to gather the required information about the dependencies you want to inject. This information includes the group id, artifact id, version, and optionally the repository URL of each dependency. Repository URL does not need to be provided if the artifact is located in the Maven Central Repository.

Once you have gathered the required information, you then construct a new `ArtifactInjector` instance using:

```java
ArtifactInjector artifactInjector = new ArtifactInjector(dependenciesFolder);
```

The `dependenciesFolder` parameter needs to be provided and is a folder where all dependencies will be cached. The directory will be created if it does not already exist.

After you have created an instance of `ArtifactInjector`, you can inject your dependencies using one of the following `inject` methods:

```java
artifactInjector.inject(groupId, artifactId, version, classLoader);
```

```java
artifactInjector.inject(groupId, artifactId, version, repository, classLoader);
```

```java
artifactInjector.inject(dependency, classLoader);
```

```java
artifactInjector.inject(dependency, repository, classLoader);
```

The `dependency` parameter is an instance of the `Dependency` class that represents the information about the dependency you want to inject. You can also use the `groupId`, `artifactId`, and `version` parameters instead if you so prefer. If you use the `groupId`, `artifactId`, and `version` parameters, the library will simply create a new `Dependency` instance for you.

The `repository` parameter is optional and represents the URL of the repository where the dependency is located. The library offers a set of predefined repositories that you can use without worrying about their URL. You can get them from the `Repositories` class. If you want to add a repository, please make an issue or a pull request.

The `classLoader` parameter is required and represents the `URLClassLoader` instance to which the dependency will be injected.

If the dependency is already cached, the library will inject it immediately. Otherwise, the library will download it from the repository, cache it, and then inject it.

If, at any point, the library encounters an error, it will throw one of these exceptions: `DependencyDownloadException`, `DependencyNotFoundException`, `DependencyInjectException`. You can catch and then access them to get more information about the error.

### Important notes

Starting from Java 9, the use of reflection to access private methods, such as `URLClassLoader#addURL` that this library uses, is restricted due to the module system and stronger encapsulation.

To allow the use of reflection, you need to add the following JVM arguments to open the required packages within the `java.base` module to reflection:

```shell
--add-opens java.base/java.net=ALL-UNNAMED
--add-opens java.base/java.lang=ALL-UNNAMED
```

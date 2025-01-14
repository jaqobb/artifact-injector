plugins {
  `java-library`
  `maven-publish`
}

group = "dev.jaqobb"
version = "3.0.1"
description = "Small, minimalistic, and easy-to-use Java library for injecting Maven-based artifacts without increasing the final JAR's size"

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.11.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.awaitility:awaitility:4.2.2")
}

tasks.register<Jar>("sourcesJar") {
  from(sourceSets["main"].allSource)
  archiveClassifier.set("sources")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

publishing {
  repositories {
    maven {
      name = if (!project.version.toString().endsWith("SNAPSHOT")) "jaqobbRepositoryReleases" else "jaqobbRepositorySnapshots"
      url = if (!project.version.toString().endsWith("SNAPSHOT")) uri("https://repository.jaqobb.dev/releases") else uri("https://repository.jaqobb.dev/snapshots")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      groupId = project.group.toString()
      artifactId = project.name
      version = project.version.toString()
      from(components["java"])
      artifact(tasks["sourcesJar"])
    }
  }
}

package dev.jaqobb.artifact_injector.dependency;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
        String filePath = this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version;
        String fileName = this.artifactId + "-" + this.version + ".jar";
        String baseURL = repository.endsWith("/") ? repository : repository + "/";
        return new URL(baseURL + filePath + "/" + fileName);
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Dependency that = (Dependency) object;
        return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version);
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

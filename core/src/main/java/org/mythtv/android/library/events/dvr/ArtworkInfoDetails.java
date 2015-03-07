package org.mythtv.android.library.events.dvr;

/**
 * Created by dmfrey on 11/12/14.
 */
public class ArtworkInfoDetails {

    private String url;
    private String fileName;
    private String storageGroup;
    private String type;

    public ArtworkInfoDetails() {
    }

    public ArtworkInfoDetails( String url, String fileName, String storageGroup, String type ) {
        this.url = url;
        this.fileName = fileName;
        this.storageGroup = storageGroup;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public void setStorageGroup(String storageGroup) {
        this.storageGroup = storageGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

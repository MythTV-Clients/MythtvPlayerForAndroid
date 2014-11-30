package org.mythtv.android.library.events.dvr;

/**
 * Created by dmfrey on 11/12/14.
 */
public class ArtworkInfoDetails {

    private String uRL;
    private String fileName;
    private String storageGroup;
    private String type;

    public ArtworkInfoDetails() {
    }

    public ArtworkInfoDetails(String uRL, String fileName, String storageGroup, String type) {
        this.uRL = uRL;
        this.fileName = fileName;
        this.storageGroup = storageGroup;
        this.type = type;
    }

    public String getuRL() {
        return uRL;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
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

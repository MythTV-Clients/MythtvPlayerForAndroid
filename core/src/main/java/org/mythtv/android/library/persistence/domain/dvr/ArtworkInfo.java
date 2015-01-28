package org.mythtv.android.library.persistence.domain.dvr;

import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;

import java.io.Serializable;

/**
 * Created by dmfrey on 11/12/14.
 */
public class ArtworkInfo implements Serializable {

    private String uRL;
    private String fileName;
    private String storageGroup;
    private String type;

    public ArtworkInfo() {
    }

    public ArtworkInfo(String uRL, String fileName, String storageGroup, String type) {
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

    @Override
    public String toString() {
        return "ArtworkInfo{" +
                "uRL='" + uRL + '\'' +
                ", fileName='" + fileName + '\'' +
                ", storageGroup='" + storageGroup + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public ArtworkInfoDetails toDetails() {

        ArtworkInfoDetails details = new ArtworkInfoDetails();
        details.setuRL( uRL );
        details.setFileName( fileName );
        details.setStorageGroup( storageGroup );
        details.setType( type );

        return details;
    }

    public static ArtworkInfo fromDetails( ArtworkInfoDetails details ) {

        ArtworkInfo artworkInfo = new ArtworkInfo();
        artworkInfo.setuRL( details.getuRL() );
        artworkInfo.setFileName( details.getFileName() );
        artworkInfo.setStorageGroup( details.getStorageGroup() );
        artworkInfo.setType( details.getType() );

        return artworkInfo;
    }

}

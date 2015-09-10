package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dmfrey on 8/27/15.
 */
public class ArtworkEntity {

    @SerializedName( "ArtworkInfos" )
    private ArtworkInfoEntity[] artworkInfos;

    public ArtworkEntity() {
    }

    public ArtworkInfoEntity[] getArtworkInfos() {

        return artworkInfos;
    }

    public void setArtworkInfos( ArtworkInfoEntity[] artworkInfos ) {

        this.artworkInfos = artworkInfos;

    }

    @Override
    public String toString() {
        return "ArtworkEntity{" +
                "artworkInfos=" + Arrays.toString(artworkInfos) +
                '}';
    }

}

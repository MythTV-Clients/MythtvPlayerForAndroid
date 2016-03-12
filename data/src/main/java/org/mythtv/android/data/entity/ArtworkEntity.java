package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 8/27/15.
 */
@Data
public class ArtworkEntity {

    @SerializedName( "ArtworkInfos" )
    private ArtworkInfoEntity[] artworkInfos;

}

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 9/7/15.
 */
@Data
public class VideoMetadataInfosEntity {

    @SerializedName( "VideoMetadataInfos" )
    private VideoMetadataInfoEntity[] videoMetadataInfoEntities;

}

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 9/18/15.
 */
@Data
public class VideoMetadataInfoWrapperEntity {

    @SerializedName( "VideoMetadataInfo" )
    private VideoMetadataInfoEntity videoMetadataInfo;

}

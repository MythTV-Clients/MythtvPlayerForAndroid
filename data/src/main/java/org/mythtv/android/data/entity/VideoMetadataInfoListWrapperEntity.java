package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 11/1/15.
 */
@Data
public class VideoMetadataInfoListWrapperEntity {

    @SerializedName( "VideoMetadataInfoList" )
    private VideoMetadataInfoListEntity videoMetadataInfoListEntity;

}

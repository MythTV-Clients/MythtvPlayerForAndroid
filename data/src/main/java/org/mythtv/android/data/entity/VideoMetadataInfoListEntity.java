package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Data;

/**
 * Created by dmfrey on 8/27/15.
 */
@Data
public class VideoMetadataInfoListEntity {

    @SerializedName( "StartIndex" )
    private int startIndex;

    @SerializedName( "Count" )
    private int count;

    @SerializedName( "CurrentPage" )
    private int currentPage;

    @SerializedName( "TotalPages" )
    private int totalPages;

    @SerializedName( "TotalAvailable" )
    private int totalAvailable;

    @SerializedName( "AsOf" )
    private DateTime asOf;

    @SerializedName( "Version" )
    private String version;

    @SerializedName( "ProtoVer" )
    private int protoVer;

    @SerializedName( "VideoMetadataInfos" )
    private VideoMetadataInfoEntity[] videoMetadataInfosEntity;

}

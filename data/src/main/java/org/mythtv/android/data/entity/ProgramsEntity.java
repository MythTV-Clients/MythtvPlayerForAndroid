package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Data;

/**
 * Created by dmfrey on 9/7/15.
 */
@Data
public class ProgramsEntity {

    @SerializedName( "StartIndex" )
    private int startIndex;

    @SerializedName( "Count" )
    private int count;

    @SerializedName( "TotalAvailable" )
    private int totalAvailable;

    @SerializedName( "AsOf" )
    private DateTime asOf;

    @SerializedName( "Version" )
    private String version;

    @SerializedName( "ProtoVer" )
    private int protoVer;

    @SerializedName( "Programs" )
    private ProgramEntity[] programs;

}

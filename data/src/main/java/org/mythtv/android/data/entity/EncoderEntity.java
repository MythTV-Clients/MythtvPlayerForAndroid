package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class EncoderEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "HostName" )
    private String hostname;

    @SerializedName( "Local" )
    private boolean local;

    @SerializedName( "Connected" )
    private boolean connected;

    @SerializedName( "State" )
    private int state;

    @SerializedName( "SleepStatus" )
    private int sleepStatus;

    @SerializedName( "LowOnFreeSpace" )
    private boolean lowOnFreeSpace;

    @SerializedName( "Inputs" )
    private InputEntity[] inputs;

    @SerializedName( "Recording" )
    private ProgramEntity recording;

}

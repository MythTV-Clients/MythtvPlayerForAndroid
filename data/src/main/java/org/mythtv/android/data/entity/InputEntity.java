package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class InputEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "CardId" )
    private int cardId;

    @SerializedName( "SourceId" )
    private int sourceId;

    @SerializedName( "InputName" )
    private String inputName;

    @SerializedName( "DisplayName" )
    private String displayName;

    @SerializedName( "QuickTune" )
    private boolean quickTune;

    @SerializedName( "RecPriority" )
    private int recordPriority;

    @SerializedName( "ScheduleOrder" )
    private int scheduleOrder;

    @SerializedName( "LiveTVOrder" )
    private int liveTvOrder;

}

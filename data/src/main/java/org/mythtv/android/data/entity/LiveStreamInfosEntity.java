package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 10/17/15.
 */
@Data
public class LiveStreamInfosEntity {

    @SerializedName( "LiveStreamInfos" )
    private LiveStreamInfoEntity[] liveStreamInfos;

}

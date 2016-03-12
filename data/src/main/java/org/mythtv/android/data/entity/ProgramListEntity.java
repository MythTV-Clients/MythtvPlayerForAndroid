package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 8/27/15.
 */
@Data
public class ProgramListEntity {

    @SerializedName( "ProgramList" )
    private ProgramsEntity programs;

}

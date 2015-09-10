package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by dmfrey on 8/27/15.
 */
public class ProgramListEntity {

    @SerializedName( "ProgramList" )
    private ProgramsEntity programs;

    public ProgramListEntity() {
    }

    public ProgramsEntity getPrograms() {
        return programs;
    }

    public void setPrograms(ProgramsEntity programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "ProgramListEntity{" +
                "programs=" + programs +
                '}';
    }

}

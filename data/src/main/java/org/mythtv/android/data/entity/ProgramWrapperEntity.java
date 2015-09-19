package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 9/18/15.
 */
public class ProgramWrapperEntity {

    @SerializedName( "Program" )
    private ProgramEntity program;

    public ProgramWrapperEntity() {
    }

    public ProgramEntity getProgram() {
        return program;
    }

    public void setProgram( ProgramEntity program ) {

        this.program = program;

    }

    @Override
    public String toString() {
        return "ProgramWrapperEntity{" +
                "program=" + program +
                '}';
    }

}

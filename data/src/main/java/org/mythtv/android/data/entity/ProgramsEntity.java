package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by dmfrey on 9/7/15.
 */
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

    public ProgramsEntity() { }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(int totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public DateTime getAsOf() {
        return asOf;
    }

    public void setAsOf(DateTime asOf) {
        this.asOf = asOf;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getProtoVer() {
        return protoVer;
    }

    public void setProtoVer(int protoVer) {
        this.protoVer = protoVer;
    }

    public ProgramEntity[] getPrograms() {
        return programs;
    }

    public void setPrograms(ProgramEntity[] programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "ProgramsEntity{" +
                "startIndex=" + startIndex +
                ", count=" + count +
                ", totalAvailable=" + totalAvailable +
                ", asOf=" + asOf +
                ", version='" + version + '\'' +
                ", protoVer=" + protoVer +
                ", programs=" + Arrays.toString(programs) +
                '}';
    }
}

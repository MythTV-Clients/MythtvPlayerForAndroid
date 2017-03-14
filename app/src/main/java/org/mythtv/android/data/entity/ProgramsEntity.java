/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/7/15.
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

    public ProgramsEntity() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public ProgramsEntity(int startIndex, int count, int totalAvailable, DateTime asOf, String version, int protoVer, ProgramEntity[] programs ) {

        this.startIndex = startIndex;
        this.count = count;
        this.totalAvailable = totalAvailable;
        this.asOf = asOf;
        this.version = version;
        this.protoVer = protoVer;
        this.programs = programs;

    }

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

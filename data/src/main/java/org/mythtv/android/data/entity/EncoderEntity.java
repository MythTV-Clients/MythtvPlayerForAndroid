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

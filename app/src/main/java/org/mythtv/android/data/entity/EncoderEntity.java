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

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
@AutoValue
public abstract class EncoderEntity {

    @SerializedName( "Id" )
    public abstract int id();

    @SerializedName( "HostName" )
    public abstract String hostname();

    @SerializedName( "Local" )
    public abstract boolean local();

    @SerializedName( "Connected" )
    public abstract boolean connected();

    @SerializedName( "State" )
    public abstract int state();

    @SerializedName( "SleepStatus" )
    public abstract int sleepStatus();

    @SerializedName( "LowOnFreeSpace" )
    public abstract boolean lowOnFreeSpace();

    @SerializedName( "Inputs" )
    public abstract List<InputEntity> inputs();

    @SerializedName( "Recording" )
    public abstract ProgramEntity recording();

    public static EncoderEntity create( int id, String hostname, boolean local, boolean connected, int state, int sleepStatus, boolean lowOnFreeSpace, List<InputEntity> inputs, ProgramEntity recording ) {

        return new AutoValue_EncoderEntity( id, hostname, local, connected, state, sleepStatus, lowOnFreeSpace, inputs, recording );
    }

    public static TypeAdapter<EncoderEntity> typeAdapter(Gson gson ) {

        return new AutoValue_EncoderEntity.GsonTypeAdapter( gson );
    }

}

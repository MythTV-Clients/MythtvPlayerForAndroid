/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

import org.joda.time.DateTime;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
@AutoValue
public abstract class RecordingInfoEntity {

    @Nullable
    @SerializedName( "RecordedId" )
    public abstract String recordedId();

    @SerializedName( "Status" )
    public abstract int status();

    @SerializedName( "Priority" )
    public abstract int priority();

    @Nullable
    @SerializedName( "StartTs" )
    public abstract DateTime startTs();

    @Nullable
    @SerializedName( "EndTs" )
    public abstract DateTime endTs();

    @SerializedName( "RecordId" )
    public abstract int recordId();

    @Nullable
    @SerializedName( "RecGroup" )
    public abstract String recGroup();

    @Nullable
    @SerializedName( "PlayGroup" )
    public abstract String playGroup();

    @Nullable
    @SerializedName( "StorageGroup" )
    public abstract String storageGroup();

    @SerializedName( "RecType" )
    public abstract int recType();

    @SerializedName( "DupInType" )
    public abstract int dupInType();

    @SerializedName( "DupMethod" )
    public abstract int dupMethod();

    @SerializedName( "EncoderId" )
    public abstract int encoderId();

    @Nullable
    @SerializedName( "EncoderName" )
    public abstract String encoderName();

    @Nullable
    @SerializedName( "Profile" )
    public abstract String profile();

    public static RecordingInfoEntity create( String recordedId, int status, int priority, DateTime startTs, DateTime endTs, int recordId, String recGroup, String playGroup, String storageGroup, int recType, int dupInType, int dupMethod, int encoderId, String encoderName, String profile ) {

        return new AutoValue_RecordingInfoEntity( recordedId, status, priority, startTs, endTs, recordId, recGroup, playGroup, storageGroup, recType, dupInType, dupMethod, encoderId, encoderName, profile );
    }

    public static TypeAdapter<RecordingInfoEntity> typeAdapter( Gson gson ) {

        return new AutoValue_RecordingInfoEntity.GsonTypeAdapter( gson );
    }

    public Integer translateRecordedId() {

        try {

            return Integer.parseInt( recordedId() );

        } catch( NumberFormatException e ) {

            return 0;
        }

    }

}

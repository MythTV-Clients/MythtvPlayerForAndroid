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

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Data;

/*
 * Created by dmfrey on 11/12/14.
 */
@Data
public class RecordingInfoEntity {

    @SerializedName( "RecordedId" )
    private int recordedId;

    @SerializedName( "Status" )
    private int status;

    @SerializedName( "Priority" )
    private int priority;

    @SerializedName( "StartTs" )
    private DateTime startTs;

    @SerializedName( "EndTs" )
    private DateTime endTs;

    @SerializedName( "RecordId" )
    private int recordId;

    @SerializedName( "RecGroup" )
    private String recGroup;

    @SerializedName( "PlayGroup" )
    private String playGroup;

    @SerializedName( "StorageGroup" )
    private String storageGroup;

    @SerializedName( "RecType" )
    private int recType;

    @SerializedName( "DupInType" )
    private int dupInType;

    @SerializedName( "DupMethod" )
    private int dupMethod;

    @SerializedName( "EncoderId" )
    private int encoderId;

    @SerializedName( "EncoderName" )
    private String encoderName;

    @SerializedName( "Profile" )
    private String profile;

}

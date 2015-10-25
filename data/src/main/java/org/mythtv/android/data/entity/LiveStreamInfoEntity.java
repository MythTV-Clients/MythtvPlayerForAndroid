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
 * Created by dmfrey on 10/17/15.
 */
@Data
public class LiveStreamInfoEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "Width" )
    private int width;

    @SerializedName( "Height" )
    private int height;

    @SerializedName( "Bitrate" )
    private int bitrate;

    @SerializedName( "AudioBitrate" )
    private int audioBitrate;

    @SerializedName( "SegmentSize" )
    private int segmentSize;

    @SerializedName( "MaxSegments" )
    private int maxSegments;

    @SerializedName( "StartSegment" )
    private int startSegment;

    @SerializedName( "CurrentSegment" )
    private int currentSegment;

    @SerializedName( "SegmentCount" )
    private int segmentCount;

    @SerializedName( "PercentComplete" )
    private int percentComplete;

    @SerializedName( "Created" )
    private DateTime created;

    @SerializedName( "LastModified" )
    private DateTime lastModified;

    @SerializedName( "RelativeURL" )
    private String relativeUrl;

    @SerializedName( "FullURL" )
    private String fullUrl;

    @SerializedName( "StatusStr" )
    private String statusString;

    @SerializedName( "StatusInt" )
    private int statusInt;

    @SerializedName( "StatusMessage" )
    private String statusMessage;

    @SerializedName( "SourceFile" )
    private String sourceFile;

    @SerializedName( "SourceHost" )
    private String sourceHost;

    @SerializedName( "SourceWidth" )
    private int sourceWidth;

    @SerializedName( "SourceHeight" )
    private int sourceHeight;

    @SerializedName( "AudioOnlyBitrate" )
    private int audioOnlyBitrate;

}

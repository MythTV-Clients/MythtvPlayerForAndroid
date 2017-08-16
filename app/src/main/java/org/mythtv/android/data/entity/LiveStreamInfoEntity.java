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
 * Created on 10/17/15.
 */
@AutoValue
public abstract class LiveStreamInfoEntity {

    @SerializedName( "Id" )
    public abstract int id();

    @SerializedName( "Width" )
    public abstract int width();

    @SerializedName( "Height" )
    public abstract int height();

    @SerializedName( "Bitrate" )
    public abstract int bitrate();

    @SerializedName( "AudioBitrate" )
    public abstract int audioBitrate();

    @SerializedName( "SegmentSize" )
    public abstract int segmentSize();

    @SerializedName( "MaxSegments" )
    public abstract int maxSegments();

    @SerializedName( "StartSegment" )
    public abstract int startSegment();

    @SerializedName( "CurrentSegment" )
    public abstract int currentSegment();

    @SerializedName( "SegmentCount" )
    public abstract int segmentCount();

    @SerializedName( "PercentComplete" )
    public abstract int percentComplete();

    @Nullable
    @SerializedName( "Created" )
    public abstract DateTime created();

    @Nullable
    @SerializedName( "LastModified" )
    public abstract DateTime lastModified();

    @Nullable
    @SerializedName( "RelativeURL" )
    public abstract String relativeUrl();

    @Nullable
    @SerializedName( "FullURL" )
    public abstract String fullUrl();

    @Nullable
    @SerializedName( "StatusStr" )
    public abstract String statusString();

    @SerializedName( "StatusInt" )
    public abstract int statusInt();

    @Nullable
    @SerializedName( "StatusMessage" )
    public abstract String statusMessage();

    @Nullable
    @SerializedName( "SourceFile" )
    public abstract String sourceFile();

    @Nullable
    @SerializedName( "SourceHost" )
    public abstract String sourceHost();

    @SerializedName( "SourceWidth" )
    public abstract int sourceWidth();

    @SerializedName( "SourceHeight" )
    public abstract int sourceHeight();

    @SerializedName( "AudioOnlyBitrate" )
    public abstract int audioOnlyBitrate();

    public static LiveStreamInfoEntity create( int id, int width, int height, int bitrate, int audioBitrate, int segmentSize, int maxSegments, int startSegment, int currentSegment, int segmentCount, int percentComplete, DateTime created, DateTime lastModified, String relativeUrl, String fullUrl, String statusString, int statusInt, String statusMessage, String sourceFile, String sourceHost, int sourceWidth, int sourceHeight, int audioOnlyBitrate ) {

        return new AutoValue_LiveStreamInfoEntity( id, width, height, bitrate, audioBitrate, segmentSize, maxSegments, startSegment, currentSegment, segmentCount, percentComplete, created, lastModified, relativeUrl, fullUrl, statusString, statusInt, statusMessage, sourceFile, sourceHost, sourceWidth, sourceHeight, audioOnlyBitrate );
    }

    public static TypeAdapter<LiveStreamInfoEntity> typeAdapter( Gson gson ) {

        return new AutoValue_LiveStreamInfoEntity.GsonTypeAdapter( gson );
    }

}

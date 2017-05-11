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
public abstract class ProgramEntity {

    private LiveStreamInfoEntity liveStreamInfoEntity;

    private long bookmark;

    @Nullable
    @SerializedName( "StartTime" )
    public abstract DateTime startTime();

    @Nullable
    @SerializedName( "EndTime" )
    public abstract DateTime endTime();

    @Nullable
    @SerializedName( "Title" )
    public abstract String title();

    @Nullable
    @SerializedName( "SubTitle" )
    public abstract String subTitle();

    @Nullable
    @SerializedName( "Category" )
    public abstract String category();

    @Nullable
    @SerializedName( "CatType" )
    public abstract String catType();

    @SerializedName( "Repeat" )
    public abstract boolean repeat();

    @SerializedName( "VideoProps" )
    public abstract int videoProps();

    @SerializedName( "AudioProps" )
    public abstract int audioProps();

    @SerializedName( "SubProps" )
    public abstract int subProps();

    @Nullable
    @SerializedName( "SeriesId" )
    public abstract String seriesId();

    @Nullable
    @SerializedName( "ProgramId" )
    public abstract String programId();

    @SerializedName( "Stars" )
    public abstract double stars();

    @SerializedName( "FileSize" )
    public abstract long fileSize();

    @Nullable
    @SerializedName( "LastModified" )
    public abstract DateTime lastModified();

    @SerializedName( "ProgramFlags" )
    public abstract int programFlags();

    @Nullable
    @SerializedName( "FileName" )
    public abstract String fileName();

    @Nullable
    @SerializedName( "HostName" )
    public abstract String hostName();

    @Nullable
    @SerializedName( "Airdate" )
    public abstract String airdate();

    @Nullable
    @SerializedName( "Description" )
    public abstract String description();

    @Nullable
    @SerializedName( "Inetref" )
    public abstract String inetref();

    @SerializedName( "Season" )
    public abstract int season();

    @SerializedName( "Episode" )
    public abstract int episode();

    @SerializedName( "TotalEpisodes" )
    public abstract int totalEpisodes();

    @Nullable
    @SerializedName( "Channel" )
    public abstract ChannelInfoEntity channel();

    @Nullable
    @SerializedName( "Recording" )
    public abstract RecordingInfoEntity recording();

    @Nullable
    @SerializedName( "Artwork" )
    public abstract ArtworkEntity artwork();

    @Nullable
    @SerializedName( "Cast" )
    public abstract CastEntity cast();

    public static ProgramEntity create( DateTime startTime, DateTime endTime, String title, String subTitle, String category, String catType, boolean repeat, int videoProps, int audioProps, int subProps, String seriesId, String programId, double stars, long fileSize, DateTime lastModified, int programFlags, String fileName, String hostName, String airdate, String description, String inetref, int season, int episode, int totalEpisodes, ChannelInfoEntity channel, RecordingInfoEntity recording, ArtworkEntity artwork, CastEntity cast ) {

        return new AutoValue_ProgramEntity( startTime, endTime, title, subTitle, category, catType, repeat, videoProps, audioProps, subProps, seriesId, programId, stars, fileSize, lastModified, programFlags, fileName, hostName, airdate, description, inetref, season, episode, totalEpisodes, channel, recording, artwork, cast );
    }

    public static TypeAdapter<ProgramEntity> typeAdapter( Gson gson ) {

        return new AutoValue_ProgramEntity.GsonTypeAdapter( gson );
    }

    public LiveStreamInfoEntity getLiveStreamInfoEntity() {
        return liveStreamInfoEntity;
    }

    public void setLiveStreamInfoEntity( LiveStreamInfoEntity liveStreamInfoEntity ) {

        this.liveStreamInfoEntity = liveStreamInfoEntity;

    }

    public long getBookmark() {

        return bookmark;
    }

    public void setBookmark( long bookmark ) {

        this.bookmark = bookmark;

    }

}

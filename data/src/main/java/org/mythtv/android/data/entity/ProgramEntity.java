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
import org.joda.time.LocalDate;

import lombok.Data;

/*
 * Created by dmfrey on 11/12/14.
 */
@Data
public class ProgramEntity {

    @SerializedName( "StartTime" )
    private DateTime startTime;

    @SerializedName( "EndTime" )
    private DateTime endTime;

    @SerializedName( "Title" )
    private String title;

    @SerializedName( "SubTitle" )
    private String subTitle;

    @SerializedName( "Category" )
    private String category;

    @SerializedName( "CatType" )
    private String catType;

    @SerializedName( "Repeat" )
    private boolean repeat;

    @SerializedName( "VideoProps" )
    private int videoProps;

    @SerializedName( "AudioProps" )
    private int audioProps;

    @SerializedName( "SubProps" )
    private int subProps;

    @SerializedName( "SeriesId" )
    private String seriesId;

    @SerializedName( "ProgramId" )
    private String programId;

    @SerializedName( "Stars" )
    private double stars;

    @SerializedName( "FileSize" )
    private long fileSize;

    @SerializedName( "LastModified" )
    private DateTime lastModified;

    @SerializedName( "ProgramFlags" )
    private int programFlags;

    @SerializedName( "FileName" )
    private String fileName;

    @SerializedName( "HostName" )
    private String hostName;

    @SerializedName( "AirDate" )
    private LocalDate airdate;

    @SerializedName( "Description" )
    private String description;

    @SerializedName( "Inetref" )
    private String inetref;

    @SerializedName( "Season" )
    private int season;

    @SerializedName( "Episode" )
    private int episode;

    @SerializedName( "TotalEpisodes" )
    private int totalEpisodes;

    @SerializedName( "Channel" )
    private ChannelInfoEntity channel;

    @SerializedName( "Recording" )
    private RecordingInfoEntity recording;

    @SerializedName( "Artwork" )
    private ArtworkEntity artwork;

    @SerializedName( "Cast" )
    private CastEntity cast;

}

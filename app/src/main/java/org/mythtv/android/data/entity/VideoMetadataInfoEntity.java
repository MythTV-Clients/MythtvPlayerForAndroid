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
 * Created by dmfrey on 10/30/15.
 */
@Data
public class VideoMetadataInfoEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "Title" )
    private String title;

    @SerializedName( "SubTitle" )
    private String subTitle;

    @SerializedName( "Tagline" )
    private String tagline;

    @SerializedName( "Director" )
    private String director;

    @SerializedName( "Studio" )
    private String studio;

    @SerializedName( "Description" )
    private String description;

    @SerializedName( "Certification" )
    private String certification;

    @SerializedName( "Inetref" )
    private String inetref;

    @SerializedName( "Collectionref" )
    private int collectionref;

    @SerializedName( "HomePage" )
    private String homePage;

    @SerializedName( "ReleaseDate" )
    private DateTime releaseDate;

    @SerializedName( "AddDate" )
    private DateTime addDate;

    @SerializedName( "UserRating" )
    private float userRating;

    @SerializedName( "Length" )
    private int length;

    @SerializedName( "PlayCount" )
    private int playCount;

    @SerializedName( "Season" )
    private int season;

    @SerializedName( "Episode" )
    private int episode;

    @SerializedName( "ParentalLevel" )
    private int parentalLevel;

    @SerializedName( "Visible" )
    private boolean visible;

    @SerializedName( "Watched" )
    private boolean watched;

    @SerializedName( "Processed" )
    private boolean processed;

    @SerializedName( "ContentType" )
    private String contentType;

    @SerializedName( "FileName" )
    private String fileName;

    @SerializedName( "Hash" )
    private String hash;

    @SerializedName( "HostName" )
    private String hostName;

    @SerializedName( "Coverart" )
    private String coverart;

    @SerializedName( "Fanart" )
    private String fanart;

    @SerializedName( "Banner" )
    private String banner;

    @SerializedName( "Screenshot" )
    private String screenshot;

    @SerializedName( "Trailer" )
    private String trailer;

    @SerializedName( "Artwork" )
    private ArtworkEntity artwork;

    @SerializedName( "Cast" )
    private CastEntity cast;

}

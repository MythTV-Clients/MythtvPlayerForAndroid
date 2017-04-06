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
 * Created on 10/30/15.
 */
@AutoValue
public abstract class VideoMetadataInfoEntity {

    @SerializedName( "Id" )
    public abstract int id();

    @Nullable
    @SerializedName( "Title" )
    public abstract String title();

    @Nullable
    @SerializedName( "SubTitle" )
    public abstract String subTitle();

    @Nullable
    @SerializedName( "Tagline" )
    public abstract String tagline();

    @Nullable
    @SerializedName( "Director" )
    public abstract String director();

    @Nullable
    @SerializedName( "Studio" )
    public abstract String studio();

    @Nullable
    @SerializedName( "Description" )
    public abstract String description();

    @Nullable
    @SerializedName( "Certification" )
    public abstract String certification();

    @Nullable
    @SerializedName( "Inetref" )
    public abstract String inetref();

    @SerializedName( "Collectionref" )
    public abstract int collectionref();

    @Nullable
    @SerializedName( "HomePage" )
    public abstract String homePage();

    @Nullable
    @SerializedName( "ReleaseDate" )
    public abstract DateTime releaseDate();

    @Nullable
    @SerializedName( "AddDate" )
    public abstract DateTime addDate();

    @SerializedName( "UserRating" )
    public abstract float userRating();

    @SerializedName( "Length" )
    public abstract int length();

    @SerializedName( "PlayCount" )
    public abstract int playCount();

    @SerializedName( "Season" )
    public abstract int season();

    @SerializedName( "Episode" )
    public abstract int episode();

    @SerializedName( "ParentalLevel" )
    public abstract int parentalLevel();

    @SerializedName( "Visible" )
    public abstract boolean visible();

    @SerializedName( "Watched" )
    public abstract boolean watched();

    @SerializedName( "Processed" )
    public abstract boolean processed();

    @Nullable
    @SerializedName( "ContentType" )
    public abstract String contentType();

    @Nullable
    @SerializedName( "FileName" )
    public abstract String fileName();

    @Nullable
    @SerializedName( "Hash" )
    public abstract String hash();

    @Nullable
    @SerializedName( "HostName" )
    public abstract String hostName();

    @Nullable
    @SerializedName( "Coverart" )
    public abstract String coverart();

    @Nullable
    @SerializedName( "Fanart" )
    public abstract String fanart();

    @Nullable
    @SerializedName( "Banner" )
    public abstract String banner();

    @Nullable
    @SerializedName( "Screenshot" )
    public abstract String screenshot();

    @Nullable
    @SerializedName( "Trailer" )
    public abstract String trailer();

    @Nullable
    @SerializedName( "Artwork" )
    public abstract ArtworkEntity artwork();

    @Nullable
    @SerializedName( "Cast" )
    public abstract CastEntity cast();

    @Nullable
    private LiveStreamInfoEntity liveStreamInfoEntity;

    public static VideoMetadataInfoEntity create( int id, String title, String subTitle, String tagline, String director, String studio, String description, String certification, String inetref, int collectionref, String homePage, DateTime releaseDate, DateTime addDate, float userRating, int length, int playCount, int season, int episode, int parentalLevel, boolean visible, boolean watched, boolean processed, String contentType, String fileName, String hash, String hostName, String coverart, String fanart, String banner, String screenshot, String trailer, ArtworkEntity artwork, CastEntity cast ) {

        return new AutoValue_VideoMetadataInfoEntity( id, title, subTitle, tagline, director, studio, description, certification, inetref, collectionref, homePage, releaseDate, addDate, userRating, length, playCount, season, episode, parentalLevel, visible, watched, processed, contentType, fileName, hash, hostName, coverart, fanart, banner, screenshot, trailer, artwork, cast );
    }

    public static TypeAdapter<VideoMetadataInfoEntity> typeAdapter(Gson gson ) {

        return new AutoValue_VideoMetadataInfoEntity.GsonTypeAdapter( gson );
    }

    @Nullable
    public LiveStreamInfoEntity getLiveStreamInfoEntity() {

        return liveStreamInfoEntity;
    }

    public void setLiveStreamInfoEntity( @Nullable LiveStreamInfoEntity liveStreamInfoEntity ) {

        this.liveStreamInfoEntity = liveStreamInfoEntity;

    }

}

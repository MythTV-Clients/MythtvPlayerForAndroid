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

import java.util.Arrays;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/7/15.
 */
public class VideoMetadataInfosEntity {

    @SerializedName( "VideoMetadataInfos" )
    private VideoMetadataInfoEntity[] videoMetadataInfoEntities;

    public VideoMetadataInfosEntity() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public VideoMetadataInfosEntity( VideoMetadataInfoEntity[] videoMetadataInfoEntities ) {

        this.videoMetadataInfoEntities = videoMetadataInfoEntities;

    }

    public VideoMetadataInfoEntity[] getVideoMetadataInfoEntities() {

        return videoMetadataInfoEntities;
    }

    public void setVideoMetadataInfoEntities( VideoMetadataInfoEntity[] videoMetadataInfoEntities ) {

        this.videoMetadataInfoEntities = videoMetadataInfoEntities;

    }

    @Override
    public String toString() {
        return "VideoMetadataInfosEntity{" +
                "videoMetadataInfoEntities=" + Arrays.toString(videoMetadataInfoEntities) +
                '}';
    }

}

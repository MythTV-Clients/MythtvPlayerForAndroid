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

//import lombok.Data;

/**
 * Created by dmfrey on 10/17/15.
 */
//@Data
public class LiveStreamInfosEntity {

    @SerializedName( "LiveStreamInfos" )
    private LiveStreamInfoEntity[] liveStreamInfos;

    public LiveStreamInfosEntity() {
    }

    public LiveStreamInfosEntity(LiveStreamInfoEntity[] liveStreamInfos ) {

        this.liveStreamInfos = liveStreamInfos;

    }

    public LiveStreamInfoEntity[] getLiveStreamInfos() {

        return liveStreamInfos;
    }

    public void setLiveStreamInfos( LiveStreamInfoEntity[] liveStreamInfos ) {

        this.liveStreamInfos = liveStreamInfos;

    }

    @Override
    public String toString() {
        return "LiveStreamInfosEntity{" +
                "liveStreamInfos=" + Arrays.toString( liveStreamInfos ) +
                '}';
    }

}

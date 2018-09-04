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

package org.mythtv.android.data.cache.serializer;

import com.google.gson.Gson;

import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
@Singleton
public class VideoListEntityJsonSerializer {

    private final Gson gson;

    @Inject
    public VideoListEntityJsonSerializer( final Gson gson ) {

        this.gson = gson;

    }

    public String serialize( VideoMetadataInfoListEntity videoListEntity ) {

        return this.gson.toJson( videoListEntity, VideoMetadataInfoListEntity.class );
    }

    public VideoMetadataInfoListEntity deserialize( String jsonString ) {

        return this.gson.fromJson( jsonString, VideoMetadataInfoListEntity.class );
    }

}

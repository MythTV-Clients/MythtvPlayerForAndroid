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

package org.mythtv.android.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListWrapperEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoWrapperEntity;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
public class VideoMetadataInfoEntityJsonMapper {

    private static final String TAG = VideoMetadataInfoEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public VideoMetadataInfoEntityJsonMapper( Gson gson ) {

        this.gson = gson;

    }

    public VideoMetadataInfoEntity transformVideoMetadataInfoEntity( Reader videoMetadataInfoJsonResponse ) throws JsonSyntaxException {
//        Log.d( TAG, "transformVideoMetadataInfoEntity : enter" );

//        Log.d( TAG, "transformVideoMetadataInfoEntity : videoMetadataInfoJsonResponse=" + videoMetadataInfoJsonResponse );
        Type videoMetadataInfoWrapperEntityType = new TypeToken<VideoMetadataInfoWrapperEntity>() {}.getType();
        VideoMetadataInfoWrapperEntity videoMetadataInfoWrapperEntity = this.gson.fromJson( videoMetadataInfoJsonResponse, videoMetadataInfoWrapperEntityType );

        return videoMetadataInfoWrapperEntity.getVideoMetadataInfo();
    }

    public List<VideoMetadataInfoEntity> transformVideoMetadataInfoEntityCollection( Reader videoMetadataInfoListJsonResponse ) throws JsonSyntaxException {

        Type videoMetadataInfoListEntityType = new TypeToken<VideoMetadataInfoListWrapperEntity>() {}.getType();
        VideoMetadataInfoListWrapperEntity videoMetadataInfoListEntity = gson.fromJson( videoMetadataInfoListJsonResponse, videoMetadataInfoListEntityType );

        return Arrays.asList( videoMetadataInfoListEntity.getVideoMetadataInfoListEntity().getVideoMetadataInfosEntity() );
    }

}

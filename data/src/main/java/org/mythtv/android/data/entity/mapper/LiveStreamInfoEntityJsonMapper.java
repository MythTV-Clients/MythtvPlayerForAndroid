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

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.LiveStreamInfoListEntity;
import org.mythtv.android.data.entity.LiveStreamInfoWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 10/17/15.
 */
public class LiveStreamInfoEntityJsonMapper {

    private static final String TAG = LiveStreamInfoEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject

    public LiveStreamInfoEntityJsonMapper() {

        Type dateTimeType = new TypeToken<DateTime>(){}.getType();

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter( dateTimeType, new DateTimeSerializer() )
                .registerTypeAdapter( dateTimeType, new DateTimeDeserializer() )
                .create();

    }

    public LiveStreamInfoEntity transformLiveStreamInfoEntity( String liveStreamInfoJsonResponse ) throws JsonSyntaxException {

        Log.i( TAG, "transformLiveStreamInfoEntity : liveStreamInfoJsonResponse=" + liveStreamInfoJsonResponse );
        Type liveStreamInfoWrapperEntityType = new TypeToken<LiveStreamInfoWrapperEntity>() {}.getType();
        LiveStreamInfoWrapperEntity liveStreamInfoWrapperEntity = this.gson.fromJson( liveStreamInfoJsonResponse, liveStreamInfoWrapperEntityType );

        return liveStreamInfoWrapperEntity.getLiveStreamInfo();
    }

    public List<LiveStreamInfoEntity> transformLiveStreamInfoEntityCollection( String liveStreamInfoListJsonResponse ) throws JsonSyntaxException {

        Log.i( TAG, "transformLiveStreamInfoEntityCollection : " + liveStreamInfoListJsonResponse );
        Type liveStreamInfoListEntityType = new TypeToken<LiveStreamInfoListEntity>() {}.getType();
        LiveStreamInfoListEntity liveStreamInfoListEntity = gson.fromJson( liveStreamInfoListJsonResponse, liveStreamInfoListEntityType );
        Log.i( TAG, "transformLiveStreamInfoEntityCollection : liveStreamInfoListEntity=" + liveStreamInfoListEntity.toString() );

        return Arrays.asList( liveStreamInfoListEntity.getLiveStreamInfos().getLiveStreamInfos() );
    }

}

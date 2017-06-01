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

package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.MythTvTypeAdapterFactory;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeTypeConverter;

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
public class VideoEntityJsonSerializer {

    private final Gson gson;

    @Inject
    public VideoEntityJsonSerializer() {

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapterFactory( MythTvTypeAdapterFactory.create() )
                .registerTypeAdapter( DateTime.class, new DateTimeTypeConverter() )
                .create();

    }

    public String serialize( VideoMetadataInfoEntity videoMetadataInfoEntity) {

        return this.gson.toJson( videoMetadataInfoEntity, VideoMetadataInfoEntity.class );
    }

    public VideoMetadataInfoEntity deserialize( String jsonString ) {

        return this.gson.fromJson( jsonString, VideoMetadataInfoEntity.class );
    }

}

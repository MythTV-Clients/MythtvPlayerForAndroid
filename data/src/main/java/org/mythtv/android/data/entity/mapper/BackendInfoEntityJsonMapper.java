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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.BackendInfoEntity;
import org.mythtv.android.data.entity.BackendInfoWrapperEntity;
import org.mythtv.android.data.entity.MythTvTypeAdapterFactory;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeTypeConverter;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 5/30/17.
 */
public class BackendInfoEntityJsonMapper {

    private final Gson gson;

    @Inject
    public BackendInfoEntityJsonMapper() {

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapterFactory( MythTvTypeAdapterFactory.create() )
                .registerTypeAdapter( DateTime.class, new DateTimeTypeConverter() )
                .create();

    }

    public BackendInfoEntity transformBackendInfoEntity( String backendInfoJsonResponse ) throws JsonSyntaxException {

        Type backendInfoWrapperEntityType = new TypeToken<BackendInfoWrapperEntity>() {}.getType();
        BackendInfoWrapperEntity backendInfoWrapperEntity = this.gson.fromJson( backendInfoJsonResponse, backendInfoWrapperEntityType );

        return backendInfoWrapperEntity.backendInfo();
    }

}

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
import org.mythtv.android.data.entity.MythTvTypeAdapterFactory;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.ProgramListEntity;
import org.mythtv.android.data.entity.ProgramWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeTypeConverter;

import java.lang.reflect.Type;
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
public class ProgramEntityJsonMapper {

    private final Gson gson;

    @Inject
    public ProgramEntityJsonMapper() {

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapterFactory( MythTvTypeAdapterFactory.create() )
                .registerTypeAdapter( DateTime.class, new DateTimeTypeConverter() )
                .create();

    }

    public ProgramEntity transformProgramEntity( String programJsonResponse ) throws JsonSyntaxException {

        Type programWrapperEntityType = new TypeToken<ProgramWrapperEntity>() {}.getType();
        ProgramWrapperEntity programWrapperEntity = this.gson.fromJson( programJsonResponse, programWrapperEntityType );

        return programWrapperEntity.program();
    }

    public List<ProgramEntity> transformProgramEntityCollection( String programListJsonResponse ) throws JsonSyntaxException {

        Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
        ProgramListEntity programListEntity = this.gson.fromJson( programListJsonResponse, programListEntityType );

        return programListEntity.programs().programs();
    }

}

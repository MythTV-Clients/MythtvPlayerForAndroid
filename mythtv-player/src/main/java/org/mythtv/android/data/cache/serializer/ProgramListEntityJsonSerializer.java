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

import org.mythtv.android.data.entity.ProgramListEntity;

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
public class ProgramListEntityJsonSerializer {

    private final Gson gson;

    @Inject
    public ProgramListEntityJsonSerializer( final Gson gson ) {

        this.gson = gson;

    }

    public String serialize( ProgramListEntity programListEntity ) {

        return this.gson.toJson( programListEntity, ProgramListEntity.class );
    }

    public ProgramListEntity deserialize( String jsonString ) {

        return this.gson.fromJson( jsonString, ProgramListEntity.class );
    }

}

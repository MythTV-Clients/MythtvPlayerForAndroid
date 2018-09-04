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

import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.ProgramListEntity;
import org.mythtv.android.data.entity.ProgramWrapperEntity;

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

//    private static final String TAG = ProgramEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public ProgramEntityJsonMapper( Gson gson ) {

        this.gson = gson;

    }

    public ProgramEntity transformProgramEntity( String programJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformProgramEntity : programJsonResponse=" + programJsonResponse );
        Type programWrapperEntityType = new TypeToken<ProgramWrapperEntity>() {}.getType();
        ProgramWrapperEntity programWrapperEntity = this.gson.fromJson( programJsonResponse, programWrapperEntityType );

        return programWrapperEntity.program();
    }

    public List<ProgramEntity> transformProgramEntityCollection( String programListJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformProgramEntityCollection : " + programListJsonResponse );
        Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
        ProgramListEntity programListEntity = this.gson.fromJson( programListJsonResponse, programListEntityType );
//        Log.i( TAG, "transformProgramEntityCollection : programListEntity=" + programListEntity.toString() );

        return programListEntity.programs().programs();
    }

}

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

import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.EncoderListEntity;
import org.mythtv.android.data.entity.EncoderWrapperEntity;

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
 * Created on 1/18/16.
 */
public class EncoderEntityJsonMapper {

    private static final String TAG = EncoderEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public EncoderEntityJsonMapper( Gson gson ) {

        this.gson = gson;

    }

    public EncoderEntity transformEncoderEntity( Reader encoderJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformEncoderEntity : encoderJsonResponse=" + encoderJsonResponse );
        Type encoderWrapperEntityType = new TypeToken<EncoderWrapperEntity>() {}.getType();
        EncoderWrapperEntity encoderWrapperEntity = this.gson.fromJson( encoderJsonResponse, encoderWrapperEntityType );

        return encoderWrapperEntity.getEncoder();
    }

    public List<EncoderEntity> transformEncoderEntityCollection( Reader encoderListJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformEncoderEntityCollection : " + encoderListJsonResponse );
        Type encoderListEntityType = new TypeToken<EncoderListEntity>() {}.getType();
        EncoderListEntity encoderListEntity = this.gson.fromJson( encoderListJsonResponse, encoderListEntityType );
//        Log.i( TAG, "transformEncoderEntityCollection : encoderListJsonResponse=" + encoderListEntity.toString() );

        return Arrays.asList( encoderListEntity.getEncoders().getEncoders() );
    }

}

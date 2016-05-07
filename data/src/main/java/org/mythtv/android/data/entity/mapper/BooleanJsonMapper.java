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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

/**
 * Created by dmfrey on 10/17/15.
 */
public class BooleanJsonMapper {

    private static final String TAG = BooleanJsonMapper.class.getSimpleName();

    private final JsonParser parser;

    @Inject
    public BooleanJsonMapper() {

        this.parser = new JsonParser();

    }

    public Boolean transformBoolean( String booleanJsonResponse ) throws JsonSyntaxException {

        Log.i( TAG, "transformBoolean : booleanJsonResponse=" + booleanJsonResponse );
        JsonObject rootObject = parser.parse( booleanJsonResponse ).getAsJsonObject();

        return rootObject.get( "bool" ).getAsBoolean();
    }

}

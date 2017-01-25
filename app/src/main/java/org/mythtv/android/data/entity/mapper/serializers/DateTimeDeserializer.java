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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/7/15.
 */
public class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    private static final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public DateTime deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {

        if( "".equals( json.getAsJsonPrimitive().getAsString() ) ) {

            return null;
        }

        return DateTime.parse( json.getAsJsonPrimitive().getAsString(), DateTimeFormat.forPattern( pattern ).withZoneUTC() );
    }

}

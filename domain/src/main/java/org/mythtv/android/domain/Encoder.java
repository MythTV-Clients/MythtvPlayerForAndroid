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

package org.mythtv.android.domain;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
@AutoValue
public abstract class Encoder {

    public abstract int id();

    @Nullable
    public abstract String inputName();

    @Nullable
    public abstract String recordingName();

    @Nullable
    public abstract String recordingDescription();

    public abstract int state();

    public static Encoder create( int id, String inputName, String recordingName, String recordingDescription, int state ) {

        return new AutoValue_Encoder( id, inputName, recordingName, recordingDescription, state );
    }

    public static TypeAdapter<Encoder> typeAdapter(Gson gson ) {

        return new AutoValue_Encoder.GsonTypeAdapter( gson );
    }

}

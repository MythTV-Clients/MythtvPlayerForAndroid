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

package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
@AutoValue
public abstract class InputEntity {

    @SerializedName( "Id" )
    public abstract int id();

    @SerializedName( "CardId" )
    public abstract int cardId();

    @SerializedName( "SourceId" )
    public abstract int sourceId();

    @SerializedName( "InputName" )
    public abstract String inputName();

    @SerializedName( "DisplayName" )
    public abstract String displayName();

    @SerializedName( "QuickTune" )
    public abstract boolean quickTune();

    @SerializedName( "RecPriority" )
    public abstract int recordPriority();

    @SerializedName( "ScheduleOrder" )
    public abstract int scheduleOrder();

    @SerializedName( "LiveTVOrder" )
    public abstract int liveTvOrder();

    public static InputEntity create( int id, int cardId, int sourceId, String inputName, String displayName, boolean quickTune, int recordPriority, int scheduleOrder, int liveTvOrder ) {

        return new AutoValue_InputEntity( id, cardId, sourceId, inputName, displayName, quickTune, recordPriority, scheduleOrder, liveTvOrder );
    }

    public static TypeAdapter<InputEntity> typeAdapter( Gson gson ) {

        return new AutoValue_InputEntity.GsonTypeAdapter( gson );
    }

}

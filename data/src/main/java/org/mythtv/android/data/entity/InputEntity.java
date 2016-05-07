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

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class InputEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "CardId" )
    private int cardId;

    @SerializedName( "SourceId" )
    private int sourceId;

    @SerializedName( "InputName" )
    private String inputName;

    @SerializedName( "DisplayName" )
    private String displayName;

    @SerializedName( "QuickTune" )
    private boolean quickTune;

    @SerializedName( "RecPriority" )
    private int recordPriority;

    @SerializedName( "ScheduleOrder" )
    private int scheduleOrder;

    @SerializedName( "LiveTVOrder" )
    private int liveTvOrder;

}

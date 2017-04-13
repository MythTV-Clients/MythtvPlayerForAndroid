/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

import java.util.List;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
@AutoValue
public abstract class ChannelInfoEntity {

    @SerializedName( "ChanId" )
    public abstract int chanId();

    @Nullable
    @SerializedName( "ChanNum" )
    public abstract String chanNum();

    @Nullable
    @SerializedName( "CallSign" )
    public abstract String callSign();

    @Nullable
    @SerializedName( "IconURL" )
    public abstract String iconURL();

    @Nullable
    @SerializedName( "ChannelName" )
    public abstract String channelName();

    @SerializedName( "MplexId" )
    public abstract int mplexId();

    @SerializedName( "ServiceId" )
    public abstract int serviceId();

    @SerializedName( "ATSCMajorChan" )
    public abstract int aTSCMajorChan();

    @SerializedName( "ATSCMinorChan" )
    public abstract int aTSCMinorChan();

    @Nullable
    @SerializedName( "Format" )
    public abstract String format();

    @Nullable
    @SerializedName( "FrequencyId" )
    public abstract String frequencyId();

    @SerializedName( "FineTune" )
    public abstract int fineTune();

    @Nullable
    @SerializedName( "ChanFilters" )
    public abstract String chanFilters();

    @SerializedName( "SourceId" )
    public abstract int sourceId();

    @SerializedName( "InputId" )
    public abstract int inputId();

//    @SerializedName( "CommFree" )
    public abstract boolean commFree();

    @SerializedName( "UseEIT" )
    public abstract boolean useEIT();

    @SerializedName( "Visible" )
    public abstract boolean visible();

    @Nullable
    @SerializedName( "XMLTVID" )
    public abstract String xMLTVID();

    @Nullable
    @SerializedName( "DefaultAuth" )
    public abstract String defaultAuth();

    @Nullable
    @SerializedName( "Programs" )
    public abstract List<ProgramEntity> programs();

    public static ChannelInfoEntity create( int chanId, String chanNum, String callSign, String iconURL, String channelName, int mplexId, int serviceId, int aTSCMajorChan, int aTSCMinorChan, String format, String frequencyId, int fineTune, String chanFilters, int sourceId, int inputId, boolean commFree, boolean useEIT, boolean visible, String xMLTVID, String defaultAuth, List<ProgramEntity> programs ) {

        return new AutoValue_ChannelInfoEntity( chanId, chanNum, callSign, iconURL, channelName, mplexId, serviceId, aTSCMajorChan, aTSCMinorChan, format, frequencyId, fineTune, chanFilters, sourceId, inputId, commFree, useEIT, visible, xMLTVID, defaultAuth, programs );
    }

    public static TypeAdapter<ChannelInfoEntity> typeAdapter(Gson gson ) {

        return new AutoValue_ChannelInfoEntity.GsonTypeAdapter( gson );
    }

}

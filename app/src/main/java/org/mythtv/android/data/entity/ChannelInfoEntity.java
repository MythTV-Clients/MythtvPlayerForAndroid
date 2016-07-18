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

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/*
 * Created by dmfrey on 11/12/14.
 */
@Data
public class ChannelInfoEntity {

    @SerializedName( "ChanId" )
    private int chanId;

    @SerializedName( "ChanNum" )
    private String chanNum;

    @SerializedName( "CallSign" )
    private String callSign;

    @SerializedName( "IconURL" )
    private String iconURL;

    @SerializedName( "ChannelName" )
    private String channelName;

    @SerializedName( "MplexId" )
    private int mplexId;

    @SerializedName( "ServiceId" )
    private int serviceId;

    @SerializedName( "ATSCMajorChan" )
    private int aTSCMajorChan;

    @SerializedName( "ATSCMinorChan" )
    private int aTSCMinorChan;

    @SerializedName( "Format" )
    private String format;

    @SerializedName( "FrequencyId" )
    private String frequencyId;

    @SerializedName( "FineTune" )
    private int fineTune;

    @SerializedName( "ChanFilters" )
    private String chanFilters;

    @SerializedName( "SourceId" )
    private int sourceId;

    @SerializedName( "InputId" )
    private int inputId;

//    @SerializedName( "CommFree" )
    private boolean commFree;

    @SerializedName( "UseEIT" )
    private boolean useEIT;

    @SerializedName( "Visible" )
    private boolean visible;

    @SerializedName( "XMLTVID" )
    private String xMLTVID;

    @SerializedName( "DefaultAuth" )
    private String defaultAuth;

    @SerializedName( "Programs" )
    private ProgramEntity[] programs;

}

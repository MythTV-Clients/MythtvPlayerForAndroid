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

package org.mythtv.android.presentation.utils;

import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Created by dmfrey on 1/13/16.
 */
public class SeasonEpisodeFormatter {

    private SeasonEpisodeFormatter() {}

    public static String format( VideoMetadataInfoModel videoMetadataInfoModel ) {

        if( videoMetadataInfoModel.getSeason() == -1 || videoMetadataInfoModel.getEpisode() == -1 ) {

            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "S" );
        if( videoMetadataInfoModel.getSeason() < 10 ) {
            sb.append( "0" );
        }
        sb.append( videoMetadataInfoModel.getSeason() );

        sb.append( "E" );
        if( videoMetadataInfoModel.getEpisode() < 10 ) {
            sb.append( "0" );
        }
        sb.append( videoMetadataInfoModel.getEpisode() );

        return sb.toString();
    }

    public static String format( SearchResultModel searchResultModel ) {

        if( searchResultModel.getSeason() == -1 || searchResultModel.getEpisode() == -1 ) {

            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "S" );
        if( searchResultModel.getSeason() < 10 ) {
            sb.append( "0" );
        }
        sb.append( searchResultModel.getSeason() );

        sb.append( "E" );
        if( searchResultModel.getEpisode() < 10 ) {
            sb.append( "0" );
        }
        sb.append( searchResultModel.getEpisode() );

        return sb.toString();
    }

}

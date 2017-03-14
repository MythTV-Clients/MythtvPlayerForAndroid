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

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/13/16.
 */
public class SeasonEpisodeFormatter {

    private SeasonEpisodeFormatter() {}

    public static String format( int season, int episode ) {

        if( season <= 0 || episode <= 0 ) {

            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append( 'S' );
        if( season < 10 ) {
            sb.append( '0' );
        }
        sb.append( season ).append( 'E' );
        if( episode < 10 ) {
            sb.append( '0' );
        }
        sb.append( episode );

        return sb.toString();

    }

}

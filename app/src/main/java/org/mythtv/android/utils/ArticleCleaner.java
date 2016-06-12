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

package org.mythtv.android.utils;

/**
 * Created by dmfrey on 2/1/16.
 */
public class ArticleCleaner {

    private ArticleCleaner() { }

    public static String clean( String value ) {

        if( null == value || "".equals( value ) ) {
            return value;
        }

        String upper = value.toUpperCase();
        if( upper.startsWith( "THE " ) ) {
            value = value.substring( "THE ".length() );
        }

        if( upper.startsWith( "AN " ) ) {
            value = value.substring( "AN ".length() );
        }

        if( upper.startsWith( "A " ) ) {
            value = value.substring( "A ".length() );
        }

        return value;
    }

}

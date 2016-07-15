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

import android.content.Context;
import android.util.Log;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 2/1/16.
 */
public class ArticleCleaner {

    private static final String TAG = ArticleCleaner.class.getSimpleName();

    private ArticleCleaner() { }

    public static String clean( final Context context, String value ) {
        Log.d( TAG, "clean : enter" );

        if( null == value || "".equals( value ) ) {
            return value;
        }

        String[] articles = context.getResources().getStringArray( R.array.articles );
        String upper = value.toUpperCase();

        for( String article : articles ) {
            Log.d( TAG, "clean : article=" + article + ", value=" + value );

            if( upper.startsWith( article ) ) {
                Log.d( TAG, "clean : article found" );

                value = value.substring( article.length() ).trim();
            }

        }

        Log.d( TAG, "clean : exit - value=" + value );
        return value;
    }

}

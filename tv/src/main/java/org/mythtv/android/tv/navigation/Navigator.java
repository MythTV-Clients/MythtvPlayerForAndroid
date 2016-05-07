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

package org.mythtv.android.tv.navigation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.mythtv.android.tv.view.activity.RecordingsActivity;
import org.mythtv.android.tv.view.activity.SettingsActivity;
import org.mythtv.android.tv.view.activity.VideoCategoryActivity;
import org.mythtv.android.tv.view.activity.VideosActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/28/16.
 */
@Singleton
public class Navigator {

    private static final String TAG = Navigator.class.getSimpleName();

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToHome( Context context ) {
        Log.d( TAG, "navigateToHome : enter" );

        Log.d( TAG, "navigateToHome : exit" );
    }

    public void navigateToRecordings( Context context ) {
        Log.d( TAG, "navigateToRecordings : enter" );

        if( null != context ) {

            Intent intentToLaunch = RecordingsActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToRecordings : exit" );
    }

    public void navigateToVideos( Context context ) {
        Log.d( TAG, "navigateToVideos : enter" );

        if( null != context ) {

            Intent intentToLaunch = VideosActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToVideos : exit" );
    }

    public void navigateToVideoCategory( Context context, String category ) {
        Log.d( TAG, "navigateToVideoCategory : enter" );

        if( null != context ) {

            Intent intentToLaunch = VideoCategoryActivity.getCallingIntent( context, category );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToVideos : exit" );
    }

    public void navigateToSettings( Context context ) {
        Log.d( TAG, "navigateToSettings : enter" );

        if( null != context ) {

            Intent intentToLaunch = SettingsActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToSettings : exit" );
    }

}

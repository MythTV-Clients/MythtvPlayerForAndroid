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

package org.mythtv.android.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.view.activity.tv.RecordingsActivity;
import org.mythtv.android.presentation.view.activity.tv.SearchableActivity;
import org.mythtv.android.presentation.view.activity.tv.SettingsActivity;
import org.mythtv.android.presentation.view.activity.tv.VideoCategoryActivity;
import org.mythtv.android.presentation.view.activity.tv.VideosActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/28/16.
 */
@Singleton
public class TvNavigator {

    private static final String TAG = TvNavigator.class.getSimpleName();

    @Inject
    public TvNavigator() {
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

    public void navigateToVideoCategory( Context context, Media media ) {
        Log.d( TAG, "navigateToVideoCategory : enter" );

        if( null != context ) {

            Intent intentToLaunch = VideoCategoryActivity.getCallingIntent( context, media );
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

    public void navigateToSearch( Context context ) {
        Log.d( TAG, "navigateToSearch : enter" );

        if( null != context ) {

            Intent intentToLaunch = SearchableActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToSearch : exit" );
    }

}

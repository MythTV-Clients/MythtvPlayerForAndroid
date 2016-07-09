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
import android.net.Uri;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.presentation.view.activity.phone.MainPhoneActivity;
import org.mythtv.android.presentation.view.activity.phone.PlayerActivity;
import org.mythtv.android.presentation.view.activity.phone.ProgramDetailsActivity;
import org.mythtv.android.presentation.view.activity.phone.ProgramDetailsSettingsActivity;
import org.mythtv.android.presentation.view.activity.phone.ProgramListActivity;
import org.mythtv.android.presentation.view.activity.phone.SettingsActivity;
import org.mythtv.android.presentation.view.activity.phone.TitleInfoListActivity;
import org.mythtv.android.presentation.view.activity.phone.VideoDetailsActivity;
import org.mythtv.android.presentation.view.activity.phone.VideoDetailsSettingsActivity;
import org.mythtv.android.presentation.view.activity.phone.VideoListActivity;
import org.mythtv.android.presentation.view.activity.phone.VideoSeriesListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 *
 * Created by dmfrey on 8/30/15.
 */
@Singleton
public class PhoneNavigator {

    private static final String TAG = PhoneNavigator.class.getSimpleName();

    @Inject
    public PhoneNavigator() {
        //empty
    }

    public void navigateToHome( Context context ) {
        Log.d( TAG, "navigateToHome : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToHome : context != null" );

            Intent intentToLaunch = MainPhoneActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToHome : exit" );
    }

    public void navigateToTitleInfos( Context context ) {
        Log.d( TAG, "navigateToTitleInfos : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToTitleInfos : context != null" );

            context.startActivity( TitleInfoListActivity.getCallingIntent( context ) );

        }

        Log.d( TAG, "navigateToTitleInfos : exit" );
    }

    public void navigateToPrograms( Context context, boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "navigateToPrograms : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToPrograms : context != null" );

            context.startActivity( ProgramListActivity.getCallingIntent( context, descending, startIndex, count, titleRegEx, recGroup, storageGroup ) );

        }

        Log.d( TAG, "navigateToPrograms : exit" );
    }

    public void navigateToProgram( Context context, int chanId, DateTime startTime ) {
        Log.d( TAG, "navigateToProgram : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToProgram : context != null" );

            context.startActivity( ProgramDetailsActivity.getCallingIntent( context, chanId, startTime ) );

        }

        Log.d( TAG, "navigateToProgram : exit" );
    }

    public void navigateToProgramSettings( Context context ) {
        Log.d( TAG, "navigateToProgramSettings : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToProgramSettings : context != null" );

            context.startActivity( ProgramDetailsSettingsActivity.getCallingIntent( context ) );

        }

        Log.d( TAG, "navigateToProgramSettings : exit" );
    }

    public void navigateToVideos( Context context ) {
        Log.d( TAG, "navigateToVideos : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToVideos : context != null" );

            context.startActivity( VideoListActivity.getCallingIntent( context ) );

        }

        Log.d( TAG, "navigateToVideos : exit" );
    }

    public void navigateToVideoSeries( Context context, String series ) {
        Log.d( TAG, "navigateToVideoSeries : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToVideoSeries : context != null" );

            context.startActivity( VideoSeriesListActivity.getCallingIntent( context, series ) );

        }

        Log.d( TAG, "navigateToVideoSeries : exit" );
    }

    public void navigateToVideo( Context context, int id, String storeageGroup, String filename, String hostname ) {
        Log.d( TAG, "navigateToVideo : enter" );

        if( null != context ) {
            Log.d(TAG, "navigateToVideo : context != null");

            context.startActivity( VideoDetailsActivity.getCallingIntent( context, id ) );

        }

        Log.d( TAG, "navigateToVideo : exit" );
    }

    public void navigateToVideoSettings( Context context ) {
        Log.d( TAG, "navigateToVideoSettings : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToVideoSettings : context != null" );

            context.startActivity( VideoDetailsSettingsActivity.getCallingIntent( context ) );

        }

        Log.d( TAG, "navigateToVideoSettings : exit" );
    }

    public void navigateToExternalPlayer( Context context, String uri ) {
        Log.d( TAG, "navigateToExternalPlayer : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToExternalPlayer : context != null" );
            Log.d( TAG, "navigateToExternalPlayer : uri=" + uri );

            Intent intentToLaunch = new Intent( Intent.ACTION_VIEW );
            intentToLaunch.setDataAndType( Uri.parse( uri ), "video/*" );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToInternalPlayer : exit" );
    }

    public void navigateToVideoPlayer( Context context, String uri ) {
        Log.d( TAG, "navigateToVideoPlayer : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToVideoPlayer : context != null" );
            Log.d( TAG, "navigateToVideoPlayer : uri=" + uri );

            context.startActivity( PlayerActivity.getCallingIntent( context, uri ) );

        }

        Log.d( TAG, "navigateToVideoPlayer : exit" );
    }

    public void navigateToSettings( Context context ) {
        Log.d( TAG, "navigateToSettings : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToSettings : context != null" );

            Intent intentToLaunch = SettingsActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToSettings : exit" );
    }

}

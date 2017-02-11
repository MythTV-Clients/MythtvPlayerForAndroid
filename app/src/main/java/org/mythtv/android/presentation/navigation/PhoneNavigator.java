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

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.activity.phone.LocalPlayerActivity;
import org.mythtv.android.presentation.view.activity.phone.MainPhoneActivity;
import org.mythtv.android.presentation.view.activity.phone.MediaItemDetailsActivity;
import org.mythtv.android.presentation.view.activity.phone.SeriesListActivity;
import org.mythtv.android.presentation.view.activity.phone.SettingsActivity;
import org.mythtv.android.presentation.view.activity.phone.TitleInfoListActivity;
import org.mythtv.android.presentation.view.activity.phone.TroubleshootActivity;
import org.mythtv.android.presentation.view.activity.phone.VideoListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
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

    public void navigateToSeries( Context context, Media media, boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup, String inetref ) {
        Log.d( TAG, "navigateToSeries : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToSeries : context != null" );

            context.startActivity( SeriesListActivity.getCallingIntent( context, media, descending, startIndex, count, titleRegEx, recGroup, storageGroup, inetref ) );

        }

        Log.d( TAG, "navigateToSeries : exit" );
    }

    public void navigateToMediaItem( final Context context, final int id, final Media media, final ActivityOptions options ) {
        Log.d( TAG, "navigateToMediaItem : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToMediaItem : context != null" );

            if( null != options ) {

                context.startActivity( MediaItemDetailsActivity.getCallingIntent(context, id, media), options.toBundle() );

            } else {

                context.startActivity( MediaItemDetailsActivity.getCallingIntent( context, id, media ) );

            }

        }

        Log.d( TAG, "navigateToMediaItem : exit" );
    }

    public void navigateToVideos( Context context ) {
        Log.d( TAG, "navigateToVideos : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToVideos : context != null" );

            context.startActivity( VideoListActivity.getCallingIntent( context ) );

        }

        Log.d( TAG, "navigateToVideos : exit" );
    }

    public void navigateToExternalPlayer( Context context, String uri, String contentType ) {
        Log.d( TAG, "navigateToExternalPlayer : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToExternalPlayer : context != null" );
            Log.d( TAG, "navigateToExternalPlayer : uri=" + uri + ", contentType=" + contentType );

            Intent intentToLaunch = new Intent( Intent.ACTION_VIEW );
            intentToLaunch.setDataAndType( Uri.parse( uri ), contentType );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToInternalPlayer : exit" );
    }

    public void navigateToLocalPlayer( Context context, MediaItemModel mediaItemModel ) {
        Log.d( TAG, "navigateToLocalPlayer : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToLocalPlayer : context != null" );
            Log.d( TAG, "navigateToLocalPlayer : mediaItemModel=" + mediaItemModel );

            context.startActivity( LocalPlayerActivity.getCallingIntent( context, mediaItemModel ) );

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

    public void navigateToTroubleshoot( Context context ) {
        Log.d( TAG, "navigateToTroubleshoot : enter" );

        if( null != context ) {
            Log.d( TAG, "navigateToTroubleshoot : context != null" );

            Intent intentToLaunch = TroubleshootActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );

        }

        Log.d( TAG, "navigateToTroubleshoot : exit" );
    }

}

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.mythtv.android.presentation.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.MediaItemModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * A collection of utility methods, all static.
 *
 * @author dmfrey
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /*
     * Making sure public utility methods remain static
     */
    private Utils() {
    }

    /**
     * Returns the screen/display size
     */
    public static Point getDisplaySize( Context context ) {

        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize( size );

        return size;
    }

    /**
     * Returns {@code true} if and only if the screen orientation is portrait.
     */
    public static boolean isOrientationPortrait( Context context ) {

        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Shows an error dialog with a given text message.
     */
    public static void showErrorDialog( Context context, String errorString ) {

        new AlertDialog.Builder( context ).setTitle( R.string.error )
                .setMessage( errorString )
                .setPositiveButton( R.string.ok, ( dialog, id ) -> dialog.cancel() )
                .create()
                .show();
    }


    /**
     * Shows a (long) toast
     */
    public static void showToast( Context context, String msg ) {

        Toast.makeText( context, msg, Toast.LENGTH_LONG ).show();

    }

    /**
     * Shows a (long) toast.
     */
    public static void showToast( Context context, int resourceId ) {

        Toast.makeText( context, context.getString( resourceId ), Toast.LENGTH_LONG ).show();

    }

    public static int convertDpToPixel( Context ctx, int dp ) {

        float density = ctx.getResources().getDisplayMetrics().density;

        return Math.round( (float) dp * density );
    }

    /**
     * Formats time in milliseconds to hh:mm:ss string format.
     */
    public static String formatMillis( int millis ) {

        String result = "";
        int hr = millis / 3600000;
        millis %= 3600000;
        int min = millis / 60000;
        millis %= 60000;
        int sec = millis / 1000;
        if( hr > 0 ) {
            result += hr + ":";
        }
        if( min >= 0 ) {
            if( min > 9 ) {
                result += min + ":";
            } else {
                result += "0" + min + ":";
            }
        }
        if( sec > 9 ) {
            result += sec;
        } else {
            result += "0" + sec;
        }

        return result;
    }

    public static long getDuration( String videoUrl ) {

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
            mmr.setDataSource( videoUrl, new HashMap<>() );
        } else {
            mmr.setDataSource( videoUrl );
        }

        return Long.parseLong( mmr.extractMetadata( MediaMetadataRetriever.METADATA_KEY_DURATION ) );
    }

    // TODO: add filter here for only displaying HLS entries issue #203
    public static List<MediaItemModel> filter( SharedPreferences sharedPreferences, Collection<MediaItemModel> mediaItemsCollection ) {
        Log.d( TAG, "filter : enter" );

        List<MediaItemModel> mediaItems = new ArrayList<>();

        boolean filterByGroup = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, false );
        String filterGroup = sharedPreferences.getString( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, "" );
        Log.d( TAG, "filter : filterByGroup=" + filterByGroup + ", filterGroup=" + filterGroup );

        boolean filterByParentalLevel = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_PARENTAL_CONTROLS, false );
        int parentalLevel;
        try {
            parentalLevel = Integer.parseInt( sharedPreferences.getString( SettingsKeys.KEY_PREF_PARENTAL_CONTROL_LEVEL, "4" ) );
        } catch( NumberFormatException e ) {
            parentalLevel = 4;
        }
        Log.d( TAG, "filter : filterByParentalLevel=" + filterByParentalLevel + ", parentalLevel=" + parentalLevel );

        boolean filterByCertification = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RESTRICT_CONTENT_TYPES, false );
        boolean ratingNR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NR, false );
        boolean ratingG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_G, false );
        boolean ratingPG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG, false );
        boolean ratingPG13 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG13, false );
        boolean ratingR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_R, false );
        boolean ratingNC17 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NC17, false );
        Log.d( TAG, "filter : filterByCertification=" + filterByCertification + ", NR=" + ratingNR + ", G=" + ratingG + ", PG=" + ratingPG + ", PG-13=" + ratingPG13 + ", R=" + ratingR + ", NC-17=" + ratingNC17 );

        for ( MediaItemModel mediaItemModel: mediaItemsCollection ) {

            boolean filtered = false;

            if( mediaItemModel.getMedia().equals( Media.UPCOMING ) ) {
                Log.d( TAG, "filter : always filter upcoming" );

                filtered = true;

            } else if( mediaItemModel.getMedia().equals( Media.PROGRAM ) ) {

                if( filterByGroup && !filterGroup.equals( mediaItemModel.getRecordingGroup() ) ) {
                    Log.d( TAG, "filter : recording group does not matches" );

                    continue;

                } else {

                    filtered = true;

                }

            } else {

                if( filterByParentalLevel && mediaItemModel.getParentalLevel() > parentalLevel ) {
                    Log.d( TAG, "filter : does not meet parental level, skipping..." );

                    continue;
                }

                if( filterByCertification ) {
                    Log.d( TAG, "filter : filtering by certification '" + mediaItemModel.getCertification() + "'" );

                    if( ratingNR && mediaItemModel.getCertification().equals( "NR" ) ) {
                        filtered = true;
                    }
                    if( ratingG && ( mediaItemModel.getCertification().equals( "G" ) || mediaItemModel.getCertification().equals( "TV-Y" ) ) ) {
                        filtered = true;
                    }
                    if( ratingPG && ( mediaItemModel.getCertification().endsWith( "PG" ) || mediaItemModel.getCertification().endsWith( "TV-PG" ) || mediaItemModel.getCertification().endsWith( "TV-Y7" ) ) ) {
                        filtered = true;
                    }
                    if( ratingPG13 && ( mediaItemModel.getCertification().equals( "PG-13" ) || mediaItemModel.getCertification().equals( "TV-14" ) ) ) {
                        filtered = true;
                    }
                    if( ratingR && ( mediaItemModel.getCertification().equals( "R" ) || mediaItemModel.getCertification().equals( "TV-MA" ) ) ) {
                        filtered = true;
                    }
                    if( ratingNC17 && mediaItemModel.getCertification().equals( "NC-17" ) ) {
                        filtered = true;
                    }

                } else {

                    filtered = true;

                }

            }

            if( filtered ) {
                Log.d( TAG, "filter : adding to list" );

                mediaItems.add( mediaItemModel );
            }

        }

        Log.d( TAG, "filter : exit" );
        return mediaItems;
    }

}

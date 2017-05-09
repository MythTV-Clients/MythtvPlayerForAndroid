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
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import org.mythtv.android.R;

import java.util.HashMap;

/**
 * A collection of utility methods, all static.
 *
 * @author dmfrey
 */
public final class Utils {

//    private static final String TAG = Utils.class.getSimpleName();

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
        int millisHr = millis % 3600000;
        int min = millisHr / 60000;
        int millisMin = millisHr % 60000;
        int sec = millisMin / 1000;
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
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ) {
            mmr.setDataSource( videoUrl, new HashMap<>() );
        } else {
            mmr.setDataSource( videoUrl );
        }

        return Long.parseLong( mmr.extractMetadata( MediaMetadataRetriever.METADATA_KEY_DURATION ) );
    }

    public static boolean meetsMinimumVersion( String version, float minimumVersion ) {

        if( "Unknown".equals( version ) || version.matches( "\\b[0-9a-f]{5,40}\\b" ) ) {

            return false;
        }

        String cleanVersion = version;
        if( cleanVersion.startsWith( "v" ) ) {
            cleanVersion = cleanVersion.substring( 1 );
        }

        if( cleanVersion.contains( "-" ) ) {

            cleanVersion = cleanVersion.substring( 0, cleanVersion.indexOf( '-' ) );

        }

        if( cleanVersion.length() > 4 ) {

            cleanVersion = cleanVersion.substring( 0, 4 );

        }

        try {

            Float.parseFloat( cleanVersion );

        } catch( NumberFormatException e ) {

            return false;
        }

        float extractedVersion = Float.parseFloat( cleanVersion );

        if( !cleanVersion.startsWith( "0" ) ) {

            extractedVersion *= 100;

        }

        return ( extractedVersion >= minimumVersion );
    }

}

package org.mythtv.android.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.MediaItemModel;

/**
 *
 * @author dmfrey
 *
 * Created on 2/22/17.
 */
public final class MediaItemFilter {

    private static final String TAG = MediaItemFilter.class.getSimpleName();

    private MediaItemFilter() {
        // this is a private constructor and intentionally left blank
    }

    public static boolean filter( MediaItemModel mediaItemModel, Context context ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        boolean filterHlsOnlyPreference = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_FILTER_HLS_ONLY, false );
        Log.d( TAG, "filter : filterHlsOnlyPreference=" + filterHlsOnlyPreference );

        switch( mediaItemModel.media() ) {

            case UPCOMING :
                Log.d( TAG, "filter : media type 'UPCOMING'" );

                return true;

            case PROGRAM :
                Log.d( TAG, "filter : media type 'PROGRAM'" );

                return filterProgram( context, mediaItemModel );

            default :
                Log.d( TAG, "filter : media type 'VIDEO'" );

                return filterVideo( context, mediaItemModel );

        }

    }

    private static boolean filterProgram( final Context context, final MediaItemModel mediaItemModel ) {
        Log.d( TAG, "filterProgram : enter" );

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        boolean filterHlsOnlyPreference = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_FILTER_HLS_ONLY, false );
        Log.d( TAG, "filterProgram : filterHlsOnlyPreference=" + filterHlsOnlyPreference );

        if( filterHlsOnlyPreference ) {
            Log.d( TAG, "filterProgram : filtering HLS only" );

            if( mediaItemModel.liveStreamId() <= 0 ) {
                Log.d( TAG, "filterProgram : mediaItem has not been HLS transcoded" );

                return false;
            }

        }

        boolean filterByGroup = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, false );
        String filterGroup = sharedPreferences.getString( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, "" );
        Log.d( TAG, "filterProgram : filterByGroup=" + filterByGroup + ", filterGroup=" + filterGroup );

        if( filterByGroup && !filterGroup.equals( mediaItemModel.recordingGroup() ) ) {
            Log.d( TAG, "filterProgram : recording group does not match" );

            return false;

        }

        Log.d( TAG, "filterProgram : recording group filter not set" );
        return true;
    }

    private static boolean filterVideo(final Context context, final MediaItemModel mediaItemModel ) {
        Log.d( TAG, "filterVideo : enter" );

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        boolean filterHlsOnlyPreference = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_FILTER_HLS_ONLY, false );
        Log.d( TAG, "filterVideo : filterHlsOnlyPreference=" + filterHlsOnlyPreference );

        if( filterHlsOnlyPreference ) {
            Log.d( TAG, "filterVideo : filtering HLS only" );

            if( mediaItemModel.liveStreamId() <= 0 ) {
                Log.d( TAG, "filterVideo : mediaItem has not been HLS transcoded" );

                return false;
            }

        }

        boolean filterByParentalLevel = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_PARENTAL_CONTROLS, false );
        int parentalLevel;
        try {
            parentalLevel = Integer.parseInt( sharedPreferences.getString( SettingsKeys.KEY_PREF_PARENTAL_CONTROL_LEVEL, "4" ) );
        } catch( NumberFormatException e ) {
            parentalLevel = 4;
        }
        final int finalParentalLevel = parentalLevel;
        Log.d( TAG, "filterVideo : filterByParentalLevel=" + filterByParentalLevel + ", finalParentalLevel=" + finalParentalLevel );

        if( filterByParentalLevel && mediaItemModel.parentalLevel() > finalParentalLevel ) {
            Log.d( TAG, "filterVideo : does not meet parental level, skipping..." );

            return false;
        }

        boolean filterByCertification = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RESTRICT_CONTENT_TYPES, false );
        boolean ratingNR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NR, false );
        boolean ratingG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_G, false );
        boolean ratingPG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG, false );
        boolean ratingPG13 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG13, false );
        boolean ratingR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_R, false );
        boolean ratingNC17 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NC17, false );
        Log.d( TAG, "filterVideo : filterByCertification=" + filterByCertification + ", NR=" + ratingNR + ", G=" + ratingG + ", PG=" + ratingPG + ", PG-13=" + ratingPG13 + ", R=" + ratingR + ", NC-17=" + ratingNC17 );

        if( filterByCertification ) {
            Log.d( TAG, "filterVideo : filtering by certification '" + mediaItemModel.certification() + "'" );

            if( ratingNR && mediaItemModel.certification().equals( "NR" ) ) {
                return true;
            } else if( ratingG && ( mediaItemModel.certification().equals( "G" ) || mediaItemModel.certification().equals( "TV-Y" ) ) ) {
                return true;
            } else if( ratingPG && ( mediaItemModel.certification().endsWith( "PG" ) || mediaItemModel.certification().endsWith( "TV-PG" ) || mediaItemModel.certification().endsWith( "TV-Y7" ) ) ) {
                return true;
            } else if( ratingPG13 && ( mediaItemModel.certification().equals( "PG-13" ) || mediaItemModel.certification().equals( "TV-14" ) ) ) {
                return true;
            } else if( ratingR && ( mediaItemModel.certification().equals( "R" ) || mediaItemModel.certification().equals( "TV-MA" ) ) ) {
                return true;
            } else {
                return ratingNC17 && mediaItemModel.certification().equals("NC-17");
            }

        }

        Log.d( TAG, "filterVideo : exit, fallback" );
        return true;
    }

}

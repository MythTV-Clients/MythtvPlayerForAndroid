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
public class MediaItemFilter {

    private static final String TAG = MediaItemFilter.class.getSimpleName();

    public static boolean filter( MediaItemModel mediaItemModel, Context context ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        boolean filterHlsOnlyPreference = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_FILTER_HLS_ONLY, false );
        Log.d( TAG, "filter : filterHlsOnlyPreference=" + filterHlsOnlyPreference );

        switch( mediaItemModel.getMedia() ) {

            case UPCOMING :
                Log.d( TAG, "filter : media type 'UPCOMING'" );

                return true;

            case PROGRAM :
                Log.d( TAG, "filter : media type 'PROGRAM'" );

                if( filterHlsOnlyPreference ) {
                    Log.d( TAG, "filter : filtering HLS only" );

                    if( mediaItemModel.getLiveStreamId() <= 0 ) {
                        Log.d( TAG, "filter : mediaItem has not been HLS transcoded" );

                        return false;
                    }

                }

                boolean filterByGroup = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, false );
                String filterGroup = sharedPreferences.getString( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, "" );
                Log.d( TAG, "filter : filterByGroup=" + filterByGroup + ", filterGroup=" + filterGroup );

                if( filterByGroup && !filterGroup.equals( mediaItemModel.getRecordingGroup() ) ) {
                    Log.d( TAG, "filter : recording group does not match" );

                    return false;

                }

                Log.d( TAG, "filter : recording group filter not set" );

                return true;

            default :
                Log.d( TAG, "filter : media type 'VIDEO'" );

                if( filterHlsOnlyPreference ) {
                    Log.d( TAG, "filter : filtering HLS only" );

                    if( mediaItemModel.getLiveStreamId() <= 0 ) {
                        Log.d( TAG, "filter : mediaItem has not been HLS transcoded" );

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
                Log.d( TAG, "filter : filterByParentalLevel=" + filterByParentalLevel + ", finalParentalLevel=" + finalParentalLevel );

                if( filterByParentalLevel ) {

                    if( mediaItemModel.getParentalLevel() > finalParentalLevel ) {
                        Log.d( TAG, "filter : does not meet parental level, skipping..." );

                        return false;
                    }

                }

                boolean filterByCertification = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RESTRICT_CONTENT_TYPES, false );
                boolean ratingNR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NR, false );
                boolean ratingG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_G, false );
                boolean ratingPG = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG, false );
                boolean ratingPG13 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_PG13, false );
                boolean ratingR = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_R, false );
                boolean ratingNC17 = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_RATING_NC17, false );
                Log.d( TAG, "filter : filterByCertification=" + filterByCertification + ", NR=" + ratingNR + ", G=" + ratingG + ", PG=" + ratingPG + ", PG-13=" + ratingPG13 + ", R=" + ratingR + ", NC-17=" + ratingNC17 );

                if( filterByCertification ) {
                    Log.d( TAG, "filter : filtering by certification '" + mediaItemModel.getCertification() + "'" );

                    if( ratingNR && mediaItemModel.getCertification().equals( "NR" ) ) {
                        return true;
                    } else if( ratingG && ( mediaItemModel.getCertification().equals( "G" ) || mediaItemModel.getCertification().equals( "TV-Y" ) ) ) {
                        return true;
                    } else if( ratingPG && ( mediaItemModel.getCertification().endsWith( "PG" ) || mediaItemModel.getCertification().endsWith( "TV-PG" ) || mediaItemModel.getCertification().endsWith( "TV-Y7" ) ) ) {
                        return true;
                    } else if( ratingPG13 && ( mediaItemModel.getCertification().equals( "PG-13" ) || mediaItemModel.getCertification().equals( "TV-14" ) ) ) {
                        return true;
                    } else if( ratingR && ( mediaItemModel.getCertification().equals( "R" ) || mediaItemModel.getCertification().equals( "TV-MA" ) ) ) {
                        return true;
                    } else if( ratingNC17 && mediaItemModel.getCertification().equals( "NC-17" ) ) {
                        return true;
                    } else {
                        return false;
                    }

                }

                Log.d( TAG, "filter : exit, fallback" );
                return true;

        }

    }

}

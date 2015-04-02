package org.mythtv.android.player.tv.videos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.app.settings.SettingsActivity;

public class VideosActivity extends Activity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        if( MainApplication.getInstance().isConnected() ) {
            Log.d(TAG, "onCreate : backend already connected");

            MainApplication.getInstance().resetBackend();

        } else {
            Log.d( TAG, "onCreate : backend NOT connected" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String backendUrlPref = sharedPref.getString( MainApplication.KEY_PREF_BACKEND_URL, "" );

            if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_backend_url ).equals( backendUrlPref ) ) {
                Log.d( TAG, "onCreate : backend not set, show settings" );

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );
            } else {

                MainApplication.getInstance().resetBackend();

            }

        }

        setContentView( R.layout.activity_tv_videos );

    }

}

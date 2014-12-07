package org.mythtv.android.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.ui.settings.SettingsActivity;

/**
 * Created by dmfrey on 12/6/14.
 */
public class FrontDoorActivity extends Activity {

    private static final String TAG = FrontDoorActivity.class.getSimpleName();

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

        Log.v( TAG, "onCreate : enter" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i( TAG, "onResume : enter" );

        if( ( (MainApplication) getApplicationContext() ).isConnected() ) {
            Log.d( TAG, "onCreate : backend already connected" );

            Intent main = new Intent( this, MainActivity.class );
            startActivity(main);

        } else {
            Log.d( TAG, "onCreate : backend NOT connected" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( this );
            String backendUrlPref = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

            if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_backend_url ).equals( backendUrlPref ) ) {
                Log.d( TAG, "onCreate : backend not set, show settings" );

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );

            } else {
                Log.d( TAG, "onCreate : resetting backend connection" );

                ( (MainApplication) getApplicationContext() ).resetBackend();

            }

        }

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        Log.i( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i( TAG, "onPause : enter" );

        if( null != mBackendConnectedBroadcastReceiver ) {
            unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

        Log.i( TAG, "onPause : exit" );
    }

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        private final String TAG = BackendConnectedBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive( Context context, Intent intent ) {
            Log.d( TAG, "onReceive : enter" );

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {
                Log.v(TAG, "onReceive : backend is connected");

                Intent main = new Intent( FrontDoorActivity.this, MainActivity.class );
                startActivity(main);

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
                Log.v( TAG, "onReceive : backend is NOT connected" );

                Toast.makeText( FrontDoorActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

            Log.d( TAG, "onReceive : exit" );
        }

    }


}
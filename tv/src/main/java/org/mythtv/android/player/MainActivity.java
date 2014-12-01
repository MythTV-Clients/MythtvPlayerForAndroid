package org.mythtv.android.player;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.R;
import org.mythtv.android.player.recordings.RecordingsActivity;
import org.mythtv.android.player.videos.VideosActivity;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( ( (MainApplication) getApplicationContext() ).isConnected() ) {
            Log.d(TAG, "onCreate : backend already connected");

        } else {
            Log.d( TAG, "onCreate : backend NOT connected" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String backendUrlPref = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

            if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_backend_url ).equals( backendUrlPref ) ) {
                Log.d( TAG, "onCreate : backend not set, show settings" );

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );
            } else {

                ( (MainApplication) getApplicationContext() ).resetBackend();

            }

        }

        setContentView( R.layout.activity_main );

        String[] values = new String[] { "Recordings", "Videos", "Photos", "Music", "Settings" };

        final ArrayList<String> list = new ArrayList<String>();
        for( int i = 0; i < values.length; ++i ) {
            list.add( values[ i ] );
        }
        final ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, list );

        GridView gridview = (GridView) findViewById( R.id.gridview );
        gridview.setAdapter( adapter );

        gridview.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            public void onItemClick( AdapterView<?> parent, View v, int position, long id ) {

                switch( position ) {

                    case 0:

                        Intent recordings = new Intent( MainActivity.this, RecordingsActivity.class );
                        startActivity( recordings );

                        break;

                    case 1 :

                        Intent videos = new Intent( MainActivity.this, VideosActivity.class );
                        startActivity( videos );

                        break;

                    case 2 :
                        break;

                    case 3 :
                        break;

                    case 4 :

                        Intent prefs = new Intent( MainActivity.this, SettingsActivity.class );
                        startActivity( prefs );

                        break;

                }

            }

        });

    }

}

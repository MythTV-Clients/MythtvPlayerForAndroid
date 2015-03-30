package org.mythtv.android.player.tv;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.tv.recordings.RecordingsActivity;
import org.mythtv.android.player.tv.videos.VideosActivity;

import java.util.ArrayList;

public class TvActivity extends Activity {

    private static final String TAG = TvActivity.class.getSimpleName();

    private static final String SELECTED_ITEM_STATE = "selected_item";
    private static final String SELECTED_TITLE_STATE = "selected_title";
    private static final String SELECTED_TITLE_INFO_STATE = "selected_title_info";
    private static final String SELECTED_PROGRAM_STATE = "selected_program";

    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";

    private CharSequence mTitle;
    private TitleInfo mTitleInfo;
    private Program mProgram;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_tv);

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

                        Intent recordings = new Intent( TvActivity.this, RecordingsActivity.class );
                        startActivity( recordings );

                        break;

                    case 1 :

                        Intent videos = new Intent( TvActivity.this, VideosActivity.class );
                        startActivity( videos );

                        break;

                    case 2 :
                        break;

                    case 3 :
                        break;

                    case 4 :

                        Intent prefs = new Intent( TvActivity.this, SettingsActivity.class );
                        startActivity( prefs );

                        break;

                }

            }

        });

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( MainApplication.getInstance().isConnected() ) {
            Log.d( TAG, "onResume : backend already connected" );

//            MainApplication.getInstance().scheduleAlarms();

        } else {
            Log.d( TAG, "onResume : backend NOT connected" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( this );
            String backendUrlPref = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );

            if( "".equals( backendUrlPref ) || getResources().getString( R.string.pref_backend_url ).equals( backendUrlPref ) ) {
                Log.d( TAG, "onResume : backend not set, show settings" );

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );

            } else {
                Log.d( TAG, "onResume : resetting backend connection" );

                MainApplication.getInstance().resetBackend();

            }

        }

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    protected void onPause() {
        super.onPause();

        if( null != mBackendConnectedBroadcastReceiver ) {
            unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

//        MainApplication.getInstance().cancelAlarms();

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( SELECTED_TITLE_STATE ) ) {
            Log.d( TAG, "onRestoreInstanceState : mTitle retrieved from savedInstanceState" );

            mTitle = savedInstanceState.getString( SELECTED_TITLE_STATE );

            setTitle( mTitle );
        }

        if( savedInstanceState.containsKey( SELECTED_TITLE_INFO_STATE ) ) {
            Log.d( TAG, "onRestoreInstanceState : mTitleInfo retrieved from savedInstanceState" );

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( SELECTED_TITLE_INFO_STATE );
        }

        if( savedInstanceState.containsKey( SELECTED_PROGRAM_STATE ) ) {
            Log.d( TAG, "onRestoreInstanceState : mProgram retrieved from savedInstanceState" );

            mProgram = (Program) savedInstanceState.getSerializable( SELECTED_PROGRAM_STATE );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d(TAG, "onSaveInstanceState : enter");

        if( null != mTitle ) {
            outState.putString( SELECTED_TITLE_STATE, mTitle.toString() );
        }

        if( null != mTitleInfo ) {
            outState.putSerializable(SELECTED_TITLE_INFO_STATE, mTitleInfo);
        }

        if( null != mProgram ) {
            outState.putSerializable( SELECTED_PROGRAM_STATE, mProgram );
        }

        Log.d( TAG, "onSaveInstanceState : exit" );
        super.onSaveInstanceState( outState );
    }

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        private final String TAG = BackendConnectedBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive( Context context, Intent intent ) {
            Log.d( TAG, "onReceive : enter" );

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {
                Log.d(TAG, "onReceive : backend is connected");

//                MainApplication.getInstance().scheduleAlarms();

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
                Log.d( TAG, "onReceive : backend is NOT connected" );

                Toast.makeText( TvActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

            Log.d( TAG, "onReceive : exit" );
        }

    }

}
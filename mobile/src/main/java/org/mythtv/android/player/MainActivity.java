package org.mythtv.android.player;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.recordings.RecordingDetailsFragment;
import org.mythtv.android.player.recordings.RecordingsActivity;
import org.mythtv.android.player.recordings.RecordingsFragment;
import org.mythtv.android.player.recordings.TitleInfosFragment;
import org.mythtv.android.player.videos.VideosFragment;

public class MainActivity extends BaseActionBarActivity implements RecordingsFragment.OnRecordingClickedListener, NavigationDrawerFragment.ClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SELECTED_ITEM_STATE = "selected_item";
    private static final String SELECTED_TITLE_STATE = "selected_title";
    private static final String SELECTED_TITLE_INFO_STATE = "selected_title_info";
    private static final String SELECTED_PROGRAM_STATE = "selected_program";

    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";
    private static final String TITLE_INFOS_FRAGEMENT_TAG = TitleInfosFragment.class.getCanonicalName();
    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();
    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    private CharSequence mTitle;
    private int mSelectedItem;

    private TitleInfosFragment mTitleInfosFragment;
    private TitleInfo mTitleInfo;

    private RecordingsFragment mRecordingsFragment;
    private Program mProgram;

    private RecordingDetailsFragment mRecordingDetailsFragment;

    private VideosFragment mVideosFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate : enter");

        mTitle = getTitle();

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        if( savedInstanceState == null ) {
            Log.d( TAG, "onCreate : savedInstanceState == null" );
            mSelectedItem = 0;
        } else {

            if( savedInstanceState.containsKey( SELECTED_ITEM_STATE ) ) {
                Log.d( TAG, "onCreate : mSelectedItem retrieved from savedInstanceState" );

                mSelectedItem = savedInstanceState.getInt( SELECTED_ITEM_STATE );
            } else {
                Log.d( TAG, "onCreate : mSelectedItem set to default" );

                mSelectedItem = 0;
            }

        }

        Log.d(TAG, "onCreate : exit");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume : enter");

        getFragmentManager().addOnBackStackChangedListener( new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Log.v( TAG, "onBackStackChanged : enter" );

                Log.v( TAG, "onBackStackChanged : backstack count=" + getFragmentManager().getBackStackEntryCount() );
                if( getFragmentManager().getBackStackEntryCount() > 0 ) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled( true );
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled( false );
                }

                Fragment fragment = getFragmentManager().findFragmentByTag( CONTENT_FRAGMENT_TAG );
                if( null != fragment ) {
                    Log.v( TAG, "onBackStackChanged : fragment " + fragment.getClass().getName() );
                }

                if( fragment instanceof TitleInfosFragment ) {
                    Log.v( TAG, "onBackStackChanged : resetting for TitleInfosFragment" );

                    setTitle( getResources().getString( R.string.drawer_item_watch_recordings ) );
                }

                if( fragment instanceof VideosFragment ) {
                    Log.v( TAG, "onBackStackChanged : resetting for VideosFragment" );

                    setTitle( getResources().getString( R.string.drawer_item_watch_videos ) );
                }

                Log.v( TAG, "onBackStackChanged : exit" );
            }

        });

        if( MainApplication.getInstance().isConnected() ) {
            Log.d( TAG, "onResume : backend already connected" );

            selectItem( mSelectedItem );

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

        Log.i(TAG, "onResume : exit");
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState : enter");

        if( savedInstanceState.containsKey( SELECTED_ITEM_STATE ) ) {
            Log.d( TAG, "onRestoreInstanceState : mSelectedItem retrieved from savedInstanceState" );

            mSelectedItem = savedInstanceState.getInt( SELECTED_ITEM_STATE );
        } else {
            Log.d( TAG, "onRestoreInstanceState : mSelectedItem set to default" );

            mSelectedItem = 0;
        }

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

        Log.d(TAG, "onRestoreInstanceState : exit");
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        outState.putInt(SELECTED_ITEM_STATE, mSelectedItem);

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

    @Override
    public boolean onSupportNavigateUp() {
        Log.d( TAG, "onSupportNavigateUp : enter" );

        getSupportFragmentManager().popBackStack();

        Log.d(TAG, "onSupportNavigateUp : exit");
        return true;
    }

    @Override
    protected void updateData() {

    }

    private void selectItem( int position ) {
        Log.d( TAG, "selectItem : enter" );

        switch( position ) {

            case 0 :

                if( null == mTitleInfosFragment ) {
                    Log.d( TAG, "selectItem : creating new TitleInfosFragment") ;

                    mTitleInfosFragment = (TitleInfosFragment) Fragment.instantiate( this, TitleInfosFragment.class.getName() );

                }

                replaceFragment( mTitleInfosFragment, TITLE_INFOS_FRAGEMENT_TAG, false );

                break;

            case 1 :

                if( null == mVideosFragment ) {
                    Log.d( TAG, "selectItem : creating new VideosFragment" ) ;

                    mVideosFragment = (VideosFragment) Fragment.instantiate( this, VideosFragment.class.getName() );

                }

                replaceFragment( mVideosFragment, CONTENT_FRAGMENT_TAG, false );

                break;

            case 2 :

                mSelectedItem = 0;

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );

                break;

        }

        Log.d( TAG, "selectItem : exit" );
    }

    @Override
    public void setTitle( CharSequence title ) {

        mTitle = title;
        getSupportActionBar().setTitle( mTitle );

    }

    @Override
    public void onSetProgram( Program program ) {
        Log.d( TAG, "onSetProgram : enter" );

        mProgram = program;

        if( null != mRecordingDetailsFragment ) {
            Log.d(TAG, "onTitleInfoClicked : creating new RecordingDetailsFragment");

            removeFragment( mRecordingDetailsFragment );
        }

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        mRecordingDetailsFragment = (RecordingDetailsFragment) Fragment.instantiate( this, RecordingDetailsFragment.class.getName(), args );

        replaceFragment( mRecordingDetailsFragment, RECORDING_DETAILS_FRAGMENT_TAG, true );

        Log.d( TAG, "onSetProgram : exit" );
    }

    @Override
    public void onClick( View view, int position ) {
        Log.d( TAG, "onClick : enter" );

        selectItem( position );

        Log.d( TAG, "onClick : exit" );
    }

    @Override
    public void onLongClick( View view, int position ) {
        Log.d( TAG, "onLongClick : enter" );

        Log.d( TAG, "onLongClick : exit" );
    }

}

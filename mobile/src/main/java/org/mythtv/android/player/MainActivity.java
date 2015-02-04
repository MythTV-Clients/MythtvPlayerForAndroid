package org.mythtv.android.player;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

public class MainActivity extends BaseActionBarActivity implements NavAdapter.OnItemClickListener, TitleInfosFragment.OnTitleInfoClickListener, RecordingsFragment.OnRecordingClickedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SELECTED_ITEM_STATE = "selected_item";
    private static final String SELECTED_TITLE_STATE = "selected_title";
    private static final String SELECTED_TITLE_INFO_STATE = "selected_title_info";
    private static final String SELECTED_PROGRAM_STATE = "selected_program";

    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";
    private static final String TITLE_INFOS_FRAGEMENT_TAG = TitleInfosFragment.class.getCanonicalName();
    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();
    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    private MainApplication mMainApplication;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;
    private RecyclerView mDrawerList;
    private TextView mMythVersion;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavTitles;
    private int mSelectedItem;

    private TitleInfosFragment mTitleInfosFragment;
    private TitleInfo mTitleInfo;

    private RecordingsFragment mRecordingsFragment;
    private Program mProgram;

    private RecordingDetailsFragment mRecordingDetailsFragment;

    private VideosFragment mVideosFragment;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate : enter" );

        mMainApplication = (MainApplication) getApplicationContext();

        mTitle = mDrawerTitle = getTitle();
        mNavTitles = getResources().getStringArray( R.array.nav_array );
        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        mDrawer = (LinearLayout) findViewById( R.id.nav_drawer );
        mMythVersion = (TextView) findViewById( R.id.mythtv_version );
        mDrawerList = (RecyclerView) findViewById( R.id.nav_drawer_items );

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );

        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize( true );
        mDrawerList.setLayoutManager( new LinearLayoutManager( this ) );

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter( new NavAdapter( mNavTitles, this ) );

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed( View view ) {
                getSupportActionBar().setTitle( mTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened( View drawerView ) {
                getSupportActionBar().setTitle( mDrawerTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener( mDrawerToggle );

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

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i( TAG, "onResume : enter" );

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

                    setTitle( getResources().getStringArray( R.array.nav_array ) [ 0 ] );
                }

                if( fragment instanceof VideosFragment ) {
                    Log.v( TAG, "onBackStackChanged : resetting for VideosFragment" );

                    setTitle( getResources().getStringArray( R.array.nav_array ) [ 1 ] );
                }

                Log.v( TAG, "onBackStackChanged : exit" );
            }

        });

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( mMainApplication.isConnected() ) {
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

                mMainApplication.resetBackend();

            }

        }

        Log.i( TAG, "onResume : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

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

        Log.d( TAG, "onRestoreInstanceState : exit" );
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

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        outState.putInt( SELECTED_ITEM_STATE, mSelectedItem );

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

        Log.d( TAG, "onSupportNavigateUp : exit" );
        return true;
    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick( View view, int position ) {
        Log.d( TAG, "onClick : enter" );

        mSelectedItem = position;

        selectItem( position );

        Log.d( TAG, "onClick : exit" );
    }

    private void updateNavigationDrawerVersion() {

        if( null != mMainApplication.getMasterBackendHostName() ) {
            mMythVersion.setText( mMainApplication.getMasterBackendHostName() );
        }

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

        // update selected item title, then close the drawer
        setTitle( mNavTitles[ position ] );
        mDrawerLayout.closeDrawer( mDrawer );

        Log.d( TAG, "selectItem : exit" );
    }

    private void addFragment( Fragment fragment, String tag, boolean addToBackStack ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.content_frame, fragment, tag);

        if( addToBackStack ) {
            tx.addToBackStack( null );
        }

        tx.commit();

    }

    private void replaceFragment( Fragment fragment, String tag, boolean addToBackStack ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace( R.id.content_frame, fragment, tag );

        if( addToBackStack ) {
            tx.addToBackStack( null );
        }

        tx.commit();

    }

    private void removeFragment( Fragment fragment ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.remove( fragment );
        tx.commit();

    }

    @Override
    public void setTitle( CharSequence title ) {

        mTitle = title;
        getSupportActionBar().setTitle( mTitle );

    }

    /**
     * When using the ActionBarDrawerTogCONTENT_FRAGMENT_TAGgle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );

        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged( newConfig );

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Log.d( TAG, "onOptionsItemSelected : enter" );

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if( mDrawerToggle.onOptionsItemSelected( item ) ) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if( id == R.id.action_settings ) {
//            Log.d( TAG, "onOptionsItemSelected : settings selected" );
//
//            Intent prefs = new Intent( this, SettingsActivity.class );
//            startActivity( prefs );
//
//            return true;
//        }

        Log.d( TAG, "onOptionsItemSelected : exit" );
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onTitleInfoClicked( TitleInfo titleInfo ) {
        Log.d( TAG, "onTitleInfoClicked : enter" );

        mTitleInfo = titleInfo;

        if( null != mRecordingsFragment ) {
            Log.d(TAG, "onTitleInfoClicked : creating new RecordingsFragment");

            removeFragment( mRecordingsFragment );
        }

        Bundle args = new Bundle();
        if( !mTitleInfo.getTitle().equals( getResources().getString( R.string.all_recordings ) ) ) {
            args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitleInfo.getTitle() );
        }

        Intent recordings = new Intent( MainActivity.this, RecordingsActivity.class );
        recordings.putExtras( args );
        startActivity( recordings );

//        mRecordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName(), args );

//        replaceFragment( mRecordingsFragment, RECORDINGS_FRAGMENT_TAG, true );

        Log.d( TAG, "onTitleInfoClicked : exit" );
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

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        private final String TAG = BackendConnectedBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive( Context context, Intent intent ) {
            Log.d( TAG, "onReceive : enter" );

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {
                Log.d( TAG, "onReceive : backend is connected" );

                selectItem( mSelectedItem );

                updateNavigationDrawerVersion();

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
                Log.d( TAG, "onReceive : backend is NOT connected" );

                Bundle args = new Bundle();
                args.putBoolean( ConnectingFragment.CONNECTED_KEY, false );

                ConnectingFragment connectingFragment = (ConnectingFragment) getFragmentManager().findFragmentByTag( CONTENT_FRAGMENT_TAG );
                if( null == connectingFragment ) {
                    Log.d( TAG, "onResume : creating new ConnectingFragment" );

                    connectingFragment = (ConnectingFragment) Fragment.instantiate( MainActivity.this, ConnectingFragment.class.getName(), args );

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace( R.id.content_frame, connectingFragment, CONTENT_FRAGMENT_TAG );
                    transaction.commit();

                }

                Toast.makeText( MainActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

            Log.d( TAG, "onReceive : exit" );
        }

    }

}

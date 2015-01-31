package org.mythtv.android.player;

import android.app.Fragment;
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
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.recordings.TitleInfosFragment;
import org.mythtv.android.player.videos.VideosFragment;

public class MainActivity extends BaseActionBarActivity implements NavAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SELECTED_ITEM_STATE = "selected_item";

    private static final String CONNECTING_FRAGMENT_TAG = ConnectingFragment.class.getCanonicalName();
    private static final String TITLE_INFOS_FRAGMENT_TAG = TitleInfosFragment.class.getCanonicalName();
    private static final String VIDEOS_FRAGMENT_TAG = VideosFragment.class.getCanonicalName();

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavTitles;
    private int mSelectedItem;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate : enter" );

        ConnectingFragment connectingFragment = (ConnectingFragment) getFragmentManager().findFragmentByTag( CONNECTING_FRAGMENT_TAG );
        if( null == connectingFragment ) {
            Log.d( TAG, "onResume : creating new ConnectingFragment" );

            connectingFragment = (ConnectingFragment) Fragment.instantiate( this, ConnectingFragment.class.getName() );

        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace( R.id.content_frame, connectingFragment, CONNECTING_FRAGMENT_TAG );
        transaction.commit();

        mTitle = mDrawerTitle = getTitle();
        mNavTitles = getResources().getStringArray( R.array.nav_array );
        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        mDrawer = (LinearLayout) findViewById( R.id.nav_drawer );
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

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( ( (MainApplication) getApplicationContext() ).isConnected() ) {
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

                ( (MainApplication) getApplicationContext() ).resetBackend();

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

        Log.d( TAG, "onSaveInstanceState : exit" );
        super.onSaveInstanceState( outState );
    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick( View view, int position ) {
        Log.d( TAG, "onClick : enter" );

        mSelectedItem = position;

        selectItem( position );

        Log.d( TAG, "onClick : exit" );
    }

    private void selectItem( int position ) {
        Log.d( TAG, "selectItem : enter" );

        switch( position ) {

            case 0 :

                TitleInfosFragment titleInfosFragment = (TitleInfosFragment) getFragmentManager().findFragmentByTag( TITLE_INFOS_FRAGMENT_TAG );
                if( null == titleInfosFragment ) {
                    Log.d( TAG, "selectItem : creating new TitleInfosFragment") ;

                    titleInfosFragment = (TitleInfosFragment) Fragment.instantiate( this, TitleInfosFragment.class.getName() );

                }

                FragmentTransaction tx0 = getFragmentManager().beginTransaction();
                tx0.replace( R.id.content_frame, titleInfosFragment, TITLE_INFOS_FRAGMENT_TAG );
                tx0.addToBackStack( null );
                tx0.commit();

                break;

            case 1 :

                VideosFragment videosFragment = (VideosFragment) getFragmentManager().findFragmentByTag( VIDEOS_FRAGMENT_TAG );
                if( null == videosFragment ) {
                    Log.d( TAG, "selectItem : creating new VideosFragment") ;

                    videosFragment = (VideosFragment) Fragment.instantiate( this, VideosFragment.class.getName() );

                }

                FragmentTransaction tx1 = getFragmentManager().beginTransaction();
                tx1.replace( R.id.content_frame, videosFragment, VIDEOS_FRAGMENT_TAG );
                tx1.addToBackStack( null );
                tx1.commit();

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

    @Override
    public void setTitle( CharSequence title ) {

        mTitle = title;
        getSupportActionBar().setTitle( mTitle );

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
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

//    @Override
//    public boolean onCreateOptionsMenu( Menu menu ) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate( R.menu.main, menu );
//
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu( Menu menu ) {
//
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen( mDrawerList );
//        menu.findItem( R.id.action_example ).setVisible( !drawerOpen );
//
//        return super.onPrepareOptionsMenu( menu );
//    }

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

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        private final String TAG = BackendConnectedBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive( Context context, Intent intent ) {
            Log.d( TAG, "onReceive : enter" );

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {
                Log.d( TAG, "onReceive : backend is connected" );

                selectItem( mSelectedItem );

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
                Log.d( TAG, "onReceive : backend is NOT connected" );

                Bundle args = new Bundle();
                args.putBoolean( ConnectingFragment.CONNECTED_KEY, false );

                ConnectingFragment connectingFragment = (ConnectingFragment) getFragmentManager().findFragmentByTag( CONNECTING_FRAGMENT_TAG );
                if( null == connectingFragment ) {
                    Log.d( TAG, "onResume : creating new ConnectingFragment" );

                    connectingFragment = (ConnectingFragment) Fragment.instantiate( MainActivity.this, ConnectingFragment.class.getName(), args );

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace( R.id.content_frame, connectingFragment, CONNECTING_FRAGMENT_TAG );
                    transaction.commit();

                } else {

                    connectingFragment = (ConnectingFragment) Fragment.instantiate( MainActivity.this, ConnectingFragment.class.getName(), args );

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace( R.id.content_frame, connectingFragment, CONNECTING_FRAGMENT_TAG );
                    transaction.commit();

                }

                Toast.makeText( MainActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

            Log.d( TAG, "onReceive : exit" );
        }

    }

}

package org.mythtv.android.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.recordings.RecordingsFragment;

import java.util.List;

public class MainActivity extends Activity implements NavAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavTitles;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_main );

        mTitle = mDrawerTitle = getTitle();
        mNavTitles = getResources().getStringArray( R.array.nav_array );
        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        mDrawerList = (RecyclerView) findViewById( R.id.left_drawer );

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );

        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize( true );
        mDrawerList.setLayoutManager( new LinearLayoutManager( this ) );

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new NavAdapter( mNavTitles, this ) );
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled( true );
        getActionBar().setHomeButtonEnabled( true );

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
                getActionBar().setTitle( mTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened( View drawerView ) {
                getActionBar().setTitle( mDrawerTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener( mDrawerToggle );

        if( savedInstanceState == null ) {
            selectItem( 0 );
        }

        Log.v( TAG, "onCreate : exit" );
    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick( View view, int position ) {
        selectItem( position );
    }

    private void selectItem( int position ) {

        switch( position ) {

            case 0 :

                RecordingsFragment recordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
                if( null == recordingsFragment ) {
                    Log.d( TAG, "setPrograms : creating new RecordingsFragment" );

                    recordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName() );

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
                    transaction.commit();

                }

                break;

            case 1 :

                Intent prefs = new Intent( this, SettingsActivity.class );
                startActivity( prefs );

                break;

        }

        // update selected item title, then close the drawer
        setTitle( mNavTitles[ position ] );
        mDrawerLayout.closeDrawer( mDrawerList );
    }

    @Override
    public void setTitle( CharSequence title ) {

        mTitle = title;
        getActionBar().setTitle( mTitle );

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

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu( Menu menu ) {

        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen( mDrawerList );
        menu.findItem( R.id.action_example ).setVisible( !drawerOpen );

        return super.onPrepareOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Log.v( TAG, "onOptionsItemSelected : enter" );

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if( mDrawerToggle.onOptionsItemSelected( item ) ) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == R.id.action_settings ) {
            Log.v( TAG, "onOptionsItemSelected : settings selected" );

            Intent prefs = new Intent( this, SettingsActivity.class );
            startActivity( prefs );

            return true;
        }

        Log.v( TAG, "onOptionsItemSelected : exit" );
        return super.onOptionsItemSelected( item );
    }

}

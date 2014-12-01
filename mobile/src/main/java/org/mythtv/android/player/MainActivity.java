package org.mythtv.android.player;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import org.mythtv.android.player.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.recordings.RecordingListActivity;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

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
                Log.d(TAG, "onCreate : resetting backend connection" );

                ( (MainApplication) getApplicationContext() ).resetBackend();

            }

        }

        setContentView( R.layout.activity_main );

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.navigation_drawer );
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp( R.id.navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout ) );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public void onNavigationDrawerItemSelected( int position ) {
        Log.v( TAG, "onNavigationDrawerItemSelected : enter" );

        switch( position ) {

            case 0 :
                Intent recordings = new Intent( this, RecordingListActivity.class );
                startActivity( recordings );

            case 1 :
            case 2 :
                // update the main content by replacing fragments
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace( R.id.container, PlaceholderFragment.newInstance( position + 1 ) )
                        .commit();

        }

        Log.v( TAG, "onNavigationDrawerItemSelected : exit" );
    }

    public void onSectionAttached( int number ) {
        Log.v( TAG, "onSectionAttached : enter" );

        switch( number ) {
            case 1:
                mTitle = getString( R.string.title_section1 );
                break;
            case 2:
                mTitle = getString( R.string.title_section2 );
                break;
            case 3:
                mTitle = getString( R.string.title_section3 );
                break;
        }

        Log.v( TAG, "onSectionAttached : exit" );
    }

    public void restoreActionBar() {
        Log.v( TAG, "restoreActionBar : enter" );

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
        actionBar.setDisplayShowTitleEnabled( true );
        actionBar.setTitle( mTitle );

        Log.v( TAG, "restoreActionBar : exit" );
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        Log.v( TAG, "onCreateOptionsMenu : enter" );

        if( !mNavigationDrawerFragment.isDrawerOpen() ) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate( R.menu.main, menu );
            restoreActionBar();

            Log.v( TAG, "onCreateOptionsMenu : exit, nav drawer was closed" );
            return true;
        }

        Log.v( TAG, "onCreateOptionsMenu : exit" );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Log.v( TAG, "onOptionsItemSelected : enter" );

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance( int sectionNumber ) {

            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt( ARG_SECTION_NUMBER, sectionNumber );
            fragment.setArguments( args );

            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

            View rootView = inflater.inflate( R.layout.fragment_main, container, false );

            return rootView;
        }

        @Override
        public void onAttach( Activity activity ) {
            super.onAttach( activity );

            ((MainActivity) activity).onSectionAttached( getArguments().getInt( ARG_SECTION_NUMBER ) );

        }

    }

}

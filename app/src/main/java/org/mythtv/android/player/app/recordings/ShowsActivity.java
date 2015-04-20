package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

public class ShowsActivity extends AbstractBaseActionBarActivity {

    private static final String TAG = ShowsActivity.class.getSimpleName();

    private TitleInfosFragment mTitleInfosFragment;
    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_shows;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

//        if( Build.VERSION.SDK_INT >= 21 ) {
//
//            TransitionInflater inflater = TransitionInflater.from( this );
//            Transition transition = inflater.inflateTransition( R.transition.transition_title );
//            getWindow().setExitTransition( transition );
//
//            Slide slide = new Slide();
//            slide.setDuration( 5000 );
//            getWindow().setReenterTransition( slide );
//
//        }

        super.onCreate( savedInstanceState );

        PreferenceManager.setDefaultValues( this, R.xml.preferences, false );

        mTitleInfosFragment = (TitleInfosFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_shows );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout ), toolbar );

        setTitle( getResources().getString( R.string.drawer_item_watch_recordings ) );

    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerFragment.setSelectItem( 0 );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void updateData() {

        mTitleInfosFragment.reload();

    }

}

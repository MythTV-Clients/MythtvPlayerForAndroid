package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.data.TitleInfoDataConsumer;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

import java.util.List;

public class ShowsActivity extends AbstractBaseActionBarActivity implements TitleInfoDataConsumer {

    private static final String TAG = ShowsActivity.class.getSimpleName();

    private TitleInfosFragment mTitleInfosFragment;
    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_shows;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        mTitleInfosFragment = (TitleInfosFragment) getFragmentManager().findFragmentById( R.id.fragment_shows );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar );

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
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public void setTitleInfos( List<TitleInfo> titleInfos ) {

        mTitleInfosFragment.setTitleInfos( titleInfos );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void updateData() {

    }

}

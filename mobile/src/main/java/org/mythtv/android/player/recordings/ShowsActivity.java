package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.data.TitleInfoDataConsumer;
import org.mythtv.android.library.ui.data.TitleInfosDataFragment;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.player.NavigationDrawerFragment;

import java.util.List;

public class ShowsActivity extends BaseActionBarActivity implements TitleInfoDataConsumer {

    private static final String TITLE_INFOS_DATA_FRAGMENT_TAG = TitleInfosDataFragment.class.getCanonicalName();

    private TitleInfosFragment mTitleInfosFragment;
    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shows;
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
    public void setTitleInfos( List<TitleInfo> titleInfos ) {

        mTitleInfosFragment.setTitleInfos( titleInfos );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void updateData() {

        TitleInfosDataFragment titleInfosDataFragment = (TitleInfosDataFragment) getFragmentManager().findFragmentByTag( TITLE_INFOS_DATA_FRAGMENT_TAG );
        if( null == titleInfosDataFragment ) {

            titleInfosDataFragment = (TitleInfosDataFragment) Fragment.instantiate( this, TitleInfosDataFragment.class.getName() );
            titleInfosDataFragment.setRetainInstance(true);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( titleInfosDataFragment, TITLE_INFOS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

    }

}

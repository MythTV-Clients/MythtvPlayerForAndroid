package org.mythtv.android.player.app.videos;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

public class VideosActivity extends AbstractBaseActionBarActivity {

    private VideosFragment mVideosFragment;
    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mVideosFragment = (VideosFragment) getFragmentManager().findFragmentById( R.id.fragment_videos );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout), toolbar );

    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerFragment.setSelectItem( 1 );

    }

    @Override
    protected void updateData() {

        mVideosFragment.reload();

    }

}

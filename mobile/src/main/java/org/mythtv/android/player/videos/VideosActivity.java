package org.mythtv.android.player.videos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.ui.data.VideoDataConsumer;
import org.mythtv.android.library.ui.data.VideosDataFragment;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.player.NavigationDrawerFragment;

import java.util.List;

public class VideosActivity extends BaseActionBarActivity implements VideoDataConsumer {

    private static final String VIDEOS_DATA_FRAGMENT_TAG = VideosDataFragment.class.getCanonicalName();

    private VideosFragment mVideosFragment;
    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mVideosFragment = (VideosFragment) getFragmentManager().findFragmentById( R.id.fragment_videos );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout), toolbar );

    }

    @Override
    protected void updateData() {

        VideosDataFragment videosDataFragment = (VideosDataFragment) getFragmentManager().findFragmentByTag(VIDEOS_DATA_FRAGMENT_TAG);
        if( null == videosDataFragment ) {

            videosDataFragment = (VideosDataFragment) Fragment.instantiate( this, VideosDataFragment.class.getName() );
            videosDataFragment.setRetainInstance( true );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( videosDataFragment, VIDEOS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

    }

    @Override
    public void setVideos( List<Video> videos ) {

        mVideosFragment.setVideos( videos );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

}

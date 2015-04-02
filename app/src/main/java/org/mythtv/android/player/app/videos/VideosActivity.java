package org.mythtv.android.player.app.videos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.common.ui.data.VideoDataConsumer;
import org.mythtv.android.player.common.ui.data.VideosDataFragment;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

import java.util.List;

public class VideosActivity extends AbstractBaseActionBarActivity implements VideoDataConsumer {

    private static final String VIDEOS_DATA_FRAGMENT_TAG = VideosDataFragment.class.getCanonicalName();

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

        VideosDataFragment videosDataFragment = (VideosDataFragment) getFragmentManager().findFragmentByTag(VIDEOS_DATA_FRAGMENT_TAG);
        if( null == videosDataFragment ) {

            videosDataFragment = (VideosDataFragment) Fragment.instantiate( this, VideosDataFragment.class.getName() );
            videosDataFragment.setRetainInstance( true );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( videosDataFragment, VIDEOS_DATA_FRAGMENT_TAG );
            transaction.commit();

        } else {

            videosDataFragment.reset();

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

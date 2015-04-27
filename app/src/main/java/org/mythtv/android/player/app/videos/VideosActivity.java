package org.mythtv.android.player.app.videos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;
import org.mythtv.android.player.common.ui.views.SlidingTabLayout;

public class VideosActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    private NavigationDrawerFragment mDrawerFragment;

    private SlidingTabLayout mTabs;
    private ViewPager mPager;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout ), toolbar );

        mPager = (ViewPager) findViewById( R.id.pager );
        mPager.setAdapter( new VideosFragmentPagerAdapter( getSupportFragmentManager() ) );

        mTabs = (SlidingTabLayout) findViewById( R.id.tabs );
        mTabs.setViewPager( mPager );

    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerFragment.setSelectItem( 1 );

    }

    @Override
    protected void updateData() {

    }

    class VideosFragmentPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;
        
        public VideosFragmentPagerAdapter( FragmentManager fm ) {
            super( fm );

            tabs = getResources().getStringArray( R.array.watch_videos_tabs );
        }

        @Override
        public Fragment getItem( int position ) {
            Log.v( TAG, "getItem : position=" + position );

            switch ( position ) {

                case 0:

                    return Fragment.instantiate( VideosActivity.this, MoviesFragment.class.getName(), null );

                case 1 :

                    return Fragment.instantiate(VideosActivity.this, TelevisionFragment.class.getName(), null);

            }

            throw new IllegalArgumentException( "position " + position + " not implemented" );
        }

        @Override
        public CharSequence getPageTitle( int position ) {

            return tabs[ position ];
        }

        @Override
        public int getCount() {

            return 2;
        }

    }

}

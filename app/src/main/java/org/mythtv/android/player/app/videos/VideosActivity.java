/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.player.app.videos;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

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

        mDrawerFragment.setSelectItem(1);

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu( menu );
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

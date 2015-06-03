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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mPager;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        mPager = (ViewPager) findViewById( R.id.pager );
        mTabLayout = (TabLayout) findViewById( R.id.tabs );
        mTabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPager.setAdapter( new VideosFragmentPagerAdapter( getSupportFragmentManager() ) );
        mTabLayout.setupWithViewPager( mPager );
        mPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTabLayout ) );

        super.setNavigationMenuItemChecked( 1 );

    }

    @Override
    public void onBackPressed() {

        if( mPager.getCurrentItem() == 0 ) {

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {

            // Otherwise, select the previous step.
            mPager.setCurrentItem( mPager.getCurrentItem() - 1 );

        }

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

    class VideosFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;
        List<Fragment> fragments = new ArrayList<>();

        boolean showAdultTab = false;

        public VideosFragmentPagerAdapter( FragmentManager fm ) {
            super( fm );

            showAdultTab = MainApplication.getInstance().showAdultTab();

            tabs = getResources().getStringArray( R.array.watch_videos_tabs );
            fragments.add( Fragment.instantiate( VideosActivity.this, MoviesFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideosActivity.this, TelevisionFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideosActivity.this, HomeMoviesFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideosActivity.this, MusicVideosFragment.class.getName(), null ) );

            if( showAdultTab ) {

                fragments.add(Fragment.instantiate( VideosActivity.this, AdultFragment.class.getName(), null ) );

            }

            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem( int position ) {
            Log.v( TAG, "getItem : position=" + position );

            return fragments.get( position );
        }

        @Override
        public CharSequence getPageTitle( int position ) {

            return tabs[ position ];
        }

        @Override
        public int getCount() {

            if( showAdultTab ) {

                return 5;
            }

            return 4;
        }

    }

}

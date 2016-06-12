/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.view.fragment.phone.AbstractBaseVideoPagerFragment;
import org.mythtv.android.view.fragment.phone.AdultListFragment;
import org.mythtv.android.view.fragment.phone.MusicVideoListFragment;
import org.mythtv.android.view.fragment.phone.TelevisionListFragment;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.internal.di.HasComponent;
import org.mythtv.android.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.internal.di.components.VideoComponent;
import org.mythtv.android.model.VideoMetadataInfoModel;
import org.mythtv.android.view.fragment.phone.HomeVideoListFragment;
import org.mythtv.android.view.fragment.phone.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dmfrey on 11/13/15.
 */
public class VideoListActivity extends AbstractBasePhoneActivity implements HasComponent<VideoComponent>, AbstractBaseVideoPagerFragment.VideoListListener {

    private static final String TAG = VideoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, VideoListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private VideoComponent videoComponent;

    @Bind( R.id.tabs )
    TabLayout mTabLayout;

    @Bind( R.id.pager )
    ViewPager mPager;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_video_metadata_info_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeInjector();

        mTabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        mPager.setAdapter( new VideosFragmentPagerAdapter( getSupportFragmentManager() ) );
        mPager.setOffscreenPageLimit( 1 );
        mTabLayout.setupWithViewPager( mPager );
        mPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTabLayout ) );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 2 );

    }

    @Override
    public void onBackPressed() {
        Log.d( TAG, "onBackPressed : enter" );

        if( mPager.getCurrentItem() == 0 ) {
            Log.d( TAG, "onBackPressed : navigating home" );

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

            navigator.navigateToHome( this );

        } else {
            Log.d( TAG, "onBackPressed : navigating to previous tab" );

            // Otherwise, select the previous step.
            mPager.setCurrentItem( mPager.getCurrentItem() - 1 );

        }

        Log.d( TAG, "onBackPressed : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public VideoComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return videoComponent;
    }

    class VideosFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;
        List<Fragment> fragments = new ArrayList<>();

        boolean showAdultTab = getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_SHOW_ADULT_TAB, false );

        public VideosFragmentPagerAdapter( FragmentManager fm ) {
            super( fm );

            tabs = getResources().getStringArray( R.array.watch_videos_tabs );
            fragments.add( Fragment.instantiate( VideoListActivity.this, MovieListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideoListActivity.this, TelevisionListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideoListActivity.this, HomeVideoListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( VideoListActivity.this, MusicVideoListFragment.class.getName(), null ) );

            if( showAdultTab ) {

                fragments.add( Fragment.instantiate( VideoListActivity.this, AdultListFragment.class.getName(), null ) );

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

    @Override
    public void onVideoClicked( VideoMetadataInfoModel videoMetadataInfoModel, String contentType ) {
        Log.d( TAG, "onVideoClicked : enter" );

        Log.i( TAG, "onVideoClicked : videoMetadataInfoModel=" + videoMetadataInfoModel.toString() );
        if( ContentType.TELEVISION.equals( contentType ) ) {

            navigator.navigateToVideoSeries( this, videoMetadataInfoModel.getTitle() );

        } else {

            navigator.navigateToVideo( this, videoMetadataInfoModel.getId(), null, videoMetadataInfoModel.getFileName(), videoMetadataInfoModel.getHostName() );

        }

        Log.d( TAG, "onVideoClicked : exit" );
    }

}

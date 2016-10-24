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

package org.mythtv.android.presentation.view.activity.phone;

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
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemListFragment;
import org.mythtv.android.presentation.view.fragment.phone.SeriesListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dmfrey on 11/13/15.
 */
public class VideoListActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, MediaItemListFragment.MediaItemListListener, SeriesListFragment.SeriesListListener {

    private static final String TAG = VideoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, VideoListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private MediaComponent mediaComponent;

    @BindView( R.id.tabs )
    TabLayout mTabLayout;

    @BindView( R.id.pager )
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

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    class VideosFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;
        List<Fragment> fragments = new ArrayList<>();

        boolean showAdultTab = getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_SHOW_ADULT_TAB, false );

        public VideosFragmentPagerAdapter( FragmentManager fm ) {
            super( fm );

            MediaItemListFragment.Builder movieParameters = new MediaItemListFragment.Builder( Media.MOVIE );
            MediaItemListFragment movieFragment = MediaItemListFragment.newInstance( movieParameters.toBundle() );

            SeriesListFragment.Builder builder = new SeriesListFragment.Builder( Media.VIDEO );
            SeriesListFragment seriesListFragment = SeriesListFragment.newInstance( builder.toBundle() );

            MediaItemListFragment.Builder homeVideoParameters = new MediaItemListFragment.Builder( Media.HOMEVIDEO );
            MediaItemListFragment homeVideoFragment = MediaItemListFragment.newInstance( homeVideoParameters.toBundle() );

            MediaItemListFragment.Builder musicVideoParameters = new MediaItemListFragment.Builder( Media.MUSICVIDEO );
            MediaItemListFragment musicVideoFragment = MediaItemListFragment.newInstance( musicVideoParameters.toBundle() );

            tabs = getResources().getStringArray( R.array.watch_videos_tabs );
            fragments.add( movieFragment );
            fragments.add( seriesListFragment );
            fragments.add( homeVideoFragment );
            fragments.add( musicVideoFragment );

            if( showAdultTab ) {

                MediaItemListFragment.Builder adultParameters = new MediaItemListFragment.Builder( Media.ADULT );
                MediaItemListFragment adultFragment = MediaItemListFragment.newInstance( adultParameters.toBundle() );

                fragments.add( adultFragment );

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
    public void onMediaItemClicked( final MediaItemModel mediaItemModel ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        navigator.navigateToMediaItem( this, mediaItemModel.getId(), mediaItemModel.getMedia() );

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

    @Override
    public void onSeriesClicked( final SeriesModel seriesModel ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        navigator.navigateToSeries( this, Media.TELEVISION, false, -1, -1, seriesModel.getTitle(), null, null );

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

}

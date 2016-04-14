package org.mythtv.android.app.view.activity;

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
import org.mythtv.android.app.view.fragment.AbstractBaseVideoPagerFragment;
import org.mythtv.android.app.view.fragment.AdultListFragment;
import org.mythtv.android.app.view.fragment.MusicVideoListFragment;
import org.mythtv.android.app.view.fragment.TelevisionListFragment;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.app.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.app.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.app.view.fragment.HomeVideoListFragment;
import org.mythtv.android.app.view.fragment.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dmfrey on 11/13/15.
 */
public class VideoListActivity extends AbstractBaseActivity implements HasComponent<VideoComponent>, AbstractBaseVideoPagerFragment.VideoListListener {

    private static final String TAG = VideoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, VideoListActivity.class );
    }

    private VideoComponent videoComponent;

    @Bind( R.id.tabs )
    TabLayout mTabLayout;

    @Bind( R.id.pager )
    ViewPager mPager;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_video_metadata_info_list;
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

        if( mPager.getCurrentItem() == 0 ) {

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {

            // Otherwise, select the previous step.
            mPager.setCurrentItem( mPager.getCurrentItem() - 1 );

        }

    }

    private void initializeInjector() {
        Log.d(TAG, "initializeInjector : enter");

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
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

        boolean showAdultTab = getSharedPreferencesModule().getShowAdultContent();

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

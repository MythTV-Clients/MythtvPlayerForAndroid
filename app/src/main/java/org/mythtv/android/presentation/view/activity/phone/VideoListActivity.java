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

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.github.jorgecastilloprz.FABProgressCircle;

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
import org.mythtv.android.presentation.view.listeners.MediaItemListListener;
import org.mythtv.android.presentation.view.listeners.NotifyListener;

import butterknife.BindView;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/13/15.
 */
public class VideoListActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, View.OnClickListener, TabLayout.OnTabSelectedListener, MediaItemListListener, SeriesListFragment.SeriesListListener, NotifyListener {

    private static final String TAG = VideoListActivity.class.getSimpleName();

    public static Intent getCallingIntent(Context context ) {

        Intent callingIntent = new Intent( context, VideoListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private MediaItemListFragment movieFragment;
    private SeriesListFragment seriesListFragment;
    private MediaItemListFragment homeVideoFragment;
    private MediaItemListFragment musicVideoFragment;
    private MediaItemListFragment adultFragment;

    private MediaComponent mediaComponent;

    @BindView( R.id.tabs )
    TabLayout mTabLayout;

    @BindView( R.id.fabProgressCircle )
    FABProgressCircle fabProgressCircle;

    @BindView( R.id.fab )
    FloatingActionButton mFab;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_video_metadata_info_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        super.onCreate( savedInstanceState );

        this.initializeInjector();

        mTabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        mTabLayout.addOnTabSelectedListener( this );
        setupTabs();

        mFab.setOnClickListener( this );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 2 );

    }

    @Override
    public void onClick( View view ) {
        Log.v( TAG, "onClick : enter" );

        switch( mTabLayout.getSelectedTabPosition() ) {

            case 0 :

                movieFragment.reload();

                break;

            case 1 :

                seriesListFragment.reload();

                break;

            case 2 :

                homeVideoFragment.reload();

                break;

            case 3 :

                musicVideoFragment.reload();

                break;

            case 4 :

                adultFragment.reload();

                break;

        }

        Log.v( TAG, "onClick : exit" );
    }

    @Override
    public void onTabSelected( TabLayout.Tab tab ) {
        Log.v( TAG, "onTabSelected : enter" );

        setSelectedTab( tab.getPosition() );

        Log.v( TAG, "onTabSelected : exit" );
    }

    @Override
    public void onTabReselected( TabLayout.Tab tab ) {
        Log.v( TAG, "onTabSelected : enter" );

        Log.v( TAG, "onTabSelected : exit" );
    }

    @Override
    public void onTabUnselected( TabLayout.Tab tab ) {
        Log.v( TAG, "onTabUnselected : enter" );

        Log.v( TAG, "onTabUnselected : exit" );
    }

    private void setupTabs() {
        Log.v( TAG, "setupTabs : enter" );

        MediaItemListFragment.Builder movieParameters = new MediaItemListFragment.Builder( Media.MOVIE );
        movieFragment = MediaItemListFragment.newInstance( movieParameters.toBundle() );

        SeriesListFragment.Builder builder = new SeriesListFragment.Builder( Media.VIDEO );
        seriesListFragment = SeriesListFragment.newInstance( builder.toBundle() );

        MediaItemListFragment.Builder homeVideoParameters = new MediaItemListFragment.Builder( Media.HOMEVIDEO );
        homeVideoFragment = MediaItemListFragment.newInstance( homeVideoParameters.toBundle() );

        MediaItemListFragment.Builder musicVideoParameters = new MediaItemListFragment.Builder( Media.MUSICVIDEO );
        musicVideoFragment = MediaItemListFragment.newInstance( musicVideoParameters.toBundle() );

        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.watch_videos_tabs )[ 0 ] ), true );
        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.watch_videos_tabs )[ 1 ] ) );
        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.watch_videos_tabs )[ 2 ] ) );
        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.watch_videos_tabs )[ 3 ] ) );

        boolean showAdultTab = getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_SHOW_ADULT_TAB, false );
        Log.v( TAG, "setupTabs : showAdultTab=" + showAdultTab );
        if( showAdultTab ) {

            MediaItemListFragment.Builder adultParameters = new MediaItemListFragment.Builder( Media.ADULT );
            adultFragment = MediaItemListFragment.newInstance( adultParameters.toBundle() );

            mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.watch_videos_tabs )[ 4 ] ) );

        }

        Log.v( TAG, "setupTabs : exit" );
    }

    private void setSelectedTab( int position ) {
        Log.v( TAG, "setSelectedTab : enter" );

        switch( position ) {

            case 0 :
                Log.v( TAG, "onTabSelected : showing 'movieFragment'" );

                replaceFragment( R.id.frame_container, movieFragment );

                break;

            case 1 :
                Log.v( TAG, "onTabSelected : showing 'seriesListFragment'" );

                replaceFragment( R.id.frame_container, seriesListFragment );

                break;

            case 2 :
                Log.v( TAG, "onTabSelected : showing 'homeVideoFragment'" );

                replaceFragment( R.id.frame_container, homeVideoFragment );

                break;

            case 3 :
                Log.v( TAG, "onTabSelected : showing 'musicVideoFragment'" );

                replaceFragment( R.id.frame_container, musicVideoFragment );

                break;

            case 4 :
                Log.v( TAG, "onTabSelected : showing 'adultFragment'" );

                replaceFragment( R.id.frame_container, adultFragment );

                break;

        }

        Log.v( TAG, "setSelectedTab : exit" );
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

    @Override
    public void onMediaItemClicked( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( this, sharedElement, sharedElementName );
        navigator.navigateToMediaItem( this, mediaItemModel.getId(), mediaItemModel.getMedia(), options );

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

    @Override
    public void showLoading() {

        if( null != fabProgressCircle ){
            fabProgressCircle.measure(15, 15);
            fabProgressCircle.show();
        }

    }

    @Override
    public void finishLoading() {

        if( null != fabProgressCircle ) {
            fabProgressCircle.beginFinalAnimation();
        }

    }

    @Override
    public void hideLoading() {

        if( null != fabProgressCircle ) {
            fabProgressCircle.hide();
        }

    }

    @Override
    public void onSeriesClicked( final SeriesModel seriesModel ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        navigator.navigateToSeries( this, Media.TELEVISION, false, -1, -1, seriesModel.getTitle(), null, null, seriesModel.getInetref() );

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

}

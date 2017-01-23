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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.fragment.phone.EncoderListFragment;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemListFragment;

import butterknife.BindView;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
public class MainPhoneActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, View.OnClickListener, TabLayout.OnTabSelectedListener, MediaItemListFragment.MediaItemListListener {

    private static final String TAG = MainPhoneActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, MainPhoneActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }


    private MediaItemListFragment recentFragment;
    private EncoderListFragment encodersFragment;
    private MediaItemListFragment upcomingFragment;

    private MediaComponent mediaComponent;

    @BindView( R.id.tabs )
    TabLayout mTabLayout;

    @BindView( R.id.fab )
    FloatingActionButton mFab;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        Log.i( TAG, "Branch: " + getResources().getString( R.string.branchName ) + ", Tag: " + getResources().getString( R.string.tagName ) + ", Commit: " + getResources().getString( R.string.shaName ) );

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

        if( getMasterBackendUrl().equals( "http://" + getResources().getString( R.string.pref_backend_url ) + ":" + getResources().getString( R.string.pref_backend_port ) ) ) {
            Log.i( TAG, "onResume : MasterBackend not set, redirecting to Settings" );

            navigator.navigateToSettings( this );

        }

        setNavigationMenuItemChecked( 0 );

    }

    @Override
    public void onClick( View view ) {
        Log.v( TAG, "onClick : enter" );

        switch( mTabLayout.getSelectedTabPosition() ) {

            case 0 :

                recentFragment.reload();

                break;

            case 1 :

                encodersFragment.reload();

                break;

            case 2 :

                upcomingFragment.reload();

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

    }

    @Override
    public void onTabUnselected( TabLayout.Tab tab ) {

    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    private void setupTabs() {
        Log.v( TAG, "setupTabs : enter" );

        MediaItemListFragment.Builder recentParameters = new MediaItemListFragment.Builder( Media.RECENT );
        recentFragment = MediaItemListFragment.newInstance( recentParameters.toBundle() );

        encodersFragment = EncoderListFragment.newInstance();

        MediaItemListFragment.Builder upcomingParameters = new MediaItemListFragment.Builder( Media.UPCOMING );
        upcomingFragment = MediaItemListFragment.newInstance( upcomingParameters.toBundle() );

        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.main_tabs )[ 0 ] ), true );
        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.main_tabs )[ 1 ] ) );
        mTabLayout.addTab( mTabLayout.newTab().setText( getResources().getStringArray( R.array.main_tabs )[ 2 ] ) );

        Log.v( TAG, "setupTabs : exit" );
    }

    private void setSelectedTab( int position ) {
        Log.v( TAG, "setSelectedTab : enter" );

        switch( position ) {

            case 0 :
                Log.v( TAG, "onTabSelected : showing 'recentFragment'" );

                replaceFragment( R.id.frame_container, recentFragment );

                break;

            case 1 :
                Log.v( TAG, "onTabSelected : showing 'encodersFragment'" );

                replaceFragment( R.id.frame_container, encodersFragment );

                break;

            case 2 :
                Log.v( TAG, "onTabSelected : showing 'upcomingFragment'" );

                replaceFragment( R.id.frame_container, upcomingFragment );

                break;

        }

        Log.v( TAG, "setSelectedTab : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    @Override
    public void onMediaItemClicked( final MediaItemModel mediaItemModel ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        if( null != mediaItemModel && !mediaItemModel.getMedia().equals( Media.UPCOMING ) ) {

            navigator.navigateToMediaItem( this, mediaItemModel.getId(), mediaItemModel.getMedia() );

        }

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

}

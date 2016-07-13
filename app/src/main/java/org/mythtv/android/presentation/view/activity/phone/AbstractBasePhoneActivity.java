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

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
//import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
//import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer;
//import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
//import com.google.android.libraries.cast.companionlibrary.widgets.IntroductoryOverlay;

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.NetComponent;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.navigation.PhoneNavigator;
import org.mythtv.android.presentation.view.fragment.phone.AboutDialogFragment;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 *
 * Created by dmfrey on 8/30/15.
 */
public abstract class AbstractBasePhoneActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = AbstractBasePhoneActivity.class.getSimpleName();

//    protected VideoCastManager mCastManager;
//    protected VideoCastConsumer mCastConsumer;
//    protected MenuItem mMediaRouteMenuItem;
//    protected boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
//    protected IntroductoryOverlay mOverlay;
    protected LiveStreamInfoModel liveStreamInfoModel;

    protected CastContext mCastContext;
    protected MenuItem mediaRouteMenuItem;
    protected IntroductoryOverlay mIntroductoryOverlay;
    protected CastStateListener mCastStateListener;

    @Inject
    PhoneNavigator navigator;

    @Nullable @Bind( R.id.navigation_view ) protected NavigationView navigationView;
    @Nullable @Bind( R.id.toolbar ) protected Toolbar toolbar;

    @Nullable @Bind( R.id.drawer_layout ) protected DrawerLayout drawerLayout;

    public abstract int getLayoutResource();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

//        VideoCastManager.checkGooglePlayServices( this );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

        if( null != navigationView ) {

            navigationView.setNavigationItemSelectedListener( this );

        }

        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState != CastState.NO_DEVICES_AVAILABLE) {
                    showIntroductoryOverlay();
                }
            }
        };

        mCastContext = CastContext.getSharedInstance( this );

        //        mCastManager = VideoCastManager.getInstance();
//        mCastConsumer = new VideoCastConsumerImpl() {
//
//            @Override
//            public void onFailed( int resourceId, int statusCode ) {
//
//                String reason = "Not Available";
//                if( resourceId > 0 ) {
//
//                    reason = getString( resourceId );
//
//                }
//
//                Log.e( TAG, "Action failed, reason:  " + reason + ", status code: " + statusCode );
//            }
//
//            @Override
//            public void onApplicationConnected( ApplicationMetadata appMetadata, String sessionId, boolean wasLaunched ) {
//
//                invalidateOptionsMenu();
//
//            }
//
//            @Override
//            public void onDisconnected() {
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onConnectionSuspended( int cause ) {
//                Log.d( TAG, "onConnectionSuspended() was called with cause: " + cause );
//
////                com.google.sample.cast.refplayer.utils.Utils.
////                        showToast( VideoBrowserActivity.this, R.string.connection_temp_lost );
//
//            }
//
//            @Override
//            public void onConnectivityRecovered() {
////                com.google.sample.cast.refplayer.utils.Utils.
////                        showToast(VideoBrowserActivity.this, R.string.connection_recovered);
//            }
//
//            @Override
//            public void onCastAvailabilityChanged( boolean castPresent ) {
//
//                if( castPresent && mIsHoneyCombOrAbove ) {
//                    showOverlay();
//                }
//
//            }
//
//        };

        if( toolbar != null ) {
            setSupportActionBar( toolbar );

            final ActionBar actionBar = getSupportActionBar();
            if( null != actionBar ) {

                if( null != navigationView ) {

                    actionBar.setHomeAsUpIndicator( R.drawable.ic_menu_white_24dp );
                }

                actionBar.setDisplayHomeAsUpEnabled( true );
            }

        }

    }

    @Override
    protected void onResume() {

        mCastContext.addCastStateListener( mCastStateListener );

//        mCastManager = VideoCastManager.getInstance();
//        if( null != mCastManager ) {
//
//            mCastManager.addVideoCastConsumer( mCastConsumer );
//            mCastManager.incrementUiCounter();
//
//        }

        super.onResume();

    }

    @Override
    protected void onPause() {

        mCastContext.removeCastStateListener( mCastStateListener );

//        mCastManager.decrementUiCounter();
//        mCastManager.removeVideoCastConsumer(mCastConsumer);

        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        ComponentName cn = new ComponentName( this, SearchableActivity.class );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( cn ) );
        searchView.setIconifiedByDefault( false );

        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton( getApplicationContext(), menu, R.id.media_route_menu_item );
//        mMediaRouteMenuItem = mCastManager.addMediaRouterButton( menu, R.id.media_route_menu_item );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                if( null != navigationView ) {

                    drawerLayout.openDrawer( GravityCompat.START );

                    return true;
                }

        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem ) {
        Log.i(TAG, "onNavigationItemSelected : enter");

        menuItem.setChecked( true );
        drawerLayout.closeDrawers();

        switch( menuItem.getItemId() ) {

            case R.id.navigation_item_home :

                navigator.navigateToHome( this );

                return true;

            case R.id.navigation_item_watch_recordings :
                Log.i( TAG, "onNavigationItemSelected : watch recordings clicked" );

                navigator.navigateToTitleInfos( this );

                return true;

            case R.id.navigation_item_watch_videos :
                Log.i( TAG, "onNavigationItemSelected : watch videos clicked" );

                navigator.navigateToVideos( this );

//                String videoView  = MainApplication.getInstance().getVideoView();
//                if( "grid".equals( videoView ) ) {

//                    Intent videos = new Intent( this, VideosActivity.class );
//                    videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( videos );

//                } else {

//                    Intent videos = new Intent( this, VideoDirActivity.class );
//                    videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( videos );

//                }

                return true;

            case R.id.navigation_item_watch_settings :
                Log.i( TAG, "onNavigationItemSelected : settings clicked" );

                navigator.navigateToSettings( this );

                return true;

            case R.id.navigation_item_about :
                Log.i( TAG, "onNavigationItemSelected : about clicked" );

                FragmentManager fm = getSupportFragmentManager();
                AboutDialogFragment fragment = new AboutDialogFragment();
                fragment.show( fm, "About Dialog Fragment" );

                return true;
        }

        return false;
    }

//    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
//    private void showOverlay() {
//        if(mOverlay != null) {
//            mOverlay.remove();
//        }
//
//        new Handler().postDelayed( new Runnable() {
//
//            @Override
//            public void run() {
//
//                if( mMediaRouteMenuItem.isVisible() ) {
//
//                    mOverlay = new IntroductoryOverlay.Builder( AbstractBasePhoneActivity.this )
//                            .setMenuItem( mMediaRouteMenuItem )
//                            .setTitleText( R.string.intro_overlay_text )
//                            .setSingleTime()
//                            .setOnDismissed( new IntroductoryOverlay.OnOverlayDismissedListener() {
//
//                                @Override
//                                public void onOverlayDismissed() {
//                                    Log.d( TAG, "overlay is dismissed" );
//
//                                    mOverlay = null;
//
//                                }
//
//                            }).build();
//
//                    mOverlay.show();
//
//                }
//
//            }
//
//        }, 1000 );
//
//    }

//    @Override
//    public boolean dispatchKeyEvent( @NonNull KeyEvent event ) {
//
//        return mCastManager.onDispatchVolumeKeyEvent( event, AndroidApplication.VOLUME_INCREMENT ) || super.dispatchKeyEvent( event );
//    }

    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                            AbstractBasePhoneActivity.this, mediaRouteMenuItem)
                            .setTitleText("Introducing Cast")
                            .setSingleTime()
                            .setOnOverlayDismissedListener(
                                    new IntroductoryOverlay.OnOverlayDismissedListener() {
                                        @Override
                                        public void onOverlayDismissed() {
                                            mIntroductoryOverlay = null;
                                        }
                                    })
                            .build();
                    mIntroductoryOverlay.show();
                }
            });
        }
    }

    public void setNavigationMenuItemChecked( int index ) {

        navigationView.getMenu().getItem( index ).setChecked( true );

    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment( int containerViewId, Fragment fragment ) {

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( containerViewId, fragment );
        fragmentTransaction.commit();

    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.components.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {

        return ( (AndroidApplication) getApplication() ).getApplicationComponent();
    }

    /**
     * Get a SharedPreferences component for dependency injection.
     *
     * @return {@link SharedPreferencesComponent}
     */
    protected SharedPreferencesComponent getSharedPreferencesComponent() {

        return ( (AndroidApplication) getApplication() ).getSharedPreferencesComponent();
    }

    /**
     * Get a NetComponent component for dependency injection.
     *
     * @return {@link NetComponent}
     */
    protected NetComponent getNetComponent() {

        return ( (AndroidApplication) getApplication() ).getNetComponent();
    }

    protected String getMasterBackendUrl() {

        String host = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_URL, "" );
        String port = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );

        return "http://" + host + ":" + port;

    }

}

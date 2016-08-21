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

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.NetComponent;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.navigation.PhoneNavigator;
import org.mythtv.android.presentation.view.fragment.phone.AboutDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 *
 * Created by dmfrey on 8/30/15.
 */
public abstract class AbstractBasePhoneActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = AbstractBasePhoneActivity.class.getSimpleName();

    protected LiveStreamInfoModel liveStreamInfoModel;

    protected CastContext mCastContext;
    protected MenuItem mediaRouteMenuItem;
    protected IntroductoryOverlay mIntroductoryOverlay;
    protected CastStateListener mCastStateListener;

    @Inject
    PhoneNavigator navigator;

    @Nullable @BindView( R.id.navigation_view ) protected NavigationView navigationView;
    @Nullable @BindView( R.id.toolbar ) protected Toolbar toolbar;

    @Nullable @BindView( R.id.drawer_layout ) protected DrawerLayout drawerLayout;

    protected FirebaseAnalytics mFirebaseAnalytics;

    public abstract int getLayoutResource();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

        if( !FirebaseApp.getApps( this ).isEmpty() ) {

            mFirebaseAnalytics = FirebaseAnalytics.getInstance( this );

        }

        if( null != navigationView ) {

            navigationView.setNavigationItemSelectedListener( this );

        }

        mCastStateListener = newState -> {
            if( newState != CastState.NO_DEVICES_AVAILABLE ) {
                showIntroductoryOverlay();
            }
        };

        mCastContext = CastContext.getSharedInstance( this );

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

        super.onResume();

    }

    @Override
    protected void onPause() {

        mCastContext.removeCastStateListener( mCastStateListener );

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

    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(() -> {
                mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                        AbstractBasePhoneActivity.this, mediaRouteMenuItem)
                        .setTitleText("Introducing Cast")
                        .setSingleTime()
                        .setOnOverlayDismissedListener(
                                () -> mIntroductoryOverlay = null)
                        .build();
                mIntroductoryOverlay.show();
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

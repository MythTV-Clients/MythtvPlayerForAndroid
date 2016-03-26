package org.mythtv.android.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaControlIntent;
import android.support.v7.media.MediaItemStatus;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaSessionStatus;
import android.support.v7.media.RemotePlaybackClient;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;

import org.mythtv.android.R;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.presentation.navigation.AppNavigator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 *
 * Created by dmfrey on 8/30/15.
 */
public abstract class AppAbstractBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = AppAbstractBaseActivity.class.getSimpleName();

    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MediaRouter.RouteInfo mRouteInfo;
    private RemotePlaybackClient mRemotePlaybackClient;
    private CastDevice mSelectedDevice;
    private GoogleApiClient mApiClient;
    private RemoteMediaPlayer mRemoteMediaPlayer;
    private Cast.Listener mCastClientListener;
    private boolean mWaitingForReconnect = false;
    private boolean mApplicationStarted = false;
    private boolean mVideoIsLoaded;
    private boolean mIsPlaying;

    @Inject
    AppNavigator navigator;

    @Nullable @Bind( R.id.navigation_view ) protected NavigationView navigationView;
    @Nullable @Bind( R.id.toolbar ) protected Toolbar toolbar;

    @Nullable @Bind( R.id.drawer_layout ) protected DrawerLayout drawerLayout;

    public abstract int getLayoutResource();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

        if( null != navigationView ) {
            navigationView.setNavigationItemSelectedListener( this );
        }

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

        initMediaRouter();

    }

    // Add the callback on start to tell the media router what kinds of routes
    // your app works with so the framework can discover them.
    @Override
    public void onStart() {

        mMediaRouter.addCallback( mMediaRouteSelector, mMediaRouterCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY );
        super.onStart();

    }

    // Remove the selector on stop to tell the media router that it no longer
    // needs to discover routes for your app.
    @Override
    public void onStop() {

        mMediaRouter.removeCallback( mMediaRouterCallback );
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        MenuItem mediaRouteMenuItem = menu.findItem( R.id.media_route_menu_item );
        MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider( mediaRouteMenuItem );
        mediaRouteActionProvider.setRouteSelector( mMediaRouteSelector );

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
        }

        return false;
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
     * Get an Activity module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.ActivityModule}
     */
    protected ActivityModule getActivityModule() {

        return new ActivityModule( this );
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( this );
    }

    private void initMediaRouter() {

        // Configure Cast device discovery
        mMediaRouter = MediaRouter.getInstance( getApplicationContext() );
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory( CastMediaControlIntent.categoryForCast( getString( R.string.app_id ) ) )
                .build();

    }

    private final MediaRouter.Callback mMediaRouterCallback =
            new MediaRouter.Callback() {

                @Override
                public void onRouteSelected( MediaRouter router, MediaRouter.RouteInfo route ) {
                    Log.d( TAG, "onRouteSelected: route=" + route );

                    if( route.supportsControlCategory( MediaControlIntent.CATEGORY_REMOTE_PLAYBACK ) ) {

                        // remote playback device
                        updateRemotePlayer( route );

                    } else {

                        // secondary output device
//                        updatePresentation( route );

                    }

                }

                @Override
                public void onRouteUnselected( MediaRouter router, MediaRouter.RouteInfo route ) {
                    Log.d( TAG, "onRouteUnselected: route=" + route );

                    if( route.supportsControlCategory( MediaControlIntent.CATEGORY_REMOTE_PLAYBACK ) ) {

                        // remote playback device
                        updateRemotePlayer( route );

                    } else {

                        // secondary output device
//                        updatePresentation( route );

                    }

                }

                @Override
                public void onRoutePresentationDisplayChanged( MediaRouter router, MediaRouter.RouteInfo route ) {
                    Log.d( TAG, "onRoutePresentationDisplayChanged: route=" + route );

                    if( route.supportsControlCategory( MediaControlIntent.CATEGORY_REMOTE_PLAYBACK ) ) {

                        // remote playback device
                        updateRemotePlayer( route );

                    } else {

                        // secondary output device
//                        updatePresentation( route );

                    }

                }

            };

    private void updateRemotePlayer( MediaRouter.RouteInfo routeInfo ) {

        // Changed route: tear down previous client
        if( null != mRouteInfo && null != mRemotePlaybackClient ) {

            mRemotePlaybackClient.release();
            mRemotePlaybackClient = null;

        }

        // Save new route
        mRouteInfo = routeInfo;

        // Attach new playback client
        mRemotePlaybackClient = new RemotePlaybackClient( this, mRouteInfo );

        // Send file for playback
        mRemotePlaybackClient.play( Uri.parse( "http://archive.org/download/Sintel/sintel-2048-stereo_512kb.mp4" ),
                "video/mp4", null, 0, null, new RemotePlaybackClient.ItemActionCallback() {

                    @Override
                    public void onResult( Bundle data, String sessionId, MediaSessionStatus sessionStatus, String itemId, MediaItemStatus itemStatus ) {
                        Log.d( TAG, "play: succeeded for item " + itemId );
                    }

                    @Override
                    public void onError( String error, int code, Bundle data ) {
                        Log.e( TAG, "play: failed - error:"+ code +" - "+ error );
                    }

                });

    }

}


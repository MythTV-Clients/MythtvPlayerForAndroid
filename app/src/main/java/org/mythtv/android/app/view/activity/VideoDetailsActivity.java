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

package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;

import org.mythtv.android.app.R;
import org.mythtv.android.app.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.app.internal.di.components.VideoComponent;
import org.mythtv.android.app.utils.MediaInfoHelper;
import org.mythtv.android.app.view.fragment.VideoDetailsFragment;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 11/25/15.
 */
public class VideoDetailsActivity extends AbstractBaseActivity implements HasComponent<VideoComponent>, VideoDetailsFragment.VideoDetailsListener {

    private static final String TAG = VideoDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_VIDEO_ID = "org.mythtv.android.INTENT_PARAM_VIDEO_ID";
    private static final String INSTANCE_STATE_PARAM_VIDEO_ID = "org.mythtv.android.STATE_PARAM_VIDEO_ID";

    private int id;
    private VideoComponent videoComponent;

    private VideoMetadataInfoModel videoMetadataInfoModel;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    @Bind( R.id.fab )
    FloatingActionButton fab;

    public static Intent getCallingIntent( Context context, int id ) {

        Intent callingIntent = new Intent( context, VideoDetailsActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_VIDEO_ID, id );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_video_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );

        super.onCreate( savedInstanceState );

        ButterKnife.bind( this );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public boolean onNavigateUp() {
        Log.d( TAG, "onNavigateUp : enter" );

        onBackPressed();

        Log.d( TAG, "onNavigateUp : exit" );
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putInt( INSTANCE_STATE_PARAM_VIDEO_ID, this.id );

        }

        super.onSaveInstanceState( outState );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.d( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState( savedInstanceState );

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_VIDEO_ID );

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_details, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.menu_settings :

                navigator.navigateToVideoSettings( this );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState  ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {
                Log.d( TAG, "initializeActivity : extras != null" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_VIDEO_ID ) ) {

                    this.id = getIntent().getIntExtra( INTENT_EXTRA_PARAM_VIDEO_ID, -1 );

                }

            }

            addFragment( R.id.fl_fragment, VideoDetailsFragment.newInstance() );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_VIDEO_ID );

        }

        loadBackdrop();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .videoModule( new VideoModule( this.id ) )
                .liveStreamModule( new LiveStreamModule() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public VideoComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return videoComponent;
    }

    @Override
    public void onVideoLoaded( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "onVideoLoaded : enter" );

        this.videoMetadataInfoModel = videoMetadataInfoModel;

        Log.d( TAG, "onVideoLoaded : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + this.id + "&Type=banner";
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        getNetComponent().picasso()
                .load( previewUrl )
                .fit().centerCrop()
                .into(imageView);

        Log.d( TAG, "loadBackdrop : exit" );
    }

    @OnClick( R.id.fab )
    void onButtonFabPlay() {
        Log.d( TAG, "onButtonFabPlay : enter" );

        if( mCastManager.isConnected() || mCastManager.isConnecting() ) {
            Log.d( TAG, "onButtonFabPlay : connected or connecting to Google Cast" );

            MediaInfo item = MediaInfoHelper.videoModelToMediaInfo( getMasterBackendUrl(), videoMetadataInfoModel );
            if( null != item ) {
                Log.d( TAG, "onButtonFabPlay : item=" + item.getContentId() );

                mCastManager.startVideoCastControllerActivity( this, item, 0, true );

            }

        } else {
            Log.d( TAG, "onButtonFabPlay : not connected to Google Cast" );

            try {

                String videoUrl = MediaInfoHelper.buildUrl( getMasterBackendUrl(), "/Content/GetFile?FileName=" + videoMetadataInfoModel.getFileName() );
                if( null != this.videoMetadataInfoModel.getLiveStreamInfo() && this.videoMetadataInfoModel.getLiveStreamInfo().getPercentComplete() >= 2 ) {

                    videoUrl = MediaInfoHelper.buildUrl( getMasterBackendUrl(), videoMetadataInfoModel.getLiveStreamInfo().getRelativeUrl() );

                }
                Log.d( TAG, "onPlayVideo : videoUrl=" + videoUrl );

                if( getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, true ) ) {
                    Log.d( TAG, "onButtonFabPlay : sending steam to internal player" );

                    navigator.navigateToVideoPlayer( this, videoUrl );

                } else {
                    Log.d( TAG, "onButtonFabPlay : sending stream to external player" );

                    navigator.navigateToExternalPlayer( this, videoUrl );

                }

            } catch( UnsupportedEncodingException e ) {
                Log.e( TAG, "onButtonFabPlay : error", e );
            }

        }

        Log.d( TAG, "onButtonFabPlay : exit" );
    }

}

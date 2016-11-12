/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mythtv.android.presentation.view.activity.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.model.CommercialBreakModel;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Activity for the local media player.
 */
public class LocalPlayerActivity extends AppCompatActivity {

    private static final String TAG = "LocalPlayerActivity";

    @BindView( R.id.videoView1 )
    VideoView mVideoView;

    @BindView( R.id.textView1 )
    TextView mTitleView;

    @BindView( R.id.textView2 )
    TextView mDescriptionView;

    @BindView( R.id.startText )
    TextView mStartText;

    @BindView( R.id.endText )
    TextView mEndText;

    @BindView( R.id.seekBar1 )
    SeekBar mSeekbar;

    @BindView( R.id.imageView2 )
    ImageView mPlayPause;

    @BindView( R.id.progressBar1 )
    ProgressBar mLoading;

    @BindView( R.id.controllers )
    View mControllers;

    @BindView( R.id.container )
    View mContainer;

    @BindView( R.id.coverArtView )
    ImageView mCoverArt;

    @BindView( R.id.textView3 )
    TextView mAuthorView;

    @BindView( R.id.play_circle )
    ImageButton mPlayCircle;

    private Timer mSeekbarTimer;
    private Timer mControllersTimer;
    private Timer mBookmarkTimer;
    private PlaybackState mPlaybackState;
    private final Handler mHandler = new Handler();
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaItemModel mSelectedMedia;
    private boolean mControllersVisible;
    private int mDuration;
    private PlaybackLocation mLocation;
    private CastContext mCastContext;
    private CastSession mCastSession;
    private SessionManagerListener<CastSession> mSessionManagerListener;
    private MenuItem mediaRouteMenuItem;

    private OkHttpClient okHttpClient;

    /**
     * indicates whether we are doing a local or a remote playback
     */
    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    /**
     * List of various states that we can be in
     */
    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    public static Intent getCallingIntent( Context context, MediaItemModel mediaItemModel ) {

        Intent intent = new Intent( context, LocalPlayerActivity.class );
        intent.putExtra( "media", mediaItemModel.toBundle() );
        intent.putExtra( "shouldStart", false );

        return intent;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setContentView( R.layout.local_player_activity );

        ButterKnife.bind( this );

        okHttpClient = ( (AndroidApplication) getApplication() ).getNetComponent().okHttpClient();

        mAquery = new AQuery( this );

        loadViews();
        setupControlsCallbacks();
        setupCastListener();

        mCastContext = CastContext.getSharedInstance( this );
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich( this, savedInstanceState );
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        // see what we need to play and where
        Bundle bundle = getIntent().getExtras();
        if( null != bundle ) {
            Log.v( TAG, "onCreate : bundle != null" );

            mSelectedMedia = MediaItemModel.fromBundle( getIntent().getBundleExtra( "media" ) );

            setupActionBar();
            setBookmark();

            boolean shouldStartPlayback = bundle.getBoolean( "shouldStart" );

            mVideoView.setVideoURI( Uri.parse( getMasterBackendUrl() + mSelectedMedia.getUrl() ) );
            Log.d( TAG, "onCreate : Setting url of the VideoView to [" + ( getMasterBackendUrl() + mSelectedMedia.getUrl() ) + "]" );

            if( shouldStartPlayback ) {
                Log.d( TAG, "onCreate : auto start playback" );

                // this will be the case only if we are coming from the
                // CastControllerActivity by disconnecting from a device
                mPlaybackState = PlaybackState.PLAYING;
                updatePlaybackLocation( PlaybackLocation.LOCAL );
                updatePlayButton( mPlaybackState );

//                if( mSelectedMedia.getBookmark() > 0 ) {
//                    Log.d( TAG, "onCreate : setting bookmark, bookmark=" + mSelectedMedia.getBookmark() );
//
//                    long startPosition = mSelectedMedia.getBookmark();
//                    mVideoView.seekTo( (int) startPosition );
//                    mSeekbar.setProgress( (int) startPosition );
//
//                }

                mVideoView.start();
                startControllersTimer();

            } else {
                Log.v( TAG, "onCreate : don't auto start playback" );

                // we should load the video but pause it
                // and show the album art.
                if( null != mCastSession && mCastSession.isConnected() ) {
                    Log.v( TAG, "onCreate : playback is REMOTE" );

                    updatePlaybackLocation( PlaybackLocation.REMOTE );

                } else {
                    Log.v( TAG, "onCreate : playback is LOCAL" );

                    updatePlaybackLocation( PlaybackLocation.LOCAL );

//                    if( mSelectedMedia.getBookmark() > 0 ) {
//                        Log.d( TAG, "onCreate : setting bookmark, bookmark=" + mSelectedMedia.getBookmark() );
//
//                        long startPosition = mSelectedMedia.getBookmark();
//                        mVideoView.seekTo( (int) startPosition );
//
//                    }

                }

                mPlaybackState = PlaybackState.IDLE;
                updatePlayButton( mPlaybackState );

            }

        }

        if( null != mTitleView ) {

            updateMetadata( true );

        }

        Log.v( TAG, "onCreate : exit" );
    }

    private void setupCastListener() {
        Log.v( TAG, "setupCastListener : enter" );

        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded( CastSession session, int error ) {

                onApplicationDisconnected();

            }

            @Override
            public void onSessionResumed( CastSession session, boolean wasSuspended ) {

                onApplicationConnected( session );

            }

            @Override
            public void onSessionResumeFailed( CastSession session, int error ) {

                onApplicationDisconnected();

            }

            @Override
            public void onSessionStarted( CastSession session, String sessionId ) {

                onApplicationConnected( session );

            }

            @Override
            public void onSessionStartFailed( CastSession session, int error ) {

                onApplicationDisconnected();

            }

            @Override
            public void onSessionStarting( CastSession session ) {
            }

            @Override
            public void onSessionEnding( CastSession session ) {
            }

            @Override
            public void onSessionResuming( CastSession session, String sessionId ) {
            }

            @Override
            public void onSessionSuspended( CastSession session, int reason ) {
            }

            private void onApplicationConnected( CastSession castSession ) {

                mCastSession = castSession;

                if( null != mSelectedMedia ) {

                    if( mPlaybackState == PlaybackState.PLAYING ) {

                        mVideoView.pause();
                        loadRemoteMedia( mSeekbar.getProgress(), true );

                        return;

                    } else {

                        mPlaybackState = PlaybackState.IDLE;
                        updatePlaybackLocation( PlaybackLocation.REMOTE );

                    }

                }

                updatePlayButton( mPlaybackState );
                invalidateOptionsMenu();

            }

            private void onApplicationDisconnected() {

                updatePlaybackLocation( PlaybackLocation.LOCAL );
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;
                updatePlayButton( mPlaybackState );
                invalidateOptionsMenu();

            }

        };

        Log.v( TAG, "setupCastListener : exit" );
    }

    /**
     * Sets various metadata based on the playback location
     * {@code PlaybackLocation}
     *   - LOCAL : playing on device
     *   - REMOTE : playing on cast device
     *
     * @param location
     */
    private void updatePlaybackLocation( PlaybackLocation location ) {
        Log.v( TAG, "updatePlaybackLocation : enter" );

        mLocation = location;
        if( location == PlaybackLocation.LOCAL ) {

            if( mPlaybackState == PlaybackState.PLAYING || mPlaybackState == PlaybackState.BUFFERING ) {

                setCoverArtStatus( null );
                startControllersTimer();

            } else {

                stopControllersTimer();
                setCoverArtStatus( getMasterBackendUrl() + mSelectedMedia.getCoverartUrl() );

            }

        } else {

            stopControllersTimer();
            setCoverArtStatus( getMasterBackendUrl() + mSelectedMedia.getCoverartUrl() );
            updateControllersVisibility( false );

        }

        Log.v( TAG, "updatePlaybackLocation : exit" );
    }

    private void play( int position ) {
        Log.v( TAG, "play : enter" );

        startControllersTimer();

        switch( mLocation ) {

            case LOCAL :

                mVideoView.seekTo( position );
                mVideoView.start();

                break;

            case REMOTE:

                mPlaybackState = PlaybackState.BUFFERING;
                updatePlayButton( mPlaybackState );
                mCastSession.getRemoteMediaClient().seek( position );

                break;

            default :
                break;

        }

        restartTrickplayTimer();
        restartBookmarkTimer();;

        Log.v( TAG, "play : exit" );
    }

    private void togglePlayback() {
        Log.v( TAG, "togglePlayback : enter" );

        stopControllersTimer();

        switch( mPlaybackState ) {

            case PAUSED :
                Log.v( TAG, "togglePlayback : PAUSED" );

                switch( mLocation ) {

                    case LOCAL :

                        mVideoView.start();

                        Log.d( TAG, "Playing locally..." );
                        mPlaybackState = PlaybackState.PLAYING;

                        startControllersTimer();
                        restartTrickplayTimer();
                        restartBookmarkTimer();
                        updatePlaybackLocation( PlaybackLocation.LOCAL );

                        break;

                    case REMOTE :

                        finish();

                        break;

                    default :
                        break;

                }

                break;

            case PLAYING :
                Log.v( TAG, "togglePlayback : PLAYING" );

                mPlaybackState = PlaybackState.PAUSED;
                mVideoView.pause();

                break;

            case IDLE :
                Log.v( TAG, "togglePlayback : IDLE" );

                switch( mLocation ) {

                    case LOCAL:

                        mVideoView.setVideoURI( Uri.parse( getMasterBackendUrl() + mSelectedMedia.getUrl() ) );

//                        if( mSelectedMedia.getBookmark() > 0 ) {
//
//                            long seek = mSelectedMedia.getBookmark();
//                            mVideoView.seekTo( (int) seek );
//
//                        }

                        mVideoView.start();
                        mPlaybackState = PlaybackState.PLAYING;

                        restartTrickplayTimer();
                        restartBookmarkTimer();
                        updatePlaybackLocation( PlaybackLocation.LOCAL );

                        break;

                    case REMOTE :

                        if( null != mCastSession && mCastSession.isConnected() ) {

                            loadRemoteMedia( mSeekbar.getProgress(), true );

                        }

                        break;

                    default :
                        break;
                }

                break;

            default :
                break;

        }

        updatePlayButton( mPlaybackState );

        Log.v( TAG, "togglePlayback : exit" );
    }

    private void loadRemoteMedia( long position, boolean autoPlay ) {
        Log.v( TAG, "loadRemoteMedia : enter" );

        if( null == mCastSession ) {

            Log.v( TAG, "loadRemoteMedia : exit, mCastSession not connected!" );
            return;
        }

        RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if( null == remoteMediaClient ) {

            Log.v( TAG, "loadRemoteMedia : remoteMediaClient not connected!" );
            return;
        }

        remoteMediaClient.load( buildMediaInfo(), autoPlay, position );
        remoteMediaClient.addProgressListener( progressListener, 60000 );

//        if( mSelectedMedia.getBookmark() > 0 ) {
//
//            remoteMediaClient.seek( mSelectedMedia.getBookmark() );
//
//            long seekPosition = ( mSelectedMedia.getBookmark() / 1000 / 60 );
//            mSeekbar.setProgress( (int) seekPosition, true );
//
//        }

        Intent intent = new Intent( this, ExpandedControlsActivity.class );
        startActivity( intent );

        Log.v( TAG, "loadRemoteMedia : exit" );
    }

    private void setBookmark() {
        Log.v( TAG, "setBookmark : enter" );

        if( mSelectedMedia.getBookmark() > 0 ) {
            Log.d( TAG, "setBookmark : setting bookmark to " + mSelectedMedia.getBookmark() );

            mSeekbar.setProgress( (int) mSelectedMedia.getBookmark() );

        }

        Log.v( TAG, "setBookmark : exit" );
    }

    private void setCoverArtStatus( String url ) {
        Log.v( TAG, "setCoverArtStatus : enter" );

        if( null != url ) {

            mAquery.id( mCoverArt ).image( url );
            mCoverArt.setVisibility( View.VISIBLE );
            mVideoView.setVisibility( View.INVISIBLE );

        } else {

            mCoverArt.setVisibility( View.GONE );
            mVideoView.setVisibility( View.VISIBLE );

        }

        Log.v( TAG, "setCoverArtStatus : exit" );
    }

    private void stopTrickplayTimer() {
        Log.v( TAG, "stopTrickplayTimer : enter" );

        if( null != mSeekbarTimer ) {
            Log.d( TAG, "stopTrickplayTimer : cancelling mSeekbarTimer" );

            mSeekbarTimer.cancel();

        }

        Log.v( TAG, "stopTrickplayTimer : exit" );
    }

    private void stopBookmarkTimer() {
        Log.v( TAG, "stopBookmarkTimer : enter" );

        if( null != mBookmarkTimer ) {
            Log.d( TAG, "stopBookmarkTimer : cancelling mBookmarkTimer" );

            mBookmarkTimer.cancel();

        }

        Log.v( TAG, "stopBookmarkTimer : exit" );
    }

    private void restartTrickplayTimer() {
        Log.v( TAG, "restartTrickplayTimer : enter" );

        stopTrickplayTimer();

        mSeekbarTimer = new Timer();
        mSeekbarTimer.scheduleAtFixedRate( new UpdateSeekbarTask(), 100, 1000 );

        Log.v( TAG, "restartTrickplayTimer : exit" );
    }

    private void restartBookmarkTimer() {
        Log.v( TAG, "restartBookmarkTimer : enter" );

        stopBookmarkTimer();

        mBookmarkTimer = new Timer();
        mBookmarkTimer.scheduleAtFixedRate( new UpdateBookmarkTimerTask(), 1000, 60000 );

        Log.v( TAG, "restartBookmarkTimer : exit" );
    }

    private void stopControllersTimer() {
        Log.v( TAG, "stopControllersTimer : enter" );

        if( null != mControllersTimer ) {
            Log.d( TAG, "stopControllersTimer : cancelling mControllersTimer" );

            mControllersTimer.cancel();

        }

        Log.v( TAG, "stopControllersTimer : exit" );
    }

    private void startControllersTimer() {
        Log.v( TAG, "startControllersTimer : enter" );

        stopControllersTimer();

        if( mLocation == PlaybackLocation.REMOTE ) {
            Log.v( TAG, "startControllersTimer : exit, playback is REMOTE" );

            return;
        }

        mControllersTimer = new Timer();
        mControllersTimer.schedule( new HideControllersTask(), 5000 );

        Log.v( TAG, "startControllersTimer : exit" );
    }

    // should be called from the main thread
    private void updateControllersVisibility( boolean show ) {
        Log.v( TAG, "updateControllersVisibility : enter" );

        if( show ) {

            getSupportActionBar().show();
            mControllers.setVisibility( View.VISIBLE );

        } else {

            if( !Utils.isOrientationPortrait( this ) ) {

                getSupportActionBar().hide();

            }

            mControllers.setVisibility( View.INVISIBLE );

        }

        Log.v( TAG, "updateControllersVisibility : exit" );
    }

    @Override
    protected void onPause() {
        Log.v( TAG, "onPause : enter" );
        super.onPause();

        if( mLocation == PlaybackLocation.LOCAL ) {
            Log.v( TAG, "onPause : playback is LOCAL" );

            stopTrickplayTimer();
            mSeekbarTimer = null;

            stopControllersTimer();

            // since we are playing locally, we need to stop the playback of
            // video (if user is not watching, pause it!)
            mVideoView.pause();
            mPlaybackState = PlaybackState.PAUSED;
            updatePlayButton( PlaybackState.PAUSED );

        }

        stopBookmarkTimer();
        mBookmarkTimer = null;

        Log.d( TAG, "onPause : removing cast session manager listener" );
        mCastContext.getSessionManager().removeSessionManagerListener( mSessionManagerListener, CastSession.class );

        Log.v( TAG, "onPause : exit" );
    }

    @Override
    protected void onStop() {
        Log.v( TAG, "onStop : enter" );
        super.onStop();

        Log.v( TAG, "onStop : exit" );
    }

    @Override
    protected void onDestroy() {
        Log.v( TAG, "onDestroy : enter" );

        stopControllersTimer();
        stopTrickplayTimer();
        stopBookmarkTimer();

        super.onDestroy();

        Log.v( TAG, "onDestroy : exit" );
    }

    @Override
    protected void onStart() {
        Log.v( TAG, "onStart : enter" );
        super.onStart();

        Log.v( TAG, "onStart : exit" );
    }

    @Override
    protected void onResume() {
        Log.v( TAG, "onResume : enter" );

        mCastContext.getSessionManager().addSessionManagerListener( mSessionManagerListener, CastSession.class );

        if( null != mCastSession && mCastSession.isConnected() ) {

            updatePlaybackLocation( PlaybackLocation.REMOTE );

        } else {

            updatePlaybackLocation( PlaybackLocation.LOCAL );

        }

        super.onResume();

        Log.v( TAG, "onResume : exit" );
    }

    private class HideControllersTask extends TimerTask {

        @Override
        public void run() {
            Log.v( TAG, "HideControllersTask.run : enter" );

            mHandler.post( () -> {
                updateControllersVisibility( false );
                mControllersVisible = false;
            });

            Log.v( TAG, "HideControllersTask.run : exit" );
        }
    }

    private class UpdateSeekbarTask extends TimerTask {

        @Override
        public void run() {
            Log.v( TAG, "UpdateSeekbarTask.run : enter" );

            if( !mSelectedMedia.getMedia().equals( Media.PROGRAM ) ) {

                Log.v( TAG, "UpdateSeekbarTask.doInBackground : exit, not for videos" );
                return;
            }

            mHandler.post( () -> {

                if( mLocation == PlaybackLocation.LOCAL ) {
                    Log.d( TAG, "UpdateSeekbarTask.run : playback is LOCAL" );

                    int currentPos = mVideoView.getCurrentPosition();
                    boolean next = false;

                    for( CommercialBreakModel commercialBreakModel : mSelectedMedia.getBreaks() ) {

                        if( next ) {
                            Log.i( TAG, "UpdateSeekbarTask.run : move to start of next commercial break" );

                            currentPos = (int) commercialBreakModel.getStart();
                            mVideoView.seekTo( currentPos );

                        }

                        if( currentPos < commercialBreakModel.getEnd() ) {
                            Log.d( TAG, "UpdateSeekbarTask : next commercialBreak not reached, currentPos=" + currentPos + "duration=" + mDuration + ", commercialBreakModel" + commercialBreakModel.toString() );

                            break;

                        } else {
                            Log.d( TAG, "UpdateSeekbarTask : next commercialBreak, currentPos=" + currentPos + "duration=" + mDuration + ", commercialBreakModel" + commercialBreakModel.toString() );

                            next = true;

                        }

                    }

                    updateSeekbar( currentPos, mDuration );

                }

            });

            Log.v( TAG, "UpdateSeekbarTask.run : exit" );
        }

    }

    private class UpdateBookmarkTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.v( TAG, "UpdateBookmarkTimerTask.run : enter" );

            if( !mSelectedMedia.getMedia().equals( Media.PROGRAM ) ) {

                Log.v( TAG, "UpdateBookmarkTimerTask.doInBackground : exit, not for videos" );
                return;
            }

            mHandler.post( () -> {

                if( mLocation == PlaybackLocation.LOCAL ) {
                    Log.d( TAG, "UpdateBookmarkTimerTask.run : playback is LOCAL" );

                    long currentPos = mVideoView.getCurrentPosition();
                    new UpdateBookmarkAsyncTask().execute( currentPos );

                }

            });

            Log.v( TAG, "UpdateBookmarkTimerTask.run : exit" );
        }

    }

    private RemoteMediaClient.ProgressListener progressListener = ( position, duration ) -> new UpdateBookmarkAsyncTask().execute( position  );

    private class UpdateBookmarkAsyncTask extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground( Long... params ) {
            Log.v( TAG, "UpdateBookmarkAsyncTask.doInBackground : enter" );

            if( !mSelectedMedia.getMedia().equals( Media.PROGRAM ) ) {

                Log.v( TAG, "UpdateBookmarkAsyncTask.doInBackground : exit, not for videos" );
                return null;
            }

            long currentPos = params[ 0 ];
            Log.d( TAG, "UpdateBookmarkAsyncTask.doInBackground : url=" + ( getMasterBackendUrl() + mSelectedMedia.getUpdateSavedBookmarkUrl() ) );

            String id = mSelectedMedia.getMedia().equals( Media.PROGRAM ) ? "RecordedId" : "Id";

            Map<String, String> parameters = new HashMap<>();
            parameters.put( id, String.valueOf( mSelectedMedia.getId() ) );
            parameters.put( "OffsetType", "Duration" );
            parameters.put( "Offset", String.valueOf( currentPos ) );

            FormBody.Builder builder = new FormBody.Builder();
            for( String key : parameters.keySet() ) {
                Log.d( TAG, "UpdateBookmarkAsyncTask.doInBackground : key=" + key + ", value=" + parameters.get( key ) );
                builder.add( key, parameters.get( key ) );
            }

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mSelectedMedia.getUpdateSavedBookmarkUrl() )
                    .addHeader( "Accept", "application/json" )
                    .post( builder.build() )
                    .build();

            try {

                String result = okHttpClient.newCall( request ).execute().body().string();
                Log.d( TAG, "UpdateBookmarkAsyncTask.doInBackground : result=" + result );

            } catch( IOException e ) {

                Log.e( TAG, "UpdateBookmarkAsyncTask.doInBackground : error", e );

            }

            Log.v( TAG, "UpdateBookmarkAsyncTask.doInBackground : exit" );
            return null;
        }

    }

    private void setupControlsCallbacks() {
        Log.v( TAG, "setupControlsCallbacks : enter" );

        mVideoView.setOnErrorListener( ( mp, what, extra ) -> {
            Log.e( TAG, "OnErrorListener.onError(): VideoView encountered an " + "error, what: " + what + ", extra: " + extra );

            String msg;
            if( extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT ) {

                msg = getString(R.string.video_error_media_load_timeout);

            } else if( what == MediaPlayer.MEDIA_ERROR_SERVER_DIED ) {

                msg = getString(R.string.video_error_server_unaccessible);

            } else {

                msg = getString(R.string.video_error_unknown_error);

            }

            Utils.showErrorDialog( LocalPlayerActivity.this, msg );

            mVideoView.stopPlayback();
            mPlaybackState = PlaybackState.IDLE;
            updatePlayButton( mPlaybackState );

            return true;
        });

        mVideoView.setOnPreparedListener( mp -> {
            Log.d( TAG, "mVideoView.onPrepared : enter" );

            mDuration = mp.getDuration();
            mEndText.setText( Utils.formatMillis( mDuration ) );
            mSeekbar.setMax( mDuration );

            if( mSelectedMedia.getBookmark() > 0 ) {
                Log.d( TAG, "mVideoView.onPrepared : updating bookmark, bookmark=" + mSelectedMedia.getBookmark() + ", mDuration=" + mDuration );

                mVideoView.seekTo( (int) mSelectedMedia.getBookmark() );
                mSeekbar.setProgress( (int) mSelectedMedia.getBookmark() );

            }

            restartTrickplayTimer();
            restartBookmarkTimer();

            Log.d( TAG, "mVideoView.onPrepared : exit" );
        });

        mVideoView.setOnCompletionListener( mp -> {
            Log.d( TAG, "setOnCompletionListener()" );

            stopTrickplayTimer();
            mPlaybackState = PlaybackState.IDLE;

            updatePlayButton( mPlaybackState );

        });

        mVideoView.setOnTouchListener( ( v, event ) -> {

            if( !mControllersVisible ) {

                updateControllersVisibility( true );

            }

            startControllersTimer();

            return false;
        });

        mSeekbar.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {

                if( mPlaybackState == PlaybackState.PLAYING ) {

                    play( seekBar.getProgress() );

                } else if( mPlaybackState != PlaybackState.IDLE ) {

                    mVideoView.seekTo( seekBar.getProgress() );

                }

                startControllersTimer();

            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {

                stopTrickplayTimer();

                mVideoView.pause();

                stopControllersTimer();

            }

            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {

                mStartText.setText( Utils.formatMillis( progress ) );

            }

        });

        mPlayPause.setOnClickListener(v -> {

            if( mLocation == PlaybackLocation.LOCAL ) {

                togglePlayback();

            }

        });

        Log.v( TAG, "setupControlsCallbacks : exit" );
    }

    private void updateSeekbar( int position, int duration ) {
        Log.v( TAG, "updateSeekbar : enter" );

        mSeekbar.setProgress( position );
        mSeekbar.setMax( duration );
        mStartText.setText( Utils.formatMillis( position ) );
        mEndText.setText( Utils.formatMillis( duration ) );

        Log.v( TAG, "updateSeekbar : exit" );
    }

    private void updatePlayButton( PlaybackState state ) {
        Log.v( TAG, "updatePlayButton : enter, PlayBackState=" + state );

        boolean isConnected = ( null != mCastSession ) && ( mCastSession.isConnected() || mCastSession.isConnecting() );

        mControllers.setVisibility( isConnected ? View.GONE : View.VISIBLE );
        mPlayCircle.setVisibility( isConnected ? View.GONE : View.VISIBLE );

        switch( state ) {

            case PLAYING :

                mLoading.setVisibility( View.INVISIBLE );
                mPlayPause.setVisibility( View.VISIBLE );
                mPlayPause.setImageDrawable( getResources().getDrawable( R.drawable.ic_av_pause_dark ) );
                mPlayCircle.setVisibility( isConnected ? View.VISIBLE : View.GONE );

                break;

            case IDLE :

                mPlayCircle.setVisibility( View.VISIBLE );
                mControllers.setVisibility( View.GONE );
                mCoverArt.setVisibility( View.VISIBLE );
                mVideoView.setVisibility( View.INVISIBLE );

                break;

            case PAUSED :

                mLoading.setVisibility( View.INVISIBLE );
                mPlayPause.setVisibility( View.VISIBLE );
                mPlayPause.setImageDrawable( getResources().getDrawable( R.drawable.ic_av_play_dark ) );
                mPlayCircle.setVisibility( isConnected ? View.VISIBLE : View.GONE );

                break;

            case BUFFERING :

                mPlayPause.setVisibility( View.INVISIBLE );
                mLoading.setVisibility( View.VISIBLE );

                break;

            default :
                break;

        }

        Log.v( TAG, "updatePlayButton : exit" );
    }

    @SuppressLint( "NewApi" )
    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        Log.v( TAG, "onConfigurationChanged : enter" );
        super.onConfigurationChanged( newConfig );

        getSupportActionBar().show();
        if( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ) {

            getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );
            getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {

                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LOW_PROFILE );

            }

            updateMetadata( false );

            mContainer.setBackgroundColor( getResources().getColor( android.R.color.black ) );

        } else {

            getWindow().setFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );
            getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );

            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {

                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_VISIBLE );

            }

            updateMetadata( true );

            mContainer.setBackgroundColor( getResources().getColor( R.color.white ) );

        }

        Log.v( TAG, "onConfigurationChanged : exit" );
    }

    private void updateMetadata( boolean visible ) {
        Log.v( TAG, "updateMetadata : enter" );

        Point displaySize;
        if( !visible ) {

            mDescriptionView.setVisibility( View.GONE );
            mTitleView.setVisibility( View.GONE );
            mAuthorView.setVisibility( View.GONE );

            displaySize = Utils.getDisplaySize( this );
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams( displaySize.x, displaySize.y + getSupportActionBar().getHeight() );
            lp.addRule( RelativeLayout.CENTER_IN_PARENT );
            mVideoView.setLayoutParams( lp );
            mVideoView.invalidate();

        } else {

            mDescriptionView.setText( mSelectedMedia.getSubTitle() );
            mTitleView.setText( mSelectedMedia.getTitle() );
            mAuthorView.setText( mSelectedMedia.getStudio() );
            mDescriptionView.setVisibility( View.VISIBLE );
            mTitleView.setVisibility( View.VISIBLE );
            mAuthorView.setVisibility( View.VISIBLE );
            displaySize = Utils.getDisplaySize( this );
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams( displaySize.x, (int) ( displaySize.x * mAspectRatio ) );
            lp.addRule( RelativeLayout.BELOW, R.id.toolbar );
            mVideoView.setLayoutParams( lp );
            mVideoView.invalidate();

        }

        Log.v( TAG, "updateMetadata : exit" );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        Log.v( TAG, "onCreateOptionsMenu : enter" );
        super.onCreateOptionsMenu( menu );

        getMenuInflater().inflate( R.menu.main, menu );

        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton( getApplicationContext(), menu, R.id.media_route_menu_item );

        Log.v( TAG, "onCreateOptionsMenu : exit" );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Log.v( TAG, "onOptionsItemSelected : enter" );

        Intent intent;
//        if (item.getItemId() == R.id.action_settings) {
//            intent = new Intent(LocalPlayerActivity.this, CastPreference.class);
//            startActivity(intent);
        /*} else */ if( item.getItemId() == android.R.id.home ) {
            ActivityCompat.finishAfterTransition( this );
        }

        Log.v( TAG, "onOptionsItemSelected : exit" );
        return true;
    }

    private void setupActionBar() {
        Log.v( TAG, "setupActionBar : enter" );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        toolbar.setTitle( mSelectedMedia.getTitle() );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        Log.v( TAG, "setupActionBar : exit" );
    }

    private void loadViews() {
        Log.v( TAG, "loadViews : enter" );

        mDescriptionView.setMovementMethod( new ScrollingMovementMethod() );
        mStartText.setText( Utils.formatMillis( 0 ) );
        ViewCompat.setTransitionName( mCoverArt, getString(R.string.transition_image ) );

//        mVideoView = (VideoView) findViewById( R.id.videoView1 );
//        mTitleView = (TextView) findViewById( R.id.textView1 );
//        mDescriptionView = (TextView) findViewById( R.id.textView2 );
//        mDescriptionView.setMovementMethod( new ScrollingMovementMethod() );
//        mAuthorView = (TextView) findViewById( R.id.textView3 );
//        mStartText = (TextView) findViewById( R.id.startText );
//        mStartText.setText( Utils.formatMillis( 0 ) );
//        mEndText = (TextView) findViewById( R.id.endText );
//        mSeekbar = (SeekBar) findViewById( R.id.seekBar1 );
//        mPlayPause = (ImageView) findViewById( R.id.imageView2 );
//        mLoading = (ProgressBar) findViewById( R.id.progressBar1 );
//        mControllers = findViewById( R.id.controllers ) ;
//        mContainer = findViewById( R.id.container );
//        mCoverArt = (ImageView) findViewById( R.id.coverArtView );
//        ViewCompat.setTransitionName( mCoverArt, getString(R.string.transition_image) );
//        mPlayCircle = (ImageButton) findViewById( R.id.play_circle );
        mPlayCircle.setOnClickListener( v -> togglePlayback() );

        Log.v( TAG, "loadViews : exit" );
    }

    private MediaInfo buildMediaInfo() {
        Log.v( TAG, "buildMediaInfo : enter" );

        MediaMetadata movieMetadata = new MediaMetadata( MediaMetadata.MEDIA_TYPE_MOVIE );

        movieMetadata.putString( MediaMetadata.KEY_SUBTITLE, mSelectedMedia.getStudio() );
        movieMetadata.putString( MediaMetadata.KEY_TITLE, mSelectedMedia.getTitle() );
        movieMetadata.addImage( new WebImage( Uri.parse( getMasterBackendUrl() + mSelectedMedia.getCoverartUrl() ) ) );
        movieMetadata.addImage( new WebImage( Uri.parse( getMasterBackendUrl() + mSelectedMedia.getCoverartUrl() ) ) );

        Log.v( TAG, "buildMediaInfo : exit" );
        return new MediaInfo.Builder( getMasterBackendUrl() + mSelectedMedia.getUrl() )
                .setStreamType( MediaInfo.STREAM_TYPE_BUFFERED )
                .setContentType( mSelectedMedia.getContentType() )
                .setMetadata( movieMetadata )
                .setStreamDuration( mSelectedMedia.getDuration() * 1000 )
                .build();
    }

    /**
     * Get a SharedPreferences component for dependency injection.
     *
     * @return {@link SharedPreferencesComponent}
     */
    private SharedPreferencesComponent getSharedPreferencesComponent() {

        return ( (AndroidApplication) getApplication() ).getSharedPreferencesComponent();
    }

    private String getMasterBackendUrl() {

        String host = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_URL, "" );
        String port = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );

        return "http://" + host + ":" + port;

    }

}

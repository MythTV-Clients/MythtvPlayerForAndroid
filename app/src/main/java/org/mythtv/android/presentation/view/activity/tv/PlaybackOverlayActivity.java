/*
 * Copyright (c) 2014 The Android Open Source Project
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

package org.mythtv.android.presentation.view.activity.tv;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.mythtv.android.R;
import org.mythtv.android.presentation.model.VideoModel;
import org.mythtv.android.presentation.view.fragment.tv.PlaybackOverlayFragment;

/**
 *
 * PlaybackOverlayActivity for video playback that loads PlaybackOverlayFragment and handles
 * the MediaSession object used to maintain the state of the media playback.
 *
 * @author dmfrey
 */
public class PlaybackOverlayActivity extends AbstractBaseTvActivity implements PlaybackOverlayFragment.OnPlayPauseClickedListener {

    private static final String TAG = PlaybackOverlayActivity.class.getSimpleName();

    private VideoView mVideoView;
    private LeanbackPlaybackState mPlaybackState = LeanbackPlaybackState.IDLE;
    private MediaSession mSession;

    private PlaybackOverlayFragment playbackOverlayFragment;

    @Override
    public int getLayoutResource() {

        return R.layout.tv_playback_controls;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        loadViews();
        setupCallbacks();
        mSession = new MediaSession( this, getResources().getString( R.string.app_name ) );
        mSession.setCallback( new MediaSessionCallback() );
        mSession.setFlags( MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS );

        mSession.setActive( true );

        this.initializeActivity( savedInstanceState );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        mVideoView.suspend();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public boolean onKeyUp( int keyCode, KeyEvent event ) {
        Log.d( TAG, "onKeyUp : enter" );

//        PlaybackOverlayFragment playbackOverlayFragment = (PlaybackOverlayFragment) getFragmentManager().findFragmentById( R.id.playback_controls_fragment );
        switch( keyCode ) {

            case KeyEvent.KEYCODE_MEDIA_PLAY :

                playbackOverlayFragment.togglePlayback( false );

                return true;

            case KeyEvent.KEYCODE_MEDIA_PAUSE :

                playbackOverlayFragment.togglePlayback( false );

                return true;

            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE :

                if( mPlaybackState == LeanbackPlaybackState.PLAYING ) {

                    playbackOverlayFragment.togglePlayback( false );

                } else {

                    playbackOverlayFragment.togglePlayback( true );

                }

                return true;

            default:

                return super.onKeyUp( keyCode, event );

        }

    }

    /**
     * Implementation of OnPlayPauseClickedListener
     */
    public void onFragmentPlayPause( VideoModel videoModel, int position, Boolean playPause ) {
        Log.d( TAG, "onFragmentPlayPause : enter" );

        mVideoView.setVideoPath( videoModel.videoUrl );

        if( position == 0 || mPlaybackState == LeanbackPlaybackState.IDLE ) {

            setupCallbacks();
            mPlaybackState = LeanbackPlaybackState.IDLE;

        }

        if( playPause && mPlaybackState != LeanbackPlaybackState.PLAYING ) {

            mPlaybackState = LeanbackPlaybackState.PLAYING;
            if( position > 0 ) {

                mVideoView.seekTo( position );
                mVideoView.start();

            }

        } else {

            mPlaybackState = LeanbackPlaybackState.PAUSED;
            mVideoView.pause();

        }

        updatePlaybackState(position);
        updateMetadata( videoModel );

        Log.d( TAG, "onFragmentPlayPause : exit" );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        playbackOverlayFragment = PlaybackOverlayFragment.newInstance();
        addFragment( R.id.fl_fragment, playbackOverlayFragment );

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void updatePlaybackState( int position ) {
        Log.d( TAG, "updatePlaybackState : enter" );

        PlaybackState.Builder stateBuilder = new PlaybackState.Builder().setActions( getAvailableActions() );

        int state = PlaybackState.STATE_PLAYING;
        if( mPlaybackState == LeanbackPlaybackState.PAUSED ) {

            state = PlaybackState.STATE_PAUSED;

        }

        stateBuilder.setState( state, position, 1.0f );
        mSession.setPlaybackState( stateBuilder.build() );

        Log.d( TAG, "updatePlaybackState : exit" );
    }

    private long getAvailableActions() {
        Log.v( TAG, "getAvailableActions : enter" );

        long actions = PlaybackState.ACTION_PLAY |
                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID |
                PlaybackState.ACTION_PLAY_FROM_SEARCH;

        if( mPlaybackState == LeanbackPlaybackState.PLAYING ) {

            actions |= PlaybackState.ACTION_PAUSE;

        }

        Log.v( TAG, "getAvailableActions : exit" );
        return actions;
    }

    private void updateMetadata( final VideoModel videoModel ) {
        Log.d( TAG, "updateMetadata : enter" );

        final MediaMetadata.Builder metadataBuilder = new MediaMetadata.Builder();

        String title = videoModel.title.replace("_", " -");

        metadataBuilder.putString( MediaMetadata.METADATA_KEY_DISPLAY_TITLE, title );
        metadataBuilder.putString( MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE, videoModel.description );
        metadataBuilder.putString( MediaMetadata.METADATA_KEY_DISPLAY_ICON_URI, videoModel.cardImageUrl );

        // And at minimum the title and artist for legacy support
        metadataBuilder.putString( MediaMetadata.METADATA_KEY_TITLE, title );
        metadataBuilder.putString( MediaMetadata.METADATA_KEY_ARTIST, videoModel.studio );

        Glide.with( this )
                .load( Uri.parse( videoModel.cardImageUrl ) )
                .asBitmap()
                .into( new SimpleTarget<Bitmap>( 500, 500 ) {

                    @Override
                    public void onResourceReady( Bitmap bitmap, GlideAnimation anim ) {

                        metadataBuilder.putBitmap( MediaMetadata.METADATA_KEY_ART, bitmap );
                        mSession.setMetadata( metadataBuilder.build() );

                    }

                });

        Log.d( TAG, "updateMetadata : exit" );
    }

    private void loadViews() {
        Log.d( TAG, "loadViews : enter" );

        mVideoView = (VideoView) findViewById( R.id.videoView );
        mVideoView.setFocusable( false );
        mVideoView.setFocusableInTouchMode( false );

        Log.d( TAG, "loadViews : exit" );
    }

    private void setupCallbacks() {
        Log.d( TAG, "setupCallbacks : enter" );

        mVideoView.setOnErrorListener( new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError( MediaPlayer mp, int what, int extra ) {

                String msg = "";
                if( extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT ) {

                    msg = getString( R.string.video_error_media_load_timeout );

                } else if( what == MediaPlayer.MEDIA_ERROR_SERVER_DIED ) {

                    msg = getString( R.string.video_error_server_inaccessible );

                } else {

                    msg = getString( R.string.video_error_unknown_error );

                }
                Log.e( TAG, "setupCallbacks : error - " + msg );

                mVideoView.stopPlayback();
                mPlaybackState = LeanbackPlaybackState.IDLE;

                return false;
            }

        });

        mVideoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared( MediaPlayer mp ) {

                if( mPlaybackState == LeanbackPlaybackState.PLAYING ) {

                    mVideoView.start();

                }

            }

        });

        mVideoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion( MediaPlayer mp ) {

                mPlaybackState = LeanbackPlaybackState.IDLE;

            }

        });

        Log.d( TAG, "setupCallbacks : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        mSession.setActive( true );

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        if( mVideoView.isPlaying()) {

            if( !requestVisibleBehind( true ) ) {

                // Try to play behind launcher, but if it fails, stop playback.
                stopPlayback();

            }

        } else {

            requestVisibleBehind( false );

        }

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    protected void onStop() {
        Log.d( TAG, "onStop : enter" );
        super.onStop();

        mSession.release();

        Log.d( TAG, "onStop : exit" );
    }

    @Override
    public void onVisibleBehindCanceled() {
        Log.d( TAG, "onVisibleBehindCanceled : enter" );
        super.onVisibleBehindCanceled();

        Log.d( TAG, "onVisibleBehindCanceled : exit" );
    }

    private void stopPlayback() {
        Log.d( TAG, "stopPlayback : enter" );

        if( null != mVideoView ) {

            mVideoView.stopPlayback();

        }

        Log.d( TAG, "stopPlayback : exit" );
    }

    /*
     * List of various states that we can be in
     */
    public enum LeanbackPlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    private class MediaSessionCallback extends MediaSession.Callback {
    }

}

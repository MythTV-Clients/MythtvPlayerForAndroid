package org.mythtv.android.player;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.service.ContentService;
import org.mythtv.android.library.core.utils.Utils;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.player.recordings.RecordingDetailsActivity;
//import org.mythtv.android.player.videos.VideoDetailsActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends Activity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    public static final String FULL_URL_TAG = "full_url";

    private static final int HIDE_CONTROLLER_TIME = 5000;
    private static final int SEEKBAR_DELAY_TIME = 100;
    private static final int SEEKBAR_INTERVAL_TIME = 1000;
    private static final int MIN_SCRUB_TIME = 3000;
    private static final int SCRUB_SEGMENT_DIVISOR = 30;
    private static final double MEDIA_BAR_TOP_MARGIN = 0.8;
    private static final double MEDIA_BAR_RIGHT_MARGIN = 0.2;
    private static final double MEDIA_BAR_BOTTOM_MARGIN = 0.0;
    private static final double MEDIA_BAR_LEFT_MARGIN = 0.2;
    private static final double MEDIA_BAR_HEIGHT = 0.1;
    private static final double MEDIA_BAR_WIDTH = 0.9;

    private VideoView mVideoView;
    private TextView mStartText;
    private TextView mEndText;
    private SeekBar mSeekbar;
    private ImageView mPlayPause;
    private ProgressBar mLoading;
    private View mControllers;
    private View mContainer;
    private Timer mSeekbarTimer;
    private Timer mControllersTimer;
    private Timer mGetLiveStreamTimer;
    private PlaybackState mPlaybackState;
    private final Handler mHandler = new Handler();
    private Program mSelectedProgram;
    private Video mSelectedVideo;
    private boolean mShouldStartPlayback;
    private boolean mControlersVisible;
    private int mDuration;
    private DisplayMetrics mMetrics;

//    private ContentService mContentService;
//    private LiveStreamInfo mLiveStreamInfo;
    private String mFileUrl;

    /*
     * List of various states that we can be in
     */
    public static enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i( TAG, "onCreate : enter" );

        setContentView(R.layout.activity_player);

//        mContentService = ( (MainApplication) getApplicationContext() ).getContentService();

        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        loadViews();
        setupController();
        setupControlsCallbacks();

        mFileUrl = getIntent().getStringExtra( FULL_URL_TAG );
        startVideoPlayerHls();
        updateMetadata( true );

//        if( null != getIntent().getSerializableExtra( getResources().getString( R.string.recording ) ) ) {
//
//            mSelectedProgram = (Program) getIntent().getSerializableExtra( getResources().getString( R.string.recording ) );
//            new AddRecordingLiveStreamAsyncTask().execute();
//
//        }
//
//        if( null != getIntent().getSerializableExtra( getResources().getString( R.string.video ) ) ) {
//
//            mSelectedVideo = (Video) getIntent().getSerializableExtra( getResources().getString( R.string.video ) );
//
//            if( mSelectedVideo.getFileName().endsWith( ".mp4" ) ) {
//                startVideoPlayerSupported();
//                updateMetadata(true);
//            } else if( mSelectedVideo.getFileName().endsWith( ".3gp" ) ) {
//                startVideoPlayerSupported();
//                updateMetadata( true );
//            } else if( mSelectedVideo.getFileName().endsWith( ".webm" ) ) {
//                startVideoPlayerSupported();
//                updateMetadata( true );
//            } else if( mSelectedVideo.getFileName().endsWith( ".mkv" ) ) {
//                startVideoPlayerSupported();
//                updateMetadata( true );
//            } else {
//                new AddVideoLiveStreamAsyncTask().execute();
//            }
//
//        }

    }

//    private void startVideoPlayerSupported() {
//        Log.i( TAG, "startVideoPlayerSupported : enter" );
//
//        Bundle b = getIntent().getExtras();
//        if (null != b) {
//            mShouldStartPlayback = b.getBoolean(getResources().getString(R.string.should_start));
//            int startPosition = b.getInt(getResources().getString(R.string.start_position), 0);
//
//            String url = ( (MainApplication) getApplicationContext() ).getMasterBackendUrl() + "/Content/GetVideo?Id=" + mSelectedVideo.getId();
//            Log.i( TAG, "startVideoPlayerSupported : url=" + url );
//            mVideoView.setVideoURI( Uri.parse( url ) );
//
//            if (mShouldStartPlayback) {
//                mPlaybackState = PlaybackState.PLAYING;
//                updatePlayButton(mPlaybackState);
//                if (startPosition > 0) {
//                    mVideoView.seekTo(startPosition);
//                }
//                mVideoView.start();
//                mPlayPause.requestFocus();
//                startControllersTimer();
//            } else {
//                updatePlaybackLocation();
//                mPlaybackState = PlaybackState.PAUSED;
//                updatePlayButton(mPlaybackState);
//            }
//        }
//    }

    private void startVideoPlayerHls() {
        Log.i( TAG, "startVideoPlayerHls : enter" );

        Bundle b = getIntent().getExtras();
        if (null != b) {
            mShouldStartPlayback = b.getBoolean(getResources().getString(R.string.should_start));
            int startPosition = b.getInt(getResources().getString(R.string.start_position), 0);

//            String url = ( (MainApplication) getApplicationContext() ).getMasterBackendUrl() + mLiveStreamInfo.getRelativeURL().substring( 1 );
            Log.i( TAG, "startVideoPlayerHls : mFileUrl=" + mFileUrl );
            String url = MainApplication.getInstance().getMasterBackendUrl() + mFileUrl.substring( 1 );
            Log.i( TAG, "startVideoPlayerHls : url=" + url );
            mVideoView.setVideoURI( Uri.parse( url ) );

            if (mShouldStartPlayback) {
                mPlaybackState = PlaybackState.PLAYING;
                updatePlayButton(mPlaybackState);
                if (startPosition > 0) {
                    mVideoView.seekTo(startPosition);
                }
                mVideoView.start();
                mPlayPause.requestFocus();
                startControllersTimer();
            } else {
                updatePlaybackLocation();
                mPlaybackState = PlaybackState.PAUSED;
                updatePlayButton(mPlaybackState);
            }
        }
    }

    private void updatePlaybackLocation() {
        Log.i( TAG, "updatePlaybackLocation : enter" );

        if (mPlaybackState == PlaybackState.PLAYING ||
                mPlaybackState == PlaybackState.BUFFERING) {
            startControllersTimer();
        } else {
            stopControllersTimer();
        }
    }

    private void play(int position) {
        Log.i( TAG, "play : enter" );

        startControllersTimer();
        mVideoView.seekTo(position);
        mVideoView.start();
        restartSeekBarTimer();
    }

    private void stopSeekBarTimer() {
        Log.d(TAG, "Stopped TrickPlay Timer");

        if (null != mSeekbarTimer) {
            mSeekbarTimer.cancel();
        }
    }

    private void restartSeekBarTimer() {
        stopSeekBarTimer();
        mSeekbarTimer = new Timer();
        mSeekbarTimer.scheduleAtFixedRate(new UpdateSeekbarTask(), SEEKBAR_DELAY_TIME,
                SEEKBAR_INTERVAL_TIME);
    }

    private void stopControllersTimer() {
        if (null != mControllersTimer) {
            mControllersTimer.cancel();
        }
    }

    private void startControllersTimer() {
        if (null != mControllersTimer) {
            mControllersTimer.cancel();
        }
        mControllersTimer = new Timer();
        mControllersTimer.schedule(new HideControllersTask(), HIDE_CONTROLLER_TIME);
    }

//    private void stopGetLiveStreamTimer() {
//        if( null != mGetLiveStreamTimer ) {
//            mGetLiveStreamTimer.cancel();
//        }
//    }
//
//    private void startGetLiveStreamTimer() {
//        if( null != mGetLiveStreamTimer ) {
//            mGetLiveStreamTimer.cancel();
//        }
//        mGetLiveStreamTimer = new Timer();
//        mGetLiveStreamTimer.schedule( mGetLiveStreamTask, 10000, 5000 );
//    }

    private void updateControlersVisibility(boolean show) {
        if (show) {
            mControllers.setVisibility(View.VISIBLE);
        } else {
            mControllers.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() was called");
        if (null != mSeekbarTimer) {
            mSeekbarTimer.cancel();
            mSeekbarTimer = null;
        }
        if (null != mControllersTimer) {
            mControllersTimer.cancel();
        }
        if( null != mGetLiveStreamTimer ) {
            mGetLiveStreamTimer.cancel();
        }
        mVideoView.pause();
        mPlaybackState = PlaybackState.PAUSED;
        updatePlayButton(PlaybackState.PAUSED);

//        MainApplication.getInstance().cancelAlarms();

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() was called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() is called");

//        if( null != mLiveStreamInfo ) {
//            new RemoveRecordingLiveStreamAsyncTask().execute();
//        }

        stopControllersTimer();
        stopSeekBarTimer();

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart was called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() was called");
        super.onResume();

//        MainApplication.getInstance().scheduleAlarms();

    }

    private class HideControllersTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateControlersVisibility(false);
                    mControlersVisible = false;
                }
            });

        }
    }

    private class UpdateSeekbarTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    int currentPos = 0;
                    currentPos = mVideoView.getCurrentPosition();
                    updateSeekbar(currentPos, mDuration);
                }
            });
        }
    }

    private class BackToDetailTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post( new Runnable() {

                @Override
                public void run() {

                    if( null != mSelectedProgram ) {
                        Intent intent = new Intent( PlayerActivity.this, RecordingDetailsActivity.class );
                        intent.putExtra( getResources().getString( R.string.recording ), mSelectedProgram );
                        startActivity(intent);
                    }

//                    if( null != mSelectedVideo ) {
//                        Intent intent = new Intent( PlayerActivity.this, VideoDetailsActivity.class );
//                        intent.putExtra( getResources().getString( R.string.video ), mSelectedVideo );
//                        startActivity( intent );
//                    }

                }
            });

        }
    }

    private void setupController() {

        int w = (int) (mMetrics.widthPixels * MEDIA_BAR_WIDTH);
        int h = (int) (mMetrics.heightPixels * MEDIA_BAR_HEIGHT);
        int marginLeft = (int) (mMetrics.widthPixels * MEDIA_BAR_LEFT_MARGIN);
        int marginTop = (int) (mMetrics.heightPixels * MEDIA_BAR_TOP_MARGIN);
        int marginRight = (int) (mMetrics.widthPixels * MEDIA_BAR_RIGHT_MARGIN);
        int marginBottom = (int) (mMetrics.heightPixels * MEDIA_BAR_BOTTOM_MARGIN);
        LayoutParams lp = new LayoutParams(w, h);
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        mControllers.setLayoutParams(lp);
        mStartText.setText(getResources().getString(R.string.init_text));
        mEndText.setText(getResources().getString(R.string.init_text));
    }

    private void setupControlsCallbacks() {

        mVideoView.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String msg = "";
                if (extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                    msg = getString(R.string.video_error_media_load_timeout);
                } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    msg = getString(R.string.video_error_server_unaccessible);
                } else {
                    msg = getString(R.string.video_error_unknown_error);
                }
                mVideoView.stopPlayback();
                mPlaybackState = PlaybackState.IDLE;
                return false;
            }
        });

        mVideoView.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared is reached");
                mDuration = mp.getDuration();
                mEndText.setText(Utils.formatMillis(mDuration));
                mSeekbar.setMax(mDuration);
                restartSeekBarTimer();
            }
        });

        mVideoView.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSeekBarTimer();
                mPlaybackState = PlaybackState.IDLE;
                updatePlayButton(PlaybackState.IDLE);
                mControllersTimer = new Timer();
                mControllersTimer.schedule(new BackToDetailTask(), HIDE_CONTROLLER_TIME);
            }
        });
    }

    /*
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { return
     * super.onKeyDown(keyCode, event); }
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentPos = 0;
        int delta = (int) (mDuration / SCRUB_SEGMENT_DIVISOR);
        if (delta < MIN_SCRUB_TIME)
            delta = MIN_SCRUB_TIME;

        Log.v("keycode", "duration " + mDuration + " delta:" + delta);
        if (!mControlersVisible) {
            updateControlersVisibility(true);
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                currentPos = mVideoView.getCurrentPosition();
                currentPos -= delta;
                if (currentPos > 0)
                    play(currentPos);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                currentPos = mVideoView.getCurrentPosition();
                currentPos += delta;
                if (currentPos < mDuration)
                    play(currentPos);
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void updateSeekbar(int position, int duration) {
        mSeekbar.setProgress(position);
        mSeekbar.setMax(duration);
        mStartText.setText(Utils.formatMillis(position));
        mEndText.setText(Utils.formatMillis(duration));
    }

    private void updatePlayButton(PlaybackState state) {
        switch (state) {
            case PLAYING:
                mLoading.setVisibility(View.INVISIBLE);
                mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(
                        getResources().getDrawable(R.drawable.ic_pause_playcontrol_normal));
                break;
            case PAUSED:
            case IDLE:
                mLoading.setVisibility(View.INVISIBLE);
                mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(
                        getResources().getDrawable(R.drawable.ic_play_playcontrol_normal));
                break;
            case BUFFERING:
                mPlayPause.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void updateMetadata(boolean visible) {
        mVideoView.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void loadViews() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mStartText = (TextView) findViewById(R.id.startText);
        mEndText = (TextView) findViewById(R.id.endText);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mPlayPause = (ImageView) findViewById(R.id.playpause);
        mLoading = (ProgressBar) findViewById(R.id.progressBar);
        mControllers = findViewById(R.id.controllers);
        mContainer = findViewById(R.id.container);

        mVideoView.setOnClickListener(mPlayPauseHandler);
    }

    View.OnClickListener mPlayPauseHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "clicked play pause button");

            if (!mControlersVisible) {
                updateControlersVisibility(true);
            }

            if (mPlaybackState == PlaybackState.PAUSED) {
                mPlaybackState = PlaybackState.PLAYING;
                updatePlayButton(mPlaybackState);
                mVideoView.start();
                startControllersTimer();
            } else {
                mVideoView.pause();
                mPlaybackState = PlaybackState.PAUSED;
                updatePlayButton(PlaybackState.PAUSED);
                stopControllersTimer();
            }
        }
    };

//    private class GetLiveStreamAsyncTask extends AsyncTask<Void, Void, LiveStreamDetailsEvent> {
//
//        private String TAG = GetLiveStreamAsyncTask.class.getSimpleName();
//
//        @Override
//        protected LiveStreamDetailsEvent doInBackground( Void... params ) {
//            Log.d( TAG, "doInBackground : get livestream hls" );
//
//            return mContentService.getLiveStream( new RequestLiveStreamDetailsEvent( mLiveStreamInfo.getId() ) );
//        }
//
//        @Override
//        protected void onPostExecute( LiveStreamDetailsEvent liveStreamDetailsEvent ) {
//
//            if( !liveStreamDetailsEvent.isModified() ) {
//                Log.d( TAG, "onPostExecute : livestream hls is not modified" );
//            }
//
//            if( liveStreamDetailsEvent.isEntityFound() ) {
//                Log.d( TAG, "onPostExecute : livestream hls retrieved" );
//
//                mLiveStreamInfo = LiveStreamInfo.fromDetails( liveStreamDetailsEvent.getDetails() );
//                Log.d( TAG, "onPostExecute : mLiveStreamInfo=" + mLiveStreamInfo );
//                if( mLiveStreamInfo.getPercentComplete() > 2 ) {
//                    Log.v( TAG, "onPostExecute : livestream hls processed greater than 2%" );
//
//                    stopGetLiveStreamTimer();
//
////                    loadViews();
////                    setupController();
////                    setupControlsCallbacks();
//                    startVideoPlayerHls();
//                    updateMetadata( true );
//
//                }
//
//            }
//
//        }
//
//    }

//    TimerTask mGetLiveStreamTask = new TimerTask() {
//
//        @Override
//        public void run() {
//
//            mHandler.post( new Runnable() {
//
//                @Override
//                public void run() {
//
//                    new GetLiveStreamAsyncTask().execute();
//
//                }
//
//            });
//
//        }
//
//    };

//    private class AddRecordingLiveStreamAsyncTask extends AsyncTask<Void, Void, LiveStreamAddedEvent> {
//
//        private String TAG = AddRecordingLiveStreamAsyncTask.class.getSimpleName();
//
//        @Override
//        protected LiveStreamAddedEvent doInBackground( Void... params ) {
//            Log.d( TAG, "doInBackground : adding recording hls" );
//
//            return mContentService.addRecordingLiveStream( new AddRecordingLiveStreamEvent( mSelectedProgram.getRecording().getRecordedId(), mSelectedProgram.getChannel().getChanId(), mSelectedProgram.getRecording().getStartTs(), 0, mMetrics.widthPixels, mMetrics.heightPixels, null, null, null ) );
//        }
//
//        @Override
//        protected void onPostExecute( LiveStreamAddedEvent liveStreamAddedEvent ) {
//            Log.d( TAG, "onPostExecute : enter" );
//
//            if( null != liveStreamAddedEvent.getKey() ) {
//                Log.d( TAG, "onPostExecute : recording hls added" );
//
//                mLiveStreamInfo = LiveStreamInfo.fromDetails( liveStreamAddedEvent.getDetails() );
//
//                startGetLiveStreamTimer();
//
//            } else {
//                Log.d( TAG, "onPostExecute : recording hls NOT added" );
//            }
//
//            Log.d( TAG, "onPostExecute : exit" );
//        }
//
//    }
//
//    private class AddVideoLiveStreamAsyncTask extends AsyncTask<Void, Void, LiveStreamAddedEvent> {
//
//        private String TAG = AddVideoLiveStreamAsyncTask.class.getSimpleName();
//
//        @Override
//        protected LiveStreamAddedEvent doInBackground( Void... params ) {
//            Log.d( TAG, "doInBackground : adding recording hls" );
//
//            return mContentService.addVideoLiveStream( new AddVideoLiveStreamEvent( mSelectedVideo.getId(), 0, mMetrics.widthPixels, mMetrics.heightPixels, null, null, null ) );
//        }
//
//        @Override
//        protected void onPostExecute( LiveStreamAddedEvent liveStreamAddedEvent ) {
//
//            if( null != liveStreamAddedEvent.getKey() ) {
//                Log.d( TAG, "onPostExecute : recording hls added" );
//
//                mLiveStreamInfo = LiveStreamInfo.fromDetails( liveStreamAddedEvent.getDetails() );
//
//                startGetLiveStreamTimer();
//
//            } else {
//                Log.d( TAG, "onPostExecute : recording hls NOT added" );
//            }
//
//        }
//
//    }
//
//    private class RemoveRecordingLiveStreamAsyncTask extends AsyncTask<Void, Void, LiveStreamRemovedEvent> {
//
//        private String TAG = RemoveRecordingLiveStreamAsyncTask.class.getSimpleName();
//
//        @Override
//        protected LiveStreamRemovedEvent doInBackground( Void... params ) {
//            Log.d( TAG, "doInBackground : removing recording hls" );
//
//            return mContentService.removeLiveStream( new RemoveLiveStreamEvent( mLiveStreamInfo.getId() ) );
//        }
//
//        @Override
//        protected void onPostExecute( LiveStreamRemovedEvent liveStreamRemovedEvent ) {
//
//            if( liveStreamRemovedEvent.isDeletionCompleted() ) {
//                Log.d( TAG, "onPostExecute : recording hls removed" );
//
//            } else {
//                Log.d( TAG, "onPostExecute : recording hls NOT removed" );
//            }
//
//        }
//
//    }

}

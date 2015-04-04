package org.mythtv.android.player.app.player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

/**
 * Created by dmfrey on 4/4/15.
 */
public class PlayerActivity extends Activity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    public static final String FULL_URL_TAG = "full_url";
    public static final String POSITION_TAG = "position";

    private VideoView mVideoView;
    MediaController mMediaController;
    private String mFileUrl;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v(TAG, "onCreate : enter");
        super.onCreate( savedInstanceState );

        setContentView(R.layout.activity_app_player);

        mFileUrl = getIntent().getStringExtra( FULL_URL_TAG );

        mVideoView =  (VideoView) findViewById( R.id.videoView );
        mVideoView.setVideoPath(MainApplication.getInstance().getMasterBackendUrl() + mFileUrl.substring(1));

        MediaController mMediaController = new MediaController( this );
        mMediaController.setAnchorView( mVideoView );
        mVideoView.setMediaController(mMediaController);

        mVideoView.start();

        int position = 0;
        if( null != savedInstanceState && savedInstanceState.containsKey( POSITION_TAG ) ) {

            position = savedInstanceState.getInt( POSITION_TAG );
            mVideoView.seekTo( position);
        }

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        if( mVideoView.isPlaying() ) {
            outState.putInt( POSITION_TAG, mVideoView.getCurrentPosition() );
        }

    }

}

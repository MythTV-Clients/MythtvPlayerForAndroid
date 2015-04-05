package org.mythtv.android.player.app.player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.CastMember;
import org.mythtv.android.library.core.domain.dvr.Program;

/**
 * Created by dmfrey on 4/4/15.
 */
public class RecordingPlayerActivity extends Activity {

    private static final String TAG = RecordingPlayerActivity.class.getSimpleName();

    public static final String PROGRAM_TAG = "program";
    public static final String FULL_URL_TAG = "full_url";
    public static final String POSITION_TAG = "position";

    private VideoView mVideoView;
    private MediaController mMediaController;
    private String mFileUrl;

    private RelativeLayout details;
    private TextView showName, episodeName, duration, startTime, endTime, description, castMembers;
    private Program mProgram;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_app_recording_player);

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

        details = (RelativeLayout) findViewById( R.id.player_details );
        if( null != details ) {

            showName = (TextView) findViewById( R.id.player_show_name );
            episodeName = (TextView) findViewById( R.id.player_episode_name );
            duration = (TextView) findViewById( R.id.player_duration );
            startTime = (TextView) findViewById( R.id.player_start_time );
            endTime = (TextView) findViewById( R.id.player_end_time );
            description = (TextView) findViewById( R.id.player_description );
            castMembers = (TextView) findViewById( R.id.player_cast );

            mProgram = (Program) getIntent().getSerializableExtra(PROGRAM_TAG);

            showName.setText( mProgram.getTitle() );
            episodeName.setText( mProgram.getSubTitle() );
            duration.setText( Minutes.minutesBetween( mProgram.getStartTime(), mProgram.getEndTime() ).getMinutes() + " mins" );
            startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString("yyyy-MM-dd hh:mm a") );
            endTime.setText( mProgram.getEndTime().withZone( DateTimeZone.getDefault() ).toString("yyyy-MM-dd hh:mm a"));
            description.setText(mProgram.getDescription());

            if( null != mProgram.getCastMembers() && !mProgram.getCastMembers().isEmpty() ) {

                StringBuilder castMemberNames = new StringBuilder();
                for( CastMember castMember : mProgram.getCastMembers() ) {

                    castMemberNames.append( castMember.getName() ).append( ", " );

                }

                String names = castMemberNames.toString();
                if( names.endsWith( ", " ) ) {

                    names = names.substring( 0, names.length() - 2 );
                }
                castMembers.setText( names );

            }

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

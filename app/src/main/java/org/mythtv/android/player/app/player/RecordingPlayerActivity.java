/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.player.app.player;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.CastMember;
import org.mythtv.android.library.core.domain.dvr.Program;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_recording_player);

        String mFileUrl = getIntent().getStringExtra( FULL_URL_TAG );
        Uri fileUri = null;
        try {

            URL url = new URL( MainApplication.getInstance().getMasterBackendUrl() + mFileUrl.substring( 1 ) );
            URI uri = new URI( url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef() );
            fileUri = Uri.parse( uri.toString() );
            Log.v( TAG, "onCreate : fileUrl=" + fileUri.toString() );

        } catch( MalformedURLException | URISyntaxException | NullPointerException e ) {
            Log.v( TAG, "onCreate : error parsing mFileUrl=" + mFileUrl, e );
        }

        mVideoView =  (VideoView) findViewById( R.id.videoView );
        mVideoView.setVideoURI(fileUri);

        MediaController mMediaController = new MediaController( this );
        mMediaController.setAnchorView( mVideoView );
        mVideoView.setMediaController( mMediaController );

        mVideoView.start();

        int position = 0;
        if( null != savedInstanceState && savedInstanceState.containsKey( POSITION_TAG ) ) {

            position = savedInstanceState.getInt( POSITION_TAG );
            mVideoView.seekTo( position);
        }

        RelativeLayout details = (RelativeLayout) findViewById( R.id.player_details );
        if( null != details ) {

            TextView showName = (TextView) findViewById( R.id.player_show_name );
            TextView episodeName = (TextView) findViewById( R.id.player_episode_name );
            TextView duration = (TextView) findViewById( R.id.player_duration );
            TextView startTime = (TextView) findViewById( R.id.player_start_time );
            TextView endTime = (TextView) findViewById( R.id.player_end_time );
            TextView description = (TextView) findViewById( R.id.player_description );
            TextView castMembers = (TextView) findViewById( R.id.player_cast );

            Program mProgram = (Program) getIntent().getSerializableExtra( PROGRAM_TAG );

            showName.setText( mProgram.getTitle() );
            episodeName.setText( mProgram.getSubTitle() );
            duration.setText( Minutes.minutesBetween( mProgram.getStartTime(), mProgram.getEndTime() ).getMinutes() + " mins" );
            startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "-S", Locale.getDefault() ) ) );
            endTime.setText( mProgram.getEndTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "-S", Locale.getDefault() ) ) );
            description.setText( mProgram.getDescription() );

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

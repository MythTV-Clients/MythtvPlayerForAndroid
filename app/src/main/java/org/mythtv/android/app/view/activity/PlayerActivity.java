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

package org.mythtv.android.app.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import org.mythtv.android.app.R;

/*
 * Created by dmfrey on 4/4/15.
 */
public class PlayerActivity extends Activity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context, String uri ) {

        return new Intent( context, PlayerActivity.class )
                .setData( Uri.parse( uri ) );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_video_player);

        Uri fileUri = getIntent().getData();

        VideoView mVideoView = (VideoView) findViewById( R.id.videoView );
        mVideoView.setVideoURI( fileUri );

        MediaController mMediaController = new MediaController( this );
        mMediaController.setAnchorView( mVideoView );
        mVideoView.setMediaController( mMediaController );

        mVideoView.start();

        Log.v( TAG, "onCreate : exit" );
    }

}

package org.mythtv.android.player.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.R;
import org.mythtv.android.player.recordings.RecordingDetailsFragment;

/**
 * Created by dmfrey on 12/8/14.
 */
public class VideoDetailsActivity extends BaseActionBarActivity {

    private VideoDetailsFragment mVideoDetailsFragment;
    Video mVideo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_video_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( VideoDetailsFragment.VIDEO_KEY ) ) {
            mVideo = (Video) getIntent().getSerializableExtra( VideoDetailsFragment.VIDEO_KEY );
        }

        mVideoDetailsFragment = (VideoDetailsFragment) getFragmentManager().findFragmentById( R.id.fragment_video_details );
        if( null != mVideo ) {

            mVideoDetailsFragment.setVideo( mVideo );

            getSupportActionBar().setTitle( mVideo.getTitle() );

        }

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        outState.putSerializable( VideoDetailsFragment.VIDEO_KEY, mVideo );

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( VideoDetailsFragment.VIDEO_KEY ) ) {
            mVideo = (Video) savedInstanceState.getSerializable( VideoDetailsFragment.VIDEO_KEY );
        }

    }

    @Override
    protected void updateData() {

    }

}

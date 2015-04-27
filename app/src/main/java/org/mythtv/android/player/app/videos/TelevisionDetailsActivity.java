package org.mythtv.android.player.app.videos;

import android.os.Bundle;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;

/**
 * Created by dmfrey on 12/8/14.
 */
public class TelevisionDetailsActivity extends AbstractBaseAppCompatActivity {

    private TelevisionDetailsFragment mTelevisionDetailsFragment;
    Video mVideo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_television_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( null != savedInstanceState ) {

            mVideo = (Video) savedInstanceState.getSerializable( TelevisionDetailsFragment.VIDEO_KEY );

        } else {

            if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( TelevisionDetailsFragment.VIDEO_KEY ) ) {

                mVideo = (Video) getIntent().getSerializableExtra( TelevisionDetailsFragment.VIDEO_KEY );

            }

        }

        mTelevisionDetailsFragment = (TelevisionDetailsFragment) getFragmentManager().findFragmentById( R.id.fragment_video_details );

    }

    @Override
    protected void onResume() {
        super.onResume();

        if( null != mVideo ) {

            mTelevisionDetailsFragment.setVideo( mVideo );

            getSupportActionBar().setTitle( mVideo.getTitle() );

        }

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        outState.putSerializable( TelevisionDetailsFragment.VIDEO_KEY, mVideo );

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( TelevisionDetailsFragment.VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( TelevisionDetailsFragment.VIDEO_KEY );

        }

    }

    @Override
    protected void updateData() {

    }

}

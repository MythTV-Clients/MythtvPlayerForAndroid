package org.mythtv.android.player.app.videos;

import android.os.Bundle;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class MovieDetailsActivity extends AbstractBaseAppCompatActivity {

    private MovieDetailsFragment mMovieDetailsFragment;
    Video mVideo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_movie_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( null != savedInstanceState ) {

            mVideo = (Video) savedInstanceState.getSerializable( MovieDetailsFragment.VIDEO_KEY );

        } else {

            if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( MovieDetailsFragment.VIDEO_KEY ) ) {

                mVideo = (Video) getIntent().getSerializableExtra(MovieDetailsFragment.VIDEO_KEY);

            }

        }

        mMovieDetailsFragment = (MovieDetailsFragment) getFragmentManager().findFragmentById( R.id.fragment_video_details );

    }

    @Override
    protected void onResume() {
        super.onResume();

        if( null != mVideo ) {

            mMovieDetailsFragment.setVideo( mVideo );

            getSupportActionBar().setTitle( mVideo.getTitle() );

        }

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        outState.putSerializable( MovieDetailsFragment.VIDEO_KEY, mVideo );

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( MovieDetailsFragment.VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( MovieDetailsFragment.VIDEO_KEY );

        }

    }

    @Override
    protected void updateData() {

    }

}

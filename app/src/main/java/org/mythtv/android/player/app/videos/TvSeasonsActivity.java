package org.mythtv.android.player.app.videos;

import android.os.Bundle;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;

/**
 * Created by dmfrey on 4/22/15.
 */
public class TvSeasonsActivity extends AbstractBaseAppCompatActivity {

    public static final String VIDEO_KEY = "video";

    private TvSeasonsFragment mTvSeasonsFragment;

    private Video mVideo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_tv_seasons;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mTvSeasonsFragment = (TvSeasonsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_tv_seasons );

        if( null != savedInstanceState ) {

            mVideo = (Video) savedInstanceState.getSerializable( VIDEO_KEY );

        } else {

            mVideo = (Video) getIntent().getSerializableExtra( VIDEO_KEY );

        }

        if( null != mVideo ) {

            getSupportActionBar().setTitle( mVideo.getTitle() );

            mTvSeasonsFragment.setShow( mVideo );

        }

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {

        outState.putSerializable( VIDEO_KEY, mVideo );

        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( VIDEO_KEY );

        }

    }

    @Override
    protected void updateData() {

    }

}

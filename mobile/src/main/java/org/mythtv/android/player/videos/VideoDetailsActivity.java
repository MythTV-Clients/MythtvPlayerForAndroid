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

    private static final String TAG = VideoDetailsActivity.class.getSimpleName();

    private static final String VIDEO_DETAILS_FRAGMENT_TAG = VideoDetailsFragment.class.getCanonicalName();

    Video mVideo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recording_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate : enter" );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( VideoDetailsFragment.VIDEO_KEY ) ) {
            mVideo = (Video) getIntent().getSerializableExtra( VideoDetailsFragment.VIDEO_KEY );
        }

        getSupportActionBar().setTitle( mVideo.getTitle() );

        VideoDetailsFragment videoDetailsFragment = (VideoDetailsFragment) getSupportFragmentManager().findFragmentByTag( VIDEO_DETAILS_FRAGMENT_TAG );
        if( null == videoDetailsFragment ) {
            Log.d( TAG, "onCreate : creating new VideoDetailsFragment" );

            Bundle args = new Bundle();
            args.putSerializable( VideoDetailsFragment.VIDEO_KEY, mVideo );

            videoDetailsFragment = (VideoDetailsFragment) Fragment.instantiate( this, VideoDetailsFragment.class.getName(), args );

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, videoDetailsFragment, VIDEO_DETAILS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();
        }

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        Log.d( TAG, "onSaveInstanceState : enter" );

        outState.putSerializable( VideoDetailsFragment.VIDEO_KEY, mVideo );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( VideoDetailsFragment.VIDEO_KEY ) ) {
            mVideo = (Video) savedInstanceState.getSerializable( VideoDetailsFragment.VIDEO_KEY );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Log.d( TAG, "onOptionsItemSelected : enter" );

        switch( item.getItemId() ) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home :
                Log.d( TAG, "onOptionsItemSelected : up selected" );

                finish();
//                NavUtils.navigateUpFromSameTask( this );
//                Intent intent = NavUtils.getParentActivityIntent( this );
//                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP );
//                NavUtils.navigateUpTo( this, intent );

                return true;

        }

        Log.d( TAG, "onOptionsItemSelected : exit" );
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void updateData() {

    }

}

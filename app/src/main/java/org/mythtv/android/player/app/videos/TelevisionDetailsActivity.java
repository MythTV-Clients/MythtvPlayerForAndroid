package org.mythtv.android.player.app.videos;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

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

        outState.putSerializable(TelevisionDetailsFragment.VIDEO_KEY, mVideo);

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( TelevisionDetailsFragment.VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( TelevisionDetailsFragment.VIDEO_KEY );

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void updateData() {

    }

}

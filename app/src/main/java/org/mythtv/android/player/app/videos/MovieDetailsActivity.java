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

package org.mythtv.android.player.app.videos;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.R;

/*
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

        outState.putSerializable(MovieDetailsFragment.VIDEO_KEY, mVideo);

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( MovieDetailsFragment.VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( MovieDetailsFragment.VIDEO_KEY );

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

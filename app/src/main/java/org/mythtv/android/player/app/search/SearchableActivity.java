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

package org.mythtv.android.player.app.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.persistence.repository.MythtvSearchSuggestionProvider;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.player.app.recordings.RecordingDetailsActivity;
import org.mythtv.android.player.app.recordings.RecordingDetailsFragment;
import org.mythtv.android.player.app.videos.MovieDetailsActivity;
import org.mythtv.android.player.app.videos.MovieDetailsFragment;
import org.mythtv.android.player.app.videos.TelevisionDetailsActivity;
import org.mythtv.android.player.app.videos.TelevisionDetailsFragment;
import org.mythtv.android.player.common.ui.adapters.SearchResultItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 3/14/15.
 */
public class SearchableActivity extends AbstractBaseAppCompatActivity implements SearchResultItemAdapter.ItemClickListener {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    RecyclerView mRecyclerView;
    SearchResultItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_search;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        mRecyclerView = (RecyclerView) findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );

        getSupportActionBar().setTitle( getResources().getString( R.string.search_results ) );

        Intent intent = getIntent();
        if( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) {
            Log.v( TAG, "onCreate : sending query to fragment" );

            String query = intent.getStringExtra( SearchManager.QUERY );

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions( this, MythtvSearchSuggestionProvider.AUTHORITY, MythtvSearchSuggestionProvider.MODE );
            suggestions.saveRecentQuery( query, null );

            String recordingGroup = null;
            if( MainApplication.getInstance().enableDefaultRecordingGroup() ) {

                recordingGroup = MainApplication.getInstance().defaultRecordingGroup();

            }

            List<Object> items = new ArrayList<>();
            AllProgramsEvent programsEvent = MainApplication.getInstance().getDvrService().searchRecordedPrograms( new SearchRecordedProgramsEvent( query, recordingGroup ) );
            if( programsEvent.isEntityFound() ) {

                for( ProgramDetails details : programsEvent.getDetails() ) {

                    Program program = Program.fromDetails( details );
                    items.add( program );

                }

            }

            AllVideosEvent videosEvent = MainApplication.getInstance().getVideoService().searchVideos( new SearchVideosEvent( query ) );
            if( videosEvent.isEntityFound() ) {

                for( VideoDetails details : videosEvent.getDetails() ) {

                    Video video = Video.fromDetails( details );
                    items.add( video );

                }

            }

            if( !items.isEmpty() ) {

                mAdapter = new SearchResultItemAdapter(items, this);
                mRecyclerView.setAdapter(mAdapter);

            }

        }

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                finish();

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void updateData() {

    }

    @Override
    public void onProgramItemClicked( View v, Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( this, RecordingDetailsActivity.class );
        recordingDetails.putExtras( args );
        startActivity( recordingDetails );

    }

    @Override
    public void onVideoItemClicked( View v, Video video ) {

        if( "MOVIE".equals( video.getContentType() ) ) {

            Bundle args = new Bundle();
            args.putSerializable( MovieDetailsFragment.VIDEO_KEY, video );

            Intent movieDetails = new Intent( this, MovieDetailsActivity.class );
            movieDetails.putExtras( args );
            startActivity( movieDetails );

        }

        if( "TELEVISION".equals( video.getContentType() ) ) {

            Bundle args = new Bundle();
            args.putSerializable( TelevisionDetailsFragment.VIDEO_KEY, video );

            Intent televisionDetails = new Intent( this, TelevisionDetailsActivity.class );
            televisionDetails.putExtras( args );
            startActivity( televisionDetails );

        }

    }

}

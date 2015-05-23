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

package org.mythtv.android.player.tv.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

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
import org.mythtv.android.player.tv.recordings.RecordingCardPresenter;
import org.mythtv.android.player.tv.recordings.RecordingDetailsActivity;
import org.mythtv.android.player.tv.recordings.RecordingDetailsFragment;
import org.mythtv.android.player.tv.videos.VideoCardPresenter;
import org.mythtv.android.player.tv.videos.VideoDetailsActivity;
import org.mythtv.android.player.tv.videos.VideoDetailsFragment;

/**
 * Created by dmfrey on 3/20/15.
 */
public class SearchableFragment extends SearchFragment implements /* LoaderManager.LoaderCallbacks<List<Program>>,*/ SearchFragment.SearchResultProvider {

    private static final String TAG = SearchableFragment.class.getSimpleName();

    private static final String QUERY_KEY = "query";
    private static final int SEARCH_DELAY_MS = 1000;

    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler();
    private String mQuery;

//    @Override
//    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {
//
//        SearchProgramsAsyncTaskLoader loader = new SearchProgramsAsyncTaskLoader( getActivity() );
//        loader.setQuery( args.getString( QUERY_KEY ) );
//
//        return loader;
//    }
//
//    @Override
//    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {
//
//        if( !programs.isEmpty() ) {
//
//            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( new RecordingCardPresenter() );
//            for( Program program : programs ) {
//                Log.v( TAG, "onLoaderFinished : program=" + program );
//
//                listRowAdapter.add( program );
//            }
//            HeaderItem header = new HeaderItem( 0, getResources().getString( R.string.search_results ) + " '" + mQuery + "'" );
//            mRowsAdapter.add( new ListRow( header, listRowAdapter ) );
//
//        }
//
//    }
//
//    @Override
//    public void onLoaderReset( Loader<List<Program>> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
        setSearchResultProvider( this );
        setOnItemViewClickedListener( new ItemViewClickedListener() );

    }

    @Override
    public ObjectAdapter getResultsAdapter() {

        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange( String newQuery ) {

        query( newQuery );

        return true;
    }

    @Override
    public boolean onQueryTextSubmit( String query ) {

        query( query );

        return true;
    }

    private void query( String query ) {

        mQuery = query;
        mRowsAdapter.clear();

//        Bundle args = new Bundle();
//        if( null != query ) {
//            args.putString( QUERY_KEY, query );
//        }

//        getLoaderManager().restartLoader( 0, args, this );

        String recordingGroup = null;
        if( MainApplication.getInstance().enableDefaultRecordingGroup() ) {

            recordingGroup = MainApplication.getInstance().defaultRecordingGroup();

        }

        ArrayObjectAdapter programRowAdapter = new ArrayObjectAdapter( new RecordingCardPresenter() );
        AllProgramsEvent programsEvent = MainApplication.getInstance().getDvrService().searchRecordedPrograms( new SearchRecordedProgramsEvent( query, recordingGroup ) );
        if( programsEvent.isEntityFound() ) {

            for( ProgramDetails details : programsEvent.getDetails() ) {

                Program program = Program.fromDetails( details );
                programRowAdapter.add( program );

            }
            HeaderItem header = new HeaderItem( 0, getResources().getString( R.string.recording ) + " " + getResources().getString( R.string.search_results ) + " '" + mQuery + "'" );
            mRowsAdapter.add( new ListRow( header, programRowAdapter ) );

        }

        ArrayObjectAdapter videoRowAdapter = new ArrayObjectAdapter( new VideoCardPresenter() );
        AllVideosEvent videosEvent = MainApplication.getInstance().getVideoService().searchVideos( new SearchVideosEvent( query ) );
        if( videosEvent.isEntityFound() ) {

            for( VideoDetails details : videosEvent.getDetails() ) {

                Video video = Video.fromDetails( details );
                videoRowAdapter.add( video );

            }
            HeaderItem header = new HeaderItem( 1, getResources().getString( R.string.video ) + " " + getResources().getString( R.string.search_results ) + " '" + mQuery + "'" );
            mRowsAdapter.add( new ListRow( header, videoRowAdapter ) );

        }


    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof Program ) {

                Program program = (Program) item;
                Log.d( TAG, "Program: " + item.toString() );

                Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
                intent.putExtra( RecordingDetailsFragment.PROGRAM_KEY, program );
                startActivity( intent );

            } else {

                Video video = (Video) item;

                if( "MOVIE".equals( video.getContentType() ) ) {
                    Log.d( TAG, "Video: " + video.toString() );

                    Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
                    intent.putExtra( VideoDetailsFragment.VIDEO, video );
                    startActivity( intent );

                } else {
                    Log.d( TAG, "Video: " + video.toString() );

                    Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
                    intent.putExtra( VideoDetailsFragment.VIDEO, video );
                    startActivity( intent );

                }

            }

        }

    }

}

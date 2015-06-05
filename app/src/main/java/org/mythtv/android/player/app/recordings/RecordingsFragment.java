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

package org.mythtv.android.player.app.recordings;

import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.utils.RefreshRecordedProgramsTask;
import org.mythtv.android.player.app.listeners.EndlessScrollListener;
import org.mythtv.android.player.common.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.player.app.loaders.ProgramsAsyncTaskLoader;
import org.mythtv.android.player.app.AbstractBaseFragment;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Program>>, ProgramItemAdapter.ProgramItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshRecordedProgramsTask.OnRefreshRecordedProgramTaskListener {

    private static final String PROGRAM_TITLE_KEY = "program_title";
    private static final String PROGRAM_INETREF_KEY = "program_inetref";

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ProgramItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;
    boolean mShowTitle = false;
    String mTitle = null;
    String mInetref = null;

    int mLimit = 5, mOffset = -1;

    View parentView;

    @Override
    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {

        return new ProgramsAsyncTaskLoader( getActivity(), mTitle, mInetref, mLimit, mOffset );
    }

    @Override
    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {

        if( !programs.isEmpty() ) {

            boolean notify = false;
            for( Program program : programs ) {

                if( !mAdapter.getPrograms().contains( program ) ) {

                    mAdapter.getPrograms().add( program );
                    notify = true;

                }

            }

            if( notify ) {

                mAdapter.notifyDataSetChanged();

            }

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Program>> loader ) {

        mRecyclerView.setAdapter( null );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        parentView = inflater.inflate( R.layout.program_list, container, false );

        mSwipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById( R.id.swipe_refresh_layout );
        mSwipeRefreshLayout.setOnRefreshListener( this );

        mAdapter = new ProgramItemAdapter( this );

        mLayoutManager = new LinearLayoutManager( getActivity() );

        mRecyclerView = (RecyclerView) parentView.findViewById( R.id.list );
        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mRecyclerView.addOnScrollListener( new EndlessScrollListener( mLayoutManager ) {

            @Override
            public void onLoadMore( int page ) {

                mOffset = ( page - 1 ) * mLimit;

                getLoaderManager().restartLoader( 0, null, RecordingsFragment.this );

            }

        });

        mEmpty = (TextView) parentView.findViewById( R.id.empty );

        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader( 0, null, this );

    }

    public void setPrograms( String title, String inetref ) {

        mShowTitle = ( null == title );
        mTitle = title;
        mInetref = inetref;

        mAdapter.setShowTitle( mShowTitle );

        getLoaderManager().initLoader( 0, null, this );

    }

    public void onProgramItemClicked( View v, Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( getActivity(), RecordingDetailsActivity.class );
        recordingDetails.putExtras( args );
        startActivity( recordingDetails );

//        String transitionName = getString( R.string.recording_transition );
//        ActivityOptionsCompat options =
//            ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(),
//                v,   // The view which starts the transition
//                transitionName    // The transitionName of the view we’re transitioning to
//            );
//        ActivityCompat.startActivity( getActivity(), recordingDetails, options.toBundle() );

    }

    @Override
    public void connected() {

        mRecyclerView.setVisibility( View.VISIBLE );
        mEmpty.setVisibility( View.GONE );

    }

    @Override
    public void notConnected() {

        mRecyclerView.setVisibility( View.GONE );
        mEmpty.setVisibility( View.VISIBLE );

    }

    @Override
    public void onRefresh() {

        new RefreshRecordedProgramsTask( this ).execute( mTitle );

    }

    @Override
    public void onRefreshComplete( boolean updated ) {

        if( mSwipeRefreshLayout.isRefreshing() ) {

            mSwipeRefreshLayout.setRefreshing( false );

        }

        if( updated ) {

            Snackbar
                    .make( parentView, R.string.recorded_programs_updated, Snackbar.LENGTH_LONG )
                    .show(); // Don’t forget to show!

        }

    }

}

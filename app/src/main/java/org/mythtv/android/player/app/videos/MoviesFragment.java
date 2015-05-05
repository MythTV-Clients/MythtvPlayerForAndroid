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

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.RefreshVideosTask;
import org.mythtv.android.player.app.listeners.EndlessScrollListener;
import org.mythtv.android.player.common.ui.adapters.VideoItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseFragment;
import org.mythtv.android.player.app.loaders.VideosAsyncTaskLoader;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class MoviesFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Video>>, VideoItemAdapter.VideoItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshVideosTask.OnRefreshVideosTaskListener {

    private static final String TAG = MoviesFragment.class.getSimpleName();

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    VideoItemAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    TextView mEmpty;

    int mLimit = 5, mOffset = -1;

    @Override
    public Loader<List<Video>> onCreateLoader( int id, Bundle args ) {

        return new VideosAsyncTaskLoader( getActivity(), VideosAsyncTaskLoader.Type.MOVIE, null, null, mLimit, mOffset );
    }

    @Override
    public void onLoadFinished( Loader<List<Video>> loader, List<Video> videos ) {

        if( !videos.isEmpty() ) {

            boolean notify = false;
            for( Video video : videos ) {

                if( !mAdapter.getVideos().contains( video ) ) {

                    mAdapter.getVideos().add( video );
                    notify = true;

                }

            }

            if( notify ) {

                mAdapter.notifyDataSetChanged();

            }

            mRecyclerView.setVisibility( View.VISIBLE );
            mEmpty.setVisibility( View.GONE );

        } else {

            mRecyclerView.setVisibility( View.GONE );
            mEmpty.setVisibility( View.VISIBLE );

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Video>> loader ) {

        mRecyclerView.setAdapter( null );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.video_list, container, false );

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mSwipeRefreshLayout.setOnRefreshListener( this );

        mAdapter = new VideoItemAdapter( this );

        mLayoutManager = new GridLayoutManager( getActivity(), 2 );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mRecyclerView.addOnScrollListener( new EndlessScrollListener( mLayoutManager ) {

            @Override
            public void onLoadMore( int page ) {

                mOffset = ( page - 1 ) * mLimit;

                getLoaderManager().restartLoader( 0, null, MoviesFragment.this );

            }

        });

        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onResume() {
        super.onResume();

//        getLoaderManager().restartLoader( 0, null, this );

    }

    public void reload() {

//        getLoaderManager().restartLoader( 0, null, this );

    }

    public void videoItemClicked( View v, Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( MovieDetailsFragment.VIDEO_KEY, video );

        Intent videoDetails = new Intent( getActivity(), MovieDetailsActivity.class );
        videoDetails.putExtras( args );

        if( Build.VERSION.SDK_INT >= 16 ) {

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(), null );
            getActivity().startActivity(videoDetails, options.toBundle());

        } else {

            startActivity( videoDetails );

        }

    }

    @Override
    public void connected() {

//        mRecyclerView.setVisibility( View.VISIBLE );
//        mEmpty.setVisibility(View.GONE);

    }

    @Override
    public void notConnected() {

//        mRecyclerView.setVisibility( View.GONE );
//        mEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRefresh() {

        new RefreshVideosTask( this ).execute();

    }

    @Override
    public void onRefreshComplete() {

        if( mSwipeRefreshLayout.isRefreshing() ) {

            mSwipeRefreshLayout.setRefreshing( false );

        }

    }

}

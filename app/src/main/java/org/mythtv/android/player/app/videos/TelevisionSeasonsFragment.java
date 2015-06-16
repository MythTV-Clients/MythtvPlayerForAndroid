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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.app.AbstractBaseFragment;
import org.mythtv.android.player.app.listeners.EndlessScrollListener;
import org.mythtv.android.player.app.loaders.VideosAsyncTaskLoader;
import org.mythtv.android.player.common.ui.adapters.VideoTvItemAdapter;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TelevisionSeasonsFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Video>>, VideoTvItemAdapter.VideoItemClickListener {

    private static final String TAG = TelevisionSeasonsFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    VideoTvItemAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    TextView mEmpty;

    String mTitle;
    Integer mSeason;
    int mLimit = 5, mOffset = -1, count = 0;

    @Override
    public Loader<List<Video>> onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Log.v( TAG, "onCreateLoader : mTitle=" + mTitle + ", mSeason=" + mSeason + ", limit=" + mLimit + ", mOffset=" + mOffset );

        Log.v(TAG, "onCreateLoader : exit");
        return new VideosAsyncTaskLoader( getActivity(), VideosAsyncTaskLoader.Type.TELEVISION, mTitle, mSeason, mLimit, mOffset );
    }

    @Override
    public void onLoadFinished( Loader<List<Video>> loader, List<Video> videos ) {
        Log.v( TAG, "onLoadFinished : enter" );

        if( !videos.isEmpty() ) {
            Log.v( TAG, "onLoadFinished : loaded videos from db" );

            boolean notify = false;
            for( Video video : videos ) {

                if( !mAdapter.getVideos().contains( video ) ) {

                    mAdapter.getVideos().add( video );
                    notify = true;
                    count++;

                }

            }
            if( notify ) {

                mAdapter.notifyDataSetChanged();

            }

            mRecyclerView.setVisibility( View.VISIBLE );
            mEmpty.setVisibility( View.GONE );

        } else {

            if( mAdapter.getVideos().isEmpty() ) {

                mRecyclerView.setVisibility( View.GONE );
                mEmpty.setVisibility( View.VISIBLE );

            }

        }

        Log.v(TAG, "onLoadFinished : exit");
    }

    @Override
    public void onLoaderReset( Loader<List<Video>> loader ) {

        mRecyclerView.setAdapter( null );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.video_tv_list, container, false );

        mLayoutManager = new GridLayoutManager( getActivity(), 2 );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mRecyclerView.setLayoutManager(mLayoutManager);

        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    public void setShow( String title, Integer season ) {
        Log.v(TAG, "setShow : enter");

        mAdapter = new VideoTvItemAdapter( this );

        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.addOnScrollListener(new EndlessScrollListener(mLayoutManager) {

            @Override
            public void onLoadMore(int page) {
                Log.v(TAG, "onLoadMore : page=" + page + ", count=" + count);

                if (count < mLimit) {

                    Log.v(TAG, "onLoadMore : exit, don't reload");
                    return;
                }

                mOffset = (page - 1) * mLimit;

                getLoaderManager().restartLoader(0, null, TelevisionSeasonsFragment.this);

                Log.v(TAG, "onLoadMore : exit");
            }

        });

        mTitle = title;
        mSeason = season;
        mOffset = -1;

        getLoaderManager().restartLoader( 0, null, this );

        Log.v( TAG, "setShow : exit" );
    }

    public void videoItemClicked( View v, Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( TelevisionDetailsFragment.VIDEO_KEY, video );

        Intent videoDetails = new Intent( getActivity(), TelevisionDetailsActivity.class );
        videoDetails.putExtras( args );
        startActivity( videoDetails );

//        if( Build.VERSION.SDK_INT >= 16 ) {
//
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(), null, null );
//            getActivity().startActivity( videoDetails, options.toBundle() );
//
//        } else {
//
//            startActivity( videoDetails );
//
//        }

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

}

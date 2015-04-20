package org.mythtv.android.player.app.videos;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.RefreshVideosTask;
import org.mythtv.android.player.common.ui.adapters.VideoItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseFragment;
import org.mythtv.android.player.app.loaders.VideosMoviesAsyncTaskLoader;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class VideosFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Video>>, VideoItemAdapter.VideoItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshVideosTask.OnRefreshVideosTaskListener {

    private static final String TAG = VideosFragment.class.getSimpleName();

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    VideoItemAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    TextView mEmpty;

    public static VideosFragment getInstance() {

        VideosFragment fragment = new VideosFragment();

        return fragment;
    }

    @Override
    public Loader<List<Video>> onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Log.v( TAG, "onCreateLoader : exit" );
        return new VideosMoviesAsyncTaskLoader( getActivity() );
    }

    @Override
    public void onLoadFinished( Loader<List<Video>> loader, List<Video> videos ) {
        Log.v( TAG, "onLoadFinished : enter" );

        if( !videos.isEmpty() ) {
            Log.v( TAG, "onLoadFinished : loaded titleInfos from db" );

            mAdapter = new VideoItemAdapter( videos, this );
            mRecyclerView.setAdapter( mAdapter );

            mRecyclerView.setVisibility( View.VISIBLE );
            mEmpty.setVisibility(View.GONE);

        } else {

            mRecyclerView.setVisibility( View.GONE );
            mEmpty.setVisibility(View.VISIBLE);

        }

        Log.v(TAG, "onLoadFinished : exit");
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

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new GridLayoutManager( getActivity(), 2 );
        mRecyclerView.setLayoutManager(mLayoutManager);
        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.v( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        getLoaderManager().initLoader( 0, null, this );

        Log.v( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.v( TAG, "resume : enter" );
        super.onResume();

        getLoaderManager().restartLoader( 0, null, this );

        Log.v( TAG, "resume : exit" );
    }

    public void reload() {
        Log.v( TAG, "reload : enter" );

        getLoaderManager().restartLoader( 0, null, this );

        Log.v(TAG, "videos : exit");
    }

    public void videoItemClicked( View v, Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( VideoDetailsFragment.VIDEO_KEY, video );

        Intent videoDetails = new Intent( getActivity(), VideoDetailsActivity.class );
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

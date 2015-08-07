package org.mythtv.android.player.app.videos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.domain.videoDir.VideoDirItem;
import org.mythtv.android.library.core.utils.RefreshVideosTask;
import org.mythtv.android.library.events.video.RequestVideoEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.player.app.AbstractBaseFragment;
import org.mythtv.android.player.app.loaders.VideoDirItemsAsyncTaskLoader;
import org.mythtv.android.player.common.ui.adapters.VideoDirItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 7/25/15.
 */
public class VideoDirFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<VideoDirItem>>, VideoDirItemAdapter.VideoDirItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshVideosTask.OnRefreshVideosTaskListener {

    private static final String TAG = VideoDirFragment.class.getSimpleName();

    private static final String DEFAULT_PARENT_PATH = "";
    private static final String PARENT_KEY = "parent_path";

//    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    TextView mPath, mUp;
    VideoDirItemAdapter mAdapter;
    TextView mEmpty;

    List<String> history = new ArrayList<>();

    @Override
    public Loader<List<VideoDirItem>> onCreateLoader( int id, Bundle args ) {

        String parentPath = DEFAULT_PARENT_PATH;

        if( null != args ) {

            parentPath = args.getString( PARENT_KEY );

        }

        if( !history.contains( parentPath ) ) {

            history.add( parentPath );

        }

        return new VideoDirItemsAsyncTaskLoader( getActivity(), parentPath );
    }

    @Override
    public void onLoadFinished( Loader<List<VideoDirItem>> loader, List<VideoDirItem> videoDirItems ) {

        if( history.size() > 1 ) {

            mUp.setVisibility( View.VISIBLE );
            mPath.setText( history.get( history.size() - 1 ) );

        } else {

            mUp.setVisibility( View.GONE );

        }

        if( !videoDirItems.isEmpty() ) {

            mAdapter.getVideoDirItems().clear();
            mAdapter.getVideoDirItems().addAll( videoDirItems );
            mAdapter.notifyDataSetChanged();

            mRecyclerView.setVisibility( View.VISIBLE );
            mEmpty.setVisibility( View.GONE );

        } else {

            if( mAdapter.getVideoDirItems().isEmpty() ) {

                mRecyclerView.setVisibility( View.GONE );
                mEmpty.setVisibility( View.VISIBLE );

            }

        }

    }

    @Override
    public void onLoaderReset( Loader<List<VideoDirItem>> loader ) {

        mRecyclerView.setAdapter( null );

    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );
        setHasOptionsMenu( true );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.video_dir_list, container, false );

//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
//        mSwipeRefreshLayout.setOnRefreshListener( this );

        mAdapter = new VideoDirItemAdapter( this );

        mPath = (TextView) view.findViewById( R.id.path );
        mUp = (TextView) view.findViewById( R.id.up );
        mUp.setOnClickListener( upClick );

        mLayoutManager = new LinearLayoutManager( getActivity() );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mEmpty = (TextView) view.findViewById( android.R.id.empty );

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        getLoaderManager().initLoader( 0, null, this );

    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );

        inflater.inflate( R.menu.menu_videos_module, menu );

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.action_view_videos :

                MainApplication.getInstance().setVideoView( "grid" );

                Intent videos = new Intent( getActivity(), VideosActivity.class );
                videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( videos );

                return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void connected() {

    }

    @Override
    public void notConnected() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void videoDirItemClicked( View v, VideoDirItem videoDirItem ) {

        if( videoDirItem.getSort() == 0 ) {

            Bundle args = new Bundle();
            args.putString(PARENT_KEY, videoDirItem.getPath());
            getLoaderManager().restartLoader(0, args, VideoDirFragment.this);

        } else {

            Bundle args = new Bundle();
            args.putSerializable( MovieDetailsFragment.VIDEO_KEY, videoDirItem.getVideo() );

            Intent videoDetails = new Intent( getActivity(), MovieDetailsActivity.class );
            videoDetails.putExtras( args );
            startActivity( videoDetails );

        }

    }

    public boolean itemsInHistory() {

        return history.size() > 1;
    }

    public void backUpDirectory() {
        Log.d(TAG, "backUpDirectory : enter");

        String parent = history.get( history.size() - 1 );
        history.remove( parent );
        parent = history.get( history.size() - 1 );
        Log.d( TAG, "backUpDirectory : go to parent=" + parent );

        Bundle args = new Bundle();
        args.putString( PARENT_KEY, parent );
        getLoaderManager().restartLoader( 0, args, VideoDirFragment.this );

        Log.d( TAG, "backUpDirectory : exit" );
    }

    private View.OnClickListener upClick = new View.OnClickListener() {

        @Override
        public void onClick( View v ) {
            Log.d( TAG, "upClick.onClick : enter" );

            backUpDirectory();

            Log.d( TAG, "upClick.onClick : exit" );
        }

    };

    private void logHistory() {
        Log.d( TAG, "logHistory : enter" );

        if( history.isEmpty() ) {

            Log.d( TAG, "logHistory is empty" );

        } else {

            for( String h : history ) {

                Log.d( TAG, "logHistory : " + h );

            }

        }

        Log.d( TAG, "logHistory : exit" );
    }

}

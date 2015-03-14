package org.mythtv.android.player.recordings;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.core.utils.RefreshTitleInfosTask;
import org.mythtv.android.library.ui.adapters.TitleInfoItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.library.ui.loaders.TitleInfosAsyncTaskLoader;
import org.mythtv.android.player.AbstractBaseFragment;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TitleInfosFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<TitleInfo>>, TitleInfoItemAdapter.TitleInfoItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshTitleInfosTask.OnRefreshRecordedProgramTaskListener {

    private static final String TAG = TitleInfosFragment.class.getSimpleName();

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TitleInfoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;

    @Override
    public Loader<List<TitleInfo>> onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Log.v( TAG, "onCreateLoader : exit" );
        return new TitleInfosAsyncTaskLoader( getActivity() );
    }

    @Override
    public void onLoadFinished( Loader<List<TitleInfo>> loader, List<TitleInfo> titleInfos ) {
        Log.v( TAG, "onLoadFinished : enter" );

        if( !titleInfos.isEmpty() ) {
            Log.v( TAG, "onLoadFinished : loaded titleInfos from db" );

            mAdapter = new TitleInfoItemAdapter( titleInfos, this );
            mRecyclerView.setAdapter( mAdapter );

        }

        Log.v( TAG, "onLoadFinished : exit" );
    }

    @Override
    public void onLoaderReset( Loader<List<TitleInfo>> loader ) { }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        View view = inflater.inflate( R.layout.title_info_list, container, false );

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mSwipeRefreshLayout.setOnRefreshListener( this );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mEmpty = (TextView) view.findViewById( R.id.empty );

        Log.v( TAG, "onCreateView : exit" );
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.v( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        getLoaderManager().initLoader( 0, null, this ).forceLoad();

        Log.v( TAG, "onActivityCreated : exit" );
    }

    public void setTitleInfos( List<TitleInfo> titleInfos ) {
        Log.v( TAG, "setTitleInfos : enter" );

        getLoaderManager().restartLoader( 0, null, this );

        Log.v( TAG, "setTitleInfos : exit" );
    }

    public void titleInfoItemClicked( View v, TitleInfo titleInfo ) {

        Bundle args = new Bundle();
        if( !getActivity().getResources().getString( R.string.all_recordings ).equals( titleInfo.getTitle() ) ) {

            args.putSerializable( RecordingsActivity.TITLE_INFO, titleInfo );

        }

        Intent recordings = new Intent( getActivity(), RecordingsActivity.class );
        recordings.putExtras( args );

        String transitionName = getString( R.string.title_info_transition );
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(),
                v,   // The view which starts the transition
                transitionName    // The transitionName of the view we’re transitioning to
            );
        ActivityCompat.startActivity( getActivity(), recordings, options.toBundle() );

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

        new RefreshTitleInfosTask( this ).execute();

    }

    @Override
    public void onRefreshComplete() {

        if( mSwipeRefreshLayout.isRefreshing() ) {
            mSwipeRefreshLayout.setRefreshing( false );
        }

    }

}

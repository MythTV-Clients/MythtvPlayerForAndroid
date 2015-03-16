package org.mythtv.android.player.search;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.library.ui.adapters.SearchResultItemAdapter;
import org.mythtv.android.library.ui.loaders.SearchProgramsAsyncTaskLoader;
import org.mythtv.android.player.AbstractBaseFragment;
import org.mythtv.android.player.recordings.RecordingDetailsActivity;
import org.mythtv.android.player.recordings.RecordingDetailsFragment;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class SearchableRecordingsFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Program>>, SearchResultItemAdapter.ProgramItemClickListener {

    private static final String TAG = SearchableRecordingsFragment.class.getSimpleName();

    private static final String QUERY_KEY = "query";

    RecyclerView mRecyclerView;
    SearchResultItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;

    @Override
    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {

        SearchProgramsAsyncTaskLoader loader = new SearchProgramsAsyncTaskLoader( getActivity() );
        loader.setQuery( args.getString( QUERY_KEY ) );

        return loader;
    }

    @Override
    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {

        if( !programs.isEmpty() ) {

            mAdapter = new SearchResultItemAdapter( programs, this );
            mRecyclerView.setAdapter( mAdapter );

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Program>> loader ) {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        View view = inflater.inflate( R.layout.search_result_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mEmpty = (TextView) view.findViewById( R.id.empty );

        Log.v( TAG, "onCreateView : exit" );
        return view;
    }

    public void setQuery( String query ) {
        Log.v( TAG, "setQuery : enter" );

        Bundle args = new Bundle();
        if( null != query ) {
            args.putString( QUERY_KEY, query );
        }

        getLoaderManager().initLoader( 0, args, this );

        Log.v( TAG, "setQuery : exit" );
    }

    public void onProgramItemClicked( View v, Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( getActivity(), RecordingDetailsActivity.class );
        recordingDetails.putExtras(args);
        startActivity( recordingDetails );

    }

    @Override
    public void connected() {

        mRecyclerView.setVisibility( View.VISIBLE );
        mEmpty.setVisibility(View.GONE);

    }

    @Override
    public void notConnected() {

        mRecyclerView.setVisibility( View.GONE );
        mEmpty.setVisibility( View.VISIBLE );

    }

}

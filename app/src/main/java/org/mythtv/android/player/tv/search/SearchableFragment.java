package org.mythtv.android.player.tv.search;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
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
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.tv.loaders.SearchProgramsAsyncTaskLoader;
import org.mythtv.android.player.tv.recordings.RecordingCardPresenter;
import org.mythtv.android.player.tv.recordings.RecordingDetailsActivity;
import org.mythtv.android.player.tv.recordings.RecordingDetailsFragment;

import java.util.List;

/**
 * Created by dmfrey on 3/20/15.
 */
public class SearchableFragment extends SearchFragment implements LoaderManager.LoaderCallbacks<List<Program>>, SearchFragment.SearchResultProvider {

    private static final String TAG = SearchableFragment.class.getSimpleName();

    private static final String QUERY_KEY = "query";
    private static final int SEARCH_DELAY_MS = 1000;

    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler();
    private String mQuery;

    @Override
    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {

        SearchProgramsAsyncTaskLoader loader = new SearchProgramsAsyncTaskLoader( getActivity() );
        loader.setQuery( args.getString( QUERY_KEY ) );

        return loader;
    }

    @Override
    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {

        if( !programs.isEmpty() ) {

            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( new RecordingCardPresenter() );
            for( Program program : programs ) {
                Log.v( TAG, "onLoaderFinished : program=" + program );

                listRowAdapter.add( program );
            }
            HeaderItem header = new HeaderItem( 0, getResources().getString( R.string.search_results ) + " '" + mQuery + "'" );
            mRowsAdapter.add( new ListRow( header, listRowAdapter ) );

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Program>> loader ) { }

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

        Bundle args = new Bundle();
        if( null != query ) {
            args.putString( QUERY_KEY, query );
        }

        getLoaderManager().restartLoader( 0, args, this );


    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof Program ) {

                Program program = (Program) item;
                Log.d( TAG, "Program: " + item.toString() );

                Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
                intent.putExtra( RecordingDetailsFragment.PROGRAM_KEY, program );
                startActivity(intent);

            } else {

                Toast.makeText( getActivity(), ( (String) item ), Toast.LENGTH_SHORT ).show();

            }

        }

    }

}

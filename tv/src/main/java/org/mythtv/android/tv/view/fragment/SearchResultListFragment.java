package org.mythtv.android.tv.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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

import org.joda.time.DateTime;
import org.mythtv.android.R;
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.repository.DatabaseHelper;
import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.tv.presenter.CardPresenter;
import org.mythtv.android.presentation.view.SearchResultListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dmfrey on 2/27/16.
 */
public class SearchResultListFragment extends AbstractBaseSearchFragment implements SearchFragment.SearchResultProvider, SearchResultListView {

    private static final String TAG = SearchResultListFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_SEARCH_TEXT = "org.mythtv.android.ARGUMENT_SEARCH_TEXT";

    private String searchText;

    /**
     * Interface for listening program list events.
     */
    public interface SearchResultListListener {

        void onSearchResultClicked( final SearchResultModel searchResultModel );

    }

    private static final String QUERY_KEY = "query";
    private static final int SEARCH_DELAY_MS = 1000;

    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler();

//    @Inject
//    SearchResultListPresenter searchResultListPresenter;

    private SQLiteDatabase db;

    private SearchResultListListener searchResultListListener;

    public SearchResultListFragment() {
        super();
    }

    public static SearchResultListFragment newInstance(String searchText ) {

        SearchResultListFragment fragment = new SearchResultListFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString( ARGUMENT_KEY_SEARCH_TEXT, searchText );
        fragment.setArguments( argumentsBundle );

        return fragment;
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof SearchResultListListener ) {
            this.searchResultListListener = (SearchResultListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        mRowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
        setSearchResultProvider( this );
        setOnItemViewClickedListener( new ItemViewClickedListener() );

        DatabaseHelper dbOpenHelper = new DatabaseHelper( getActivity() );
        db = dbOpenHelper.getReadableDatabase();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadSearchResultList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

//        this.searchResultListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

//        this.searchResultListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

//        this.searchResultListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

//        this.getComponent( SearchComponent.class ).inject( this );
//        this.searchResultListPresenter.setView( this );

        this.searchText = getArguments().getString( ARGUMENT_KEY_SEARCH_TEXT );

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        Log.d( TAG, "hideRetry : exit" );
    }

    @Override
    public void renderSearchResultList( Collection<SearchResultModel> searchResultModelCollection ) {
        Log.d( TAG, "renderSearchResultList : enter" );

        if( null != searchResultModelCollection ) {
            Log.d( TAG, "renderSearchResultList : searchResultModelCollection is not null" );

            mRowsAdapter.clear();

            ArrayObjectAdapter programRowAdapter = new ArrayObjectAdapter( new CardPresenter() );
            ArrayObjectAdapter videoRowAdapter = new ArrayObjectAdapter( new CardPresenter() );

            for( SearchResultModel searchResultModel : searchResultModelCollection ) {
                Log.d( TAG, "renderSearchResultList : searchResult=" + searchResultModel );

                if( SearchResult.Type.RECORDING.equals( searchResultModel.getType() ) ) {

                    programRowAdapter.add( searchResultModel );

                } else {

                    videoRowAdapter.add( searchResultModel );

                }

            }

            if( programRowAdapter.size() > 0 ) {
                Log.d( TAG, "renderSearchResultList : programRowAdapter has programs" );

                HeaderItem header = new HeaderItem( 0, getResources().getString( R.string.recording ) + " " + getResources().getString( R.string.search_results ) + " '" + this.searchText + "'" );
                mRowsAdapter.add( new ListRow( header, programRowAdapter ) );

            }

            if( videoRowAdapter.size() > 0 ) {
                Log.d( TAG, "renderSearchResultList : videoRowAdapter has videos" );

                HeaderItem header = new HeaderItem( 1, getResources().getString( R.string.video ) + " " + getResources().getString( R.string.search_results ) + " '" + this.searchText + "'" );
                mRowsAdapter.add( new ListRow( header, videoRowAdapter ) );

            }

        }

        Log.d( TAG, "renderSearchResultList : exit" );
    }

    @Override
    public void viewSearchResult( SearchResultModel searchResultModel ) {
        Log.d( TAG, "viewSearchResult : enter" );

        if( null != this.searchResultListListener ) {
            Log.d( TAG, "viewSearchResult : searchResultModel=" + searchResultModel.toString() );

            this.searchResultListListener.onSearchResultClicked( searchResultModel );

        }

        Log.d( TAG, "viewSearchResult : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );


        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all search results.
     */
    private void loadSearchResultList() {
        Log.d( TAG, "loadSearchResultList : enter" );

        Log.d( TAG, "loadSearchResultList : searchText=" + searchText );
//        this.searchResultListPresenter.initialize( searchText );

        renderSearchResultList( search( searchText ) );

        Log.d( TAG, "loadSearchResultList : exit" );
    }

    @Override
    public ObjectAdapter getResultsAdapter() {

        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange( String newQuery ) {
        Log.d( TAG, "onQueryTextChange : enter" );

        this.searchText = newQuery;
//        this.loadSearchResultList();

        renderSearchResultList( search( searchText ) );

        Log.d( TAG, "onQueryTextChange : exit" );
        return true;
    }

    @Override
    public boolean onQueryTextSubmit( String query ) {
        Log.d( TAG, "onQueryTextSubmit : enter" );

        this.searchText = query;
//        this.loadSearchResultList();

        Log.d( TAG, "onQueryTextSubmit : exit" );
        return true;
    }

    private List<SearchResultModel> search( String searchString ) {
        Log.d( TAG, "search : enter" );

        searchString = "*" + searchString + "*";
        searchString = searchString.replaceAll( " ", "*" );
        Log.d( TAG, "search : searchString=" + searchString );

        final String query = searchString;

//        return Observable.create( new Observable.OnSubscribe<List<SearchResultModel>>() {
//
//            @Override
//            public void call( Subscriber<? super List<SearchResultModel>> subscriber ) {
//                Log.d( TAG, "search.call : enter" );

                Cursor cursor;

                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        List<SearchResultModel> searchResultEntities = new ArrayList<>();
                try {

                    builder.setTables( "search_result" );

                    String selection = SearchResultEntity.SQL_SELECT_MATCH;
                    String[] selectionArgs = new String[] { query };

                    SearchResultModel searchResultModel;
                    cursor = builder.query( db, null, selection, selectionArgs, null, null, "START_TIME DESC, TITLE" );
                    while( cursor.moveToNext() ) {

                        searchResultModel = new SearchResultModel();
                        searchResultModel.setStartTime( new DateTime( cursor.getLong( cursor.getColumnIndex( "START_TIME" ) ) ) );
                        searchResultModel.setTitle( cursor.getString( cursor.getColumnIndex( "TITLE" ) ) );
                        searchResultModel.setSubTitle( cursor.getString( cursor.getColumnIndex( "SUB_TITLE" ) ) );
                        searchResultModel.setCategory( cursor.getString( cursor.getColumnIndex( "CATEGORY" ) ) );
                        searchResultModel.setDescription( cursor.getString( cursor.getColumnIndex( "DESCRIPTION" ) ) );
                        searchResultModel.setInetref( cursor.getString( cursor.getColumnIndex( "INETREF" ) ) );
                        searchResultModel.setSeason( cursor.getInt( cursor.getColumnIndex( "SEASON" ) ) );
                        searchResultModel.setEpisode( cursor.getInt( cursor.getColumnIndex( "EPISODE" ) ) );
                        searchResultModel.setChanId( cursor.getInt( cursor.getColumnIndex( "CHAN_ID" ) ) );
                        searchResultModel.setChannelNumber( cursor.getString( cursor.getColumnIndex( "CHAN_NUM" ) ) );
                        searchResultModel.setCallsign( cursor.getString( cursor.getColumnIndex( "CALLSIGN" ) ) );
                        searchResultModel.setCastMembers( cursor.getString( cursor.getColumnIndex( "CAST_MEMBER_NAMES" ) ) );
                        searchResultModel.setCharacters( cursor.getString( cursor.getColumnIndex( "CAST_MEMBER_CHARACTERS" ) ) );
                        searchResultModel.setVideoId( cursor.getInt( cursor.getColumnIndex( "VIDEO_ID" ) ) );
                        searchResultModel.setRating( cursor.getString( cursor.getColumnIndex( "RATING" ) ) );
                        searchResultModel.setStorageGroup( cursor.getString( cursor.getColumnIndex( "STORAGE_GROUP" ) ) );
                        searchResultModel.setContentType( cursor.getString( cursor.getColumnIndex( "CONTENT_TYPE" ) ) );
                        searchResultModel.setFilename( cursor.getString( cursor.getColumnIndex( "FILENAME" ) ) );
                        searchResultModel.setHostname( cursor.getString( cursor.getColumnIndex( "HOSTNAME" ) ) );
                        searchResultModel.setType( SearchResult.Type.valueOf( cursor.getString( cursor.getColumnIndex( "TYPE" ) ) ) );

                        Log.d( TAG, "search.call : searchResultModel=" + searchResultModel.toString() );
                        searchResultEntities.add( searchResultModel );

                    }
                    cursor.close();

//                    subscriber.onNext( searchResultEntities );
//                    subscriber.onCompleted();
//
                } catch( Exception e ) {
                    Log.e( TAG, "search.call : error", e );
//
//                    subscriber.onError( new DatabaseException( e.getCause() ) );
//
                }
//
//                Log.d( TAG, "search.call : exit" );
//            }
//
//        });

        return searchResultEntities;
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof ProgramModel ) {

                ProgramModel programModel = (ProgramModel) item;
                Log.d( TAG, "Program: " + item.toString() );

//                Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
//                intent.putExtra( RecordingDetailsFragment.PROGRAM_KEY, program );
//                startActivity( intent );

            } else {

                VideoMetadataInfoModel videoMetadataInfoModel = (VideoMetadataInfoModel) item;

//                if( "MOVIE".equals( video.getContentType() ) ) {
//                    Log.d( TAG, "Video: " + video.toString() );
//
//                    Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
//                    intent.putExtra( VideoDetailsFragment.VIDEO, video );
//                    startActivity( intent );
//
//                } else {
//                    Log.d( TAG, "Video: " + video.toString() );
//
//                    Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
//                    intent.putExtra( VideoDetailsFragment.VIDEO, video );
//                    startActivity( intent );
//
//                }

            }

        }

    }

}

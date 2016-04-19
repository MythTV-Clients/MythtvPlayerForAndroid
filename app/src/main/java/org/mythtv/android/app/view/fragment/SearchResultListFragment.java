package org.mythtv.android.app.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mythtv.android.R;
import org.mythtv.android.app.internal.di.components.SearchComponent;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.presenter.SearchResultListPresenter;
import org.mythtv.android.presentation.view.SearchResultListView;
import org.mythtv.android.app.view.adapter.SearchResultsAdapter;
import org.mythtv.android.app.view.adapter.SearchResultsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Created by dmfrey on 10/12/15.
 */
public class SearchResultListFragment extends AbstractBaseFragment implements SearchResultListView {

    private static final String TAG = SearchResultListFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_SEARCH_TEXT = "org.mythtv.android.ARGUMENT_SEARCH_TEXT";

    private String searchText;

    /**
     * Interface for listening program list events.
     */
    public interface SearchResultListListener {

        void onSearchResultClicked( final SearchResultModel searchResultModel );

    }

    @Inject
    SearchResultListPresenter searchResultListPresenter;

    @Bind( R.id.rv_search_results )
    RecyclerView rv_searchResults;

    @Bind( R.id.fast_scroller )
    VerticalRecyclerViewFastScroller fastScroller;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private SearchResultsAdapter searchResultsAdapter;

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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_app_search_result_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        fastScroller.setRecyclerView( rv_searchResults );
        rv_searchResults.addOnScrollListener( fastScroller.getOnScrollListener() );

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
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

        this.searchResultListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.searchResultListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.searchResultListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        Log.d( TAG, "initialize : get searchText" );
        this.searchText = getArguments().getString( ARGUMENT_KEY_SEARCH_TEXT );

        Log.d( TAG, "initialize : get component" );
        this.getComponent( SearchComponent.class ).inject( this );

        Log.d( TAG, "initialize : set view" );
        this.searchResultListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        SearchResultsLayoutManager searchResultsLayoutManager = new SearchResultsLayoutManager(getActivity());
        this.rv_searchResults.setLayoutManager(searchResultsLayoutManager);

        this.searchResultsAdapter = new SearchResultsAdapter( getActivity(), new ArrayList<SearchResultModel>() );
        this.searchResultsAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_searchResults.setAdapter( searchResultsAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );

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

            this.searchResultsAdapter.setSearchResultsCollection( searchResultModelCollection );

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

        this.showToastMessage( message, getResources().getString( R.string.retry ), new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                SearchResultListFragment.this.loadSearchResultList();

            }

        });

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
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

        this.searchResultListPresenter.initialize( searchText );

        Log.d( TAG, "loadSearchResultList : exit" );
    }

    private SearchResultsAdapter.OnItemClickListener onItemClickListener = new SearchResultsAdapter.OnItemClickListener() {

        @Override
        public void onSearchResultItemClicked( SearchResultModel searchResultModel ) {

            if( null != SearchResultListFragment.this.searchResultListListener && null != searchResultModel ) {
                Log.d( TAG, "onProgramItemClicked : searchResultModel=" + searchResultModel.toString() );

                SearchResultListFragment.this.searchResultListListener.onSearchResultClicked(searchResultModel );

            }

        }

    };

}

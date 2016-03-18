package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.SearchResultModelDataMapper;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.view.SearchResultListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 10/14/15.
 */
public class SearchResultListPresenter extends DefaultSubscriber<List<SearchResult>> implements Presenter {

    private static final String TAG = SearchResultListPresenter.class.getSimpleName();

    private String searchText;

    private SearchResultListView viewListView;

    private final UseCase getSearchResultListUseCase;
    private final SearchResultModelDataMapper searchResultModelDataMapper;

    @Inject
    public SearchResultListPresenter( @Named( "searchResultList" ) UseCase getSearchResultListUseCase, SearchResultModelDataMapper searchResultModelDataMapper ) {

        this.getSearchResultListUseCase = getSearchResultListUseCase;
        this.searchResultModelDataMapper = searchResultModelDataMapper;

    }

    public void setView( @NonNull SearchResultListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

        this.getSearchResultListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the search result list.
     */
    public void initialize( String searchText ) {

        this.searchText = searchText;
        this.loadSearchResultList();

    }

    /**
     * Loads all search results.
     */
    private void loadSearchResultList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getSearchResultList();

    }

    public void onSearchResultClicked( SearchResultModel searchResultModel ) {
        Log.i( TAG, "onSearchResultClicked : searchResultModel=" + searchResultModel.toString() );

        this.viewListView.viewSearchResult( searchResultModel );

    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage( ErrorBundle errorBundle ) {

        String errorMessage = ErrorMessageFactory.create( this.viewListView.getContext(), errorBundle.getException() );
        this.viewListView.showError( errorMessage );

    }

    private void showSearchResultsCollectionInView( Collection<SearchResult> searchResultsCollection ) {

        final Collection<SearchResultModel> searchResultModelsCollection = this.searchResultModelDataMapper.transform( searchResultsCollection );
        this.viewListView.renderSearchResultList( searchResultModelsCollection );

    }

    private void getSearchResultList() {

        this.getSearchResultListUseCase.execute( new SearchResultListSubscriber() );

    }

    private final class SearchResultListSubscriber extends DefaultSubscriber<List<SearchResult>> {

        @Override
        public void onCompleted() {
            SearchResultListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            SearchResultListPresenter.this.hideViewLoading();
            SearchResultListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            SearchResultListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<SearchResult> searchResults ) {

            SearchResultListPresenter.this.showSearchResultsCollectionInView( searchResults );

        }

    }

}

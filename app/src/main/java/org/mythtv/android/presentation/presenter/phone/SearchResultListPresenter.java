/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.presenter.phone;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultObserver;
import org.mythtv.android.domain.interactor.GetSearchResultList;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.MediaItemListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/14/15.
 */
@PerActivity
public class SearchResultListPresenter extends DefaultObserver<List<MediaItem>> implements Presenter {

    private static final String TAG = SearchResultListPresenter.class.getSimpleName();

    private String searchText;
    private MediaItemListView viewListView;

    private final GetSearchResultList getSearchResultListUseCase;
    private final MediaItemModelDataMapper mediaItemModelDataMapper;

    @Inject
    public SearchResultListPresenter( GetSearchResultList getSearchResultListUseCase, MediaItemModelDataMapper mediaItemModelDataMapper) {

        this.getSearchResultListUseCase = getSearchResultListUseCase;
        this.mediaItemModelDataMapper = mediaItemModelDataMapper;

    }

    public void setView( @NonNull MediaItemListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
        // this method is intentionally left blank
    }

    @Override
    public void pause() {
        // this method is intentionally left blank
    }

    @Override
    public void destroy() {

        this.getSearchResultListUseCase.dispose();

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
    /* private */ void loadSearchResultList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getSearchResultList();

    }

    public void onMediaItemClicked( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.i( TAG, "onMediaItemClicked : mediaItemModel=" + mediaItemModel.toString() );

        this.viewListView.viewMediaItem( mediaItemModel, sharedElement, sharedElementName );

    }

    /* private */ void showViewLoading() {
        this.viewListView.showLoading();
    }

    /* private */ void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    /* private */ void showViewRetry() {
        this.viewListView.showRetry();
    }

    /* private */ void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    /* private */ void showErrorMessage( ErrorBundle errorBundle ) {

        String errorMessage = ErrorMessageFactory.create( this.viewListView.context(), errorBundle.getException() );
        this.viewListView.showError( errorMessage );

    }

    /* private */ void showSearchResultsCollectionInView( Collection<MediaItem> mediaItemsCollection ) {

        final Collection<MediaItemModel> mediaItemModelsCollection = this.mediaItemModelDataMapper.transform( mediaItemsCollection );
        this.viewListView.renderMediaItemList( mediaItemModelsCollection );

    }

    /* private */ void getSearchResultList() {

        this.getSearchResultListUseCase.execute( new SearchResultListObserver(), GetSearchResultList.Params.forSearch( this.searchText ) );

    }

    private final class SearchResultListObserver extends DefaultObserver<List<MediaItem>> {

        @Override
        public void onComplete() {
            SearchResultListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            SearchResultListPresenter.this.hideViewLoading();
            SearchResultListPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            SearchResultListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<MediaItem> mediaItems  ) {

            SearchResultListPresenter.this.showSearchResultsCollectionInView( mediaItems );

        }

    }

}

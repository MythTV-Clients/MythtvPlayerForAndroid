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

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultObserver;
import org.mythtv.android.domain.interactor.GetMediaItemList;
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
 * Created on 9/15/16.
 */
@PerActivity
public class MediaItemListPresenter extends DefaultObserver<List<MediaItem>> implements Presenter {

    private static final String TAG = MediaItemListPresenter.class.getSimpleName();

    private MediaItemListView viewListView;

    private final GetMediaItemList getMediaItemListUseCase;
    private final MediaItemModelDataMapper mediaItemModelDataMapper;

    private Media media;
    private String titleRegEx;
    private boolean tv;

    @Inject
    public MediaItemListPresenter( GetMediaItemList getMediaItemListUseCase, MediaItemModelDataMapper mediaItemModelDataMapper) {

        this.getMediaItemListUseCase = getMediaItemListUseCase;
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
        // this method is intentionally left blank
    }

    /**
     * Initializes the presenter by start retrieving the media item list.
     */
    public void initialize( final Media media, final String titleRegEx, final boolean tv ) {

        this.media = media;
        this.titleRegEx = titleRegEx;
        this.tv = tv;

        this.loadMediaItemList();

    }

    /**
     * Loads media items.
     */
    /* private */ void loadMediaItemList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getMediaItemList();

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

    /* private */ void showMediaItemsCollectionInView( Collection<MediaItem> mediaItemsCollection ) {

        final Collection<MediaItemModel> mediaItemModelsCollection = this.mediaItemModelDataMapper.transform( mediaItemsCollection );
        this.viewListView.renderMediaItemList( mediaItemModelsCollection );

    }

    /* private */ void getMediaItemList() {

        this.getMediaItemListUseCase.execute( new MediaItemListObserver(), GetMediaItemList.Params.forMedia( this.media, this.titleRegEx, this.tv ) );

    }

    private final class MediaItemListObserver extends DefaultObserver<List<MediaItem>> {

        @Override
        public void onComplete() {
            MediaItemListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MediaItemListPresenter.this.hideViewLoading();
            MediaItemListPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            MediaItemListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<MediaItem> mediaItems ) {

            MediaItemListPresenter.this.showMediaItemsCollectionInView( mediaItems );

        }

    }

}

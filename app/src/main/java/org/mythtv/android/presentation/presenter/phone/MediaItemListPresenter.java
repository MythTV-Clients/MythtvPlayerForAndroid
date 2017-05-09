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
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.MediaItemModelMapper;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.MediaItemListView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/15/16.
 */
public class MediaItemListPresenter extends DefaultSubscriber<List<MediaItem>> implements Presenter {

    private static final String TAG = MediaItemListPresenter.class.getSimpleName();

    private MediaItemListView viewListView;

    private final DynamicUseCase getMediaItemListUseCase;
    private final MediaItemModelMapper mediaItemModelMapper;

    @Inject
    public MediaItemListPresenter( @Named( "mediaItemList" ) DynamicUseCase getMediaItemListUseCase, MediaItemModelMapper mediaItemModelMapper ) {

        this.getMediaItemListUseCase = getMediaItemListUseCase;
        this.mediaItemModelMapper = mediaItemModelMapper;

    }

    public void setView( @NonNull MediaItemListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
        Log.v( TAG, "resume : enter" );

        Log.v( TAG, "resume : exit" );
    }

    @Override
    public void pause() {
        Log.v( TAG, "pause : enter" );

        Log.v( TAG, "pause : exit" );
    }

    @Override
    public void destroy() {
        Log.v( TAG, "destroy : enter" );

        Log.v( TAG, "destroy : exit" );
    }

    /**
     * Initializes the presenter by start retrieving the media item list.
     */
    public void initialize( Map<String, Object> parameters ) {

        this.loadMediaItemList( parameters );

    }

    /**
     * Loads media items.
     */
    private void loadMediaItemList( Map<String, Object> parameters ) {

        this.hideViewRetry();
        this.showViewLoading();
        this.getMediaItemList( parameters );

    }

    public void onMediaItemClicked( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.i( TAG, "onMediaItemClicked : mediaItemModel=" + mediaItemModel.toString() );

        this.viewListView.viewMediaItem( mediaItemModel, sharedElement, sharedElementName );

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

    private void showMediaItemsCollectionInView( Collection<MediaItem> mediaItemsCollection ) {

        final Collection<MediaItemModel> mediaItemModelsCollection = this.mediaItemModelMapper.transform( mediaItemsCollection );
        this.viewListView.renderMediaItemList( mediaItemModelsCollection );

    }

    private void getMediaItemList( Map<String, Object> parameters ) {

        this.getMediaItemListUseCase.execute( new MediaItemListSubscriber(), parameters );

    }

    private final class MediaItemListSubscriber extends DefaultSubscriber<List<MediaItem>> {

        @Override
        public void onCompleted() {
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

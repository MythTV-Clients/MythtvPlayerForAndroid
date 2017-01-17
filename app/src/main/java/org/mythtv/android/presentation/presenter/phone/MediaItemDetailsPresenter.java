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

import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.MediaItemModelMapper;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.MediaItemDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
public class MediaItemDetailsPresenter implements Presenter {

    private static final String TAG = MediaItemDetailsPresenter.class.getSimpleName();

    private MediaItemModel mediaItemModel;
    private MediaItemDetailsView viewDetailsView;

    private final UseCase getMediaItemDetailsUseCase;
    private final MediaItemModelMapper mediaItemModelMapper;

    @Inject
    public MediaItemDetailsPresenter( @Named( "mediaItemDetails" ) UseCase getMediaItemDetailsUseCase, MediaItemModelMapper mediaItemModelMapper ) {

        this.getMediaItemDetailsUseCase = getMediaItemDetailsUseCase;
        this.mediaItemModelMapper = mediaItemModelMapper;

    }

    public void setView( @NonNull MediaItemDetailsView view ) {

        this.viewDetailsView = view;

    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {

        this.getMediaItemDetailsUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving media item details.
     */
    public void initialize() {

        this.loadMediaItemDetails();

    }

    /**
     * Loads mediaItem details.
     */
    private void loadMediaItemDetails() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getDetails();

    }

    private void showViewLoading() {

        this.viewDetailsView.showLoading();

    }

    private void hideViewLoading() {

        this.viewDetailsView.hideLoading();

    }

    private void showViewRetry() {

        this.viewDetailsView.showRetry();

    }

    private void hideViewRetry() {

        this.viewDetailsView.hideRetry();

    }

    private void showErrorMessage( ErrorBundle errorBundle ) {

        Log.e( TAG, "showErrorMessage : error", errorBundle.getException() );
        String errorMessage = ErrorMessageFactory.create( this.viewDetailsView.getContext(), errorBundle.getException() );
        this.viewDetailsView.showError( errorMessage );

    }

    private synchronized void updateDetails( MediaItem mediaItem ) {
        Log.d( TAG, "updateDetails : enter" );

        this.mediaItemModel = this.mediaItemModelMapper.transform( mediaItem );

        showDetailsInView();

        Log.d( TAG, "updateDetails : exit" );
    }

    private void showDetailsInView() {
        Log.d( TAG, "showDetailsInView : enter" );

        this.viewDetailsView.renderMediaItem( this.mediaItemModel);

        Log.d( TAG, "showDetailsInView : exit" );
    }

    private void getDetails() {
        Log.d( TAG, "getDetails : enter" );

        this.getMediaItemDetailsUseCase.execute( new MediaItemDetailsSubscriber() );

        Log.d( TAG, "getDetails : exit" );
    }

    private final class MediaItemDetailsSubscriber extends DefaultSubscriber<MediaItem> {

        @Override
        public void onCompleted() {
            MediaItemDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MediaItemDetailsPresenter.this.hideViewLoading();
            MediaItemDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            MediaItemDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( MediaItem mediaItem ) {

            MediaItemDetailsPresenter.this.updateDetails( mediaItem );

        }

    }

}
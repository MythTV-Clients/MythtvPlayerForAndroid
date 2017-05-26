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

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.AddLiveStreamUseCase;
import org.mythtv.android.domain.interactor.DefaultObserver;
import org.mythtv.android.domain.interactor.GetMediaItemDetails;
import org.mythtv.android.domain.interactor.PostUpdatedWatchedStatus;
import org.mythtv.android.domain.interactor.RemoveLiveStreamUseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.MediaItemDetailsView;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
@PerActivity
public class MediaItemDetailsPresenter implements Presenter {

    private static final String TAG = MediaItemDetailsPresenter.class.getSimpleName();

    private Media media;
    private int id;

    private MediaItemModel mediaItemModel;
    private MediaItemDetailsView viewDetailsView;

    private final GetMediaItemDetails getMediaItemDetailsUseCase;
    private final AddLiveStreamUseCase addLiveStreamUseCase;
    private final RemoveLiveStreamUseCase removeLiveStreamUseCase;
    private final PostUpdatedWatchedStatus updateWatchedStatusUseCase;
    private final MediaItemModelDataMapper mediaItemModelDataMapper;

    @Inject
    public MediaItemDetailsPresenter(
            final GetMediaItemDetails getMediaItemDetailsUseCase,
            final AddLiveStreamUseCase addLiveStreamUseCase,
            final RemoveLiveStreamUseCase removeLiveStreamUseCase,
            final PostUpdatedWatchedStatus updateWatchedStatusUseCase,
            MediaItemModelDataMapper mediaItemModelDataMapper ) {

        this.getMediaItemDetailsUseCase = getMediaItemDetailsUseCase;
        this.addLiveStreamUseCase = addLiveStreamUseCase;
        this.removeLiveStreamUseCase = removeLiveStreamUseCase;
        this.updateWatchedStatusUseCase = updateWatchedStatusUseCase;
        this.mediaItemModelDataMapper = mediaItemModelDataMapper;

    }

    public void setView( @NonNull MediaItemDetailsView view ) {

        this.viewDetailsView = view;

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

        this.getMediaItemDetailsUseCase.dispose();
        this.addLiveStreamUseCase.dispose();
        this.removeLiveStreamUseCase.dispose();

    }

    /**
     * Initializes the presenter by start retrieving media item details.
     */
    public void initialize( final Media media, final int id ) {

        this.media = media;
        this.id = id;

        this.loadMediaItemDetails();

    }

    public void addLiveStream() {

        this.addLiveStreamUseCase.execute( new RefreshMediaItemDetailsObserver(), AddLiveStreamUseCase.Params.forMediaItem( this.media, this.id ) );

    }

    public void removeLiveStream() {

        this.removeLiveStreamUseCase.execute( new RefreshMediaItemDetailsObserver(), RemoveLiveStreamUseCase.Params.forMediaItem( this.media, this.id ) );

    }

    public void reload() {

        this.getMediaItemDetailsUseCase.execute( new RefreshMediaItemDetailsObserver(), GetMediaItemDetails.Params.forMediaItem( this.media, this.id ) );

    }

    public void markWatched( boolean watched ) {

        this.updateWatchedStatusUseCase.execute( new MarkWatchedSubscriber(), PostUpdatedWatchedStatus.Params.forMediaItem( this.media, this.id, watched) );

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
        String errorMessage = ErrorMessageFactory.create( this.viewDetailsView.context(), errorBundle.getException() );
        this.viewDetailsView.showError( errorMessage );

    }

    private void updateDetails( MediaItem mediaItem ) {
        Log.d( TAG, "updateDetails : enter" );

        this.mediaItemModel = this.mediaItemModelDataMapper.transform( mediaItem );

        showDetailsInView();

        Log.d( TAG, "updateDetails : exit" );
    }

    private void showDetailsInView() {
        Log.d( TAG, "showDetailsInView : enter" );

        this.viewDetailsView.renderMediaItem( this.mediaItemModel );

        Log.d( TAG, "showDetailsInView : exit" );
    }

    private void refreshDetails( MediaItem mediaItem ) {
        Log.d( TAG, "refreshDetails : enter" );

        this.mediaItemModel = this.mediaItemModelDataMapper.transform( mediaItem );

        refreshDetailsInView();

        Log.d( TAG, "refreshDetails : exit" );
    }

    private void refreshDetailsInView() {
        Log.d( TAG, "refreshDetailsInView : enter" );

        this.viewDetailsView.refreshMediaItem( this.mediaItemModel );

        Log.d( TAG, "refreshDetailsInView : exit" );
    }

    private void getDetails() {
        Log.d( TAG, "getDetails : enter" );

        this.getMediaItemDetailsUseCase.execute( new MediaItemDetailsObserver(), GetMediaItemDetails.Params.forMediaItem( this.media, this.id ) );

        Log.d( TAG, "getDetails : exit" );
    }

    private final class MediaItemDetailsObserver extends DefaultObserver<MediaItem> {

        @Override
        public void onComplete() {
            MediaItemDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MediaItemDetailsPresenter.this.hideViewLoading();
            MediaItemDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            MediaItemDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( MediaItem mediaItem ) {

            MediaItemDetailsPresenter.this.updateDetails( mediaItem );

        }

    }

    private final class RefreshMediaItemDetailsObserver extends DefaultObserver<MediaItem> {

        @Override
        public void onComplete() {
            MediaItemDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MediaItemDetailsPresenter.this.hideViewLoading();
            MediaItemDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            MediaItemDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( MediaItem mediaItem ) {

            MediaItemDetailsPresenter.this.refreshDetails( mediaItem );

        }

    }

    private final class MarkWatchedSubscriber extends DefaultObserver<MediaItem> {

        @Override
        public void onComplete() {
            MediaItemDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MediaItemDetailsPresenter.this.hideViewLoading();
            MediaItemDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            MediaItemDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( MediaItem mediaItem ) {

            MediaItemDetailsPresenter.this.refreshDetails( mediaItem );

        }

    }

}
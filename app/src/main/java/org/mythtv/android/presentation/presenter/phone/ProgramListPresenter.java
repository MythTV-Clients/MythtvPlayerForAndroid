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
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.MediaItemModelMapper;
import org.mythtv.android.presentation.mapper.ProgramModelDataMapper;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.ProgramListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramListPresenter extends DefaultSubscriber<List<MediaItem>> implements Presenter {

    private static final String TAG = ProgramListPresenter.class.getSimpleName();

    private MediaItemListView viewListView;

    private final UseCase getProgramListUseCase;
    private final MediaItemModelMapper mediaItemModelMapper;

    @Inject
    public ProgramListPresenter( @Named( "programList" ) UseCase getProgramListUseCase, MediaItemModelMapper mediaItemModelMapper ) {

        this.getProgramListUseCase = getProgramListUseCase;
        this.mediaItemModelMapper = mediaItemModelMapper;

    }

    public void setView( @NonNull MediaItemListView view ) {
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

        this.getProgramListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the program list.
     */
    public void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.loadProgramList();

        Log.d( TAG, "initialize : exit" );
    }

    /**
     * Loads all programs.
     */
    private void loadProgramList() {
        Log.d( TAG, "loadProgramList : enter" );

        this.hideViewRetry();
        this.showViewLoading();
        this.getProgramList();

        Log.d( TAG, "loadProgramList : exit" );
    }

    public void onMediaItemClicked( MediaItemModel mediaItemModel ) {
        Log.i( TAG, "onMediaItemClicked : mediaItemModel=" + mediaItemModel.toString() );

        this.viewListView.viewMediaItem( mediaItemModel );

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
        Log.d( TAG, "showMediaItemsCollectionInView : enter" );

        final Collection<MediaItemModel> mediaItemModelsCollection = this.mediaItemModelMapper.transform( mediaItemsCollection );
        this.viewListView.renderMediaItemList( mediaItemModelsCollection );

        Log.d( TAG, "showMediaItemsCollectionInView : exit" );
    }

    private void getProgramList() {
        Log.d( TAG, "getProgramList : enter" );

        this.getProgramListUseCase.execute( new MediaItemListSubscriber() );

        Log.d( TAG, "getProgramList : exit" );
    }

    private final class MediaItemListSubscriber extends DefaultSubscriber<List<MediaItem>> {

        @Override
        public void onCompleted() {
            Log.d( TAG, "MediaItemListSubscriber.onCompleted : enter" );

            ProgramListPresenter.this.hideViewLoading();

            Log.d( TAG, "MediaItemListSubscriber.onCompleted : exit" );
        }

        @Override
        public void onError( Throwable e ) {
            Log.d( TAG, "MediaItemListSubscriber.onError : enter" );
            Log.e( TAG, "MediaItemListSubscriber.onError : error", e );

            ProgramListPresenter.this.hideViewLoading();
            ProgramListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramListPresenter.this.showViewRetry();

            Log.d( TAG, "MediaItemListSubscriber.onError : exit" );
        }

        @Override
        public void onNext( List<MediaItem> mediaItems ) {
            Log.d( TAG, "MediaItemListSubscriber.onNext : enter" );

            ProgramListPresenter.this.showMediaItemsCollectionInView( mediaItems );

            Log.d( TAG, "MediaItemListSubscriber.onNext : exit" );
        }

    }

}

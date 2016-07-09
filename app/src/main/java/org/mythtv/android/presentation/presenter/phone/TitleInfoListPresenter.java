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

import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.TitleInfoModelDataMapper;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.view.TitleInfoListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class TitleInfoListPresenter extends DefaultSubscriber<List<TitleInfo>> implements Presenter {

    private static final String TAG = TitleInfoListPresenter.class.getSimpleName();

    private TitleInfoListView viewListView;

    private final UseCase getTitleInfoListUseCase;
    private final TitleInfoModelDataMapper titleInfoModelDataMapper;

    @Inject
    public TitleInfoListPresenter( @Named( "titleInfoList" ) UseCase getTitleInfoListUseCase, TitleInfoModelDataMapper titleInfoModelDataMapper ) {
        Log.d( TAG, "construct : enter" );

        this.getTitleInfoListUseCase = getTitleInfoListUseCase;
        this.titleInfoModelDataMapper = titleInfoModelDataMapper;

        Log.d( TAG, "construct : exit" );
    }

    public void setView( @NonNull TitleInfoListView view ) {
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

        this.getTitleInfoListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the titleInfo list.
     */
    public void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.loadTitleInfoList();

        Log.d( TAG, "initialize : enter" );
    }

    /**
     * Loads all titleInfos.
     */
    private void loadTitleInfoList() {
        Log.d( TAG, "loadTitleInfoList : enter" );

        this.hideViewRetry();
        this.showViewLoading();
        this.getTitleInfoList();

        Log.d( TAG, "loadTitleInfoList : exit" );
    }

    public void onTitleInfoClicked( TitleInfoModel titleInfoModel ) {
        Log.d( TAG, "onTitleInfoClicked : enter" );

        this.viewListView.viewTitleInfo( titleInfoModel );

        Log.d( TAG, "onTitleInfoClicked : exit" );
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
        Log.d( TAG, "showErrorMessage : enter" );

        String errorMessage = ErrorMessageFactory.create( this.viewListView.getContext(), errorBundle.getException() );
        Log.e( TAG, "showErrorMessage : errorMessage=" + errorMessage, errorBundle.getException() );

        this.viewListView.showError( errorMessage );

        Log.d( TAG, "showErrorMessage : exit" );
    }

    private void showTitleInfosCollectionInView( Collection<TitleInfo> titleInfosCollection ) {
        Log.d( TAG, "showTitleInfosCollectionInView : enter" );

        final Collection<TitleInfoModel> titleInfoModelsCollection = this.titleInfoModelDataMapper.transform( titleInfosCollection );
        this.viewListView.renderTitleInfoList( titleInfoModelsCollection );

        Log.d( TAG, "showTitleInfosCollectionInView : exit" );
    }

    private void getTitleInfoList() {
        Log.d( TAG, "getTitleInfoList : enter" );

        this.getTitleInfoListUseCase.execute( new TitleInfoListSubscriber() );

        Log.d( TAG, "getTitleInfoList : exit" );
    }

    private final class TitleInfoListSubscriber extends DefaultSubscriber<List<TitleInfo>> {

        @Override
        public void onCompleted() {
            TitleInfoListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            TitleInfoListPresenter.this.hideViewLoading();
            TitleInfoListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            TitleInfoListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<TitleInfo> titleInfos ) {

            TitleInfoListPresenter.this.showTitleInfosCollectionInView( titleInfos );

        }

    }

}

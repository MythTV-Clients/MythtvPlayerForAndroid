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

import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.SeriesModelMapper;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.view.SeriesListView;

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
public class SeriesListPresenter extends DefaultSubscriber<List<Series>> implements Presenter {

    private static final String TAG = SeriesListPresenter.class.getSimpleName();

    private SeriesListView viewListView;

    private final DynamicUseCase getSeriesListUseCase;
    private final SeriesModelMapper seriesModelMapper;

    @Inject
    public SeriesListPresenter( @Named( "seriesList" ) DynamicUseCase getSeriesListUseCase, SeriesModelMapper seriesModelMapper ) {

        this.getSeriesListUseCase = getSeriesListUseCase;
        this.seriesModelMapper = seriesModelMapper;

    }

    public void setView( @NonNull SeriesListView view ) {
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

        this.getSeriesListUseCase.unsubscribe();

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
        this.getSeriesList( parameters );

    }

    public void onSeriesClicked( SeriesModel seriesModel ) {
        Log.i( TAG, "onSeriesClicked : seriesModel=" + seriesModel.toString() );

        this.viewListView.viewSeries( seriesModel );

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

        Log.e( TAG, "showErrorMessage : error", errorBundle.getException() );
        String errorMessage = ErrorMessageFactory.create( this.viewListView.getContext(), errorBundle.getException() );
        this.viewListView.showError( errorMessage );

    }

    private void showSeriesCollectionInView( Collection<Series> seriesCollection ) {

        final Collection<SeriesModel> seriesModelsCollection = this.seriesModelMapper.transform( seriesCollection );
        this.viewListView.renderSeriesList( seriesModelsCollection );

    }

    private void getSeriesList( Map<String, Object> parameters ) {

        this.getSeriesListUseCase.execute( new SeriesListSubscriber(), parameters );

    }

    private final class SeriesListSubscriber extends DefaultSubscriber<List<Series>> {

        @Override
        public void onCompleted() {
            SeriesListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            SeriesListPresenter.this.hideViewLoading();
            SeriesListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            SeriesListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Series> seriesList ) {

            SeriesListPresenter.this.showSeriesCollectionInView( seriesList );

        }

    }

}

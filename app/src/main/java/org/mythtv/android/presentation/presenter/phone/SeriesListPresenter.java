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
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultObserver;
import org.mythtv.android.domain.interactor.GetSeriesList;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.mapper.SeriesModelDataMapper;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.view.SeriesListView;

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
public class SeriesListPresenter extends DefaultObserver<List<Series>> implements Presenter {

    private static final String TAG = SeriesListPresenter.class.getSimpleName();

    private SeriesListView viewListView;

    private final GetSeriesList getSeriesListUseCase;
    private final SeriesModelDataMapper seriesModelDataMapper;

    private Media media;

    @Inject
    public SeriesListPresenter( GetSeriesList getSeriesListUseCase, SeriesModelDataMapper seriesModelDataMapper) {

        this.getSeriesListUseCase = getSeriesListUseCase;
        this.seriesModelDataMapper = seriesModelDataMapper;

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

    }

    /**
     * Initializes the presenter by start retrieving the media item list.
     */
    public void initialize( final Media media ) {

        this.media = media;

        this.loadMediaItemList();

    }

    /**
     * Loads media items.
     */
    private void loadMediaItemList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getSeriesList();

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
        String errorMessage = ErrorMessageFactory.create( this.viewListView.context(), errorBundle.getException() );
        this.viewListView.showError( errorMessage );

    }

    private void showSeriesCollectionInView( Collection<Series> seriesCollection ) {

        final Collection<SeriesModel> seriesModelsCollection = this.seriesModelDataMapper.transform( seriesCollection );
        this.viewListView.renderSeriesList( seriesModelsCollection );

    }

    private void getSeriesList() {

        this.getSeriesListUseCase.execute( new SeriesListObserver(), GetSeriesList.Params.forMedia( this.media ) );

    }

    private final class SeriesListObserver extends DefaultObserver<List<Series>> {

        @Override
        public void onComplete() {
            SeriesListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            SeriesListPresenter.this.hideViewLoading();
            SeriesListPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            SeriesListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Series> seriesList ) {

            SeriesListPresenter.this.showSeriesCollectionInView( seriesList );

        }

    }

}

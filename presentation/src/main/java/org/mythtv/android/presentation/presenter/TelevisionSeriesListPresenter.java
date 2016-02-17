package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.VideoMetadataInfoModelDataMapper;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.VideoListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 11/13/15.
 */
public class TelevisionSeriesListPresenter extends DefaultSubscriber<List<VideoMetadataInfo>> implements Presenter {

    private static final String TAG = TelevisionSeriesListPresenter.class.getSimpleName();

    private String series;

    private VideoListView viewListView;

    private final UseCase getTelevisionSeriesListUseCase;
    private final VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;

    @Inject
    public TelevisionSeriesListPresenter( @Named( "televisionSeriesList" ) UseCase getTelevisionSeriesListUseCase, VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper ) {

        this.getTelevisionSeriesListUseCase = getTelevisionSeriesListUseCase;
        this.videoMetadataInfoModelDataMapper = videoMetadataInfoModelDataMapper;

    }

    public void setView( @NonNull VideoListView view ) {
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

        this.getTelevisionSeriesListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the videoMetadataInfo list.
     */
    public void initialize( String series ) {
        Log.d( TAG, "initialize : enter" );

        this.series = series;
        this.loadTelevisionList();

        Log.d( TAG, "initialize : exit" );
    }

    /**
     * Loads all videoMetadataInfos.
     */
    private void loadTelevisionList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getTelevisionList();

    }

    public void onVideoClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {

        this.viewListView.viewVideo( videoMetadataInfoModel );

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

    private void showTelevisionCollectionInView( Collection<VideoMetadataInfo> videoMetadataInfosCollection ) {

        final Collection<VideoMetadataInfoModel> videoMetadataInfoModelsCollection = this.videoMetadataInfoModelDataMapper.transform( videoMetadataInfosCollection );
        this.viewListView.renderVideoList( videoMetadataInfoModelsCollection);

    }

    private void getTelevisionList() {

        this.getTelevisionSeriesListUseCase.execute( new MovieListSubscriber() );

    }

    private final class MovieListSubscriber extends DefaultSubscriber<List<VideoMetadataInfo>> {

        @Override
        public void onCompleted() {
            TelevisionSeriesListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            TelevisionSeriesListPresenter.this.hideViewLoading();
            TelevisionSeriesListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            TelevisionSeriesListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<VideoMetadataInfo> videoMetadataInfos ) {

            TelevisionSeriesListPresenter.this.showTelevisionCollectionInView( videoMetadataInfos );

        }

    }

}

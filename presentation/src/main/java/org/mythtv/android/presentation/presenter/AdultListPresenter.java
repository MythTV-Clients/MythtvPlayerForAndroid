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
import org.mythtv.android.presentation.view.VideoMetadataInfoListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 11/13/15.
 */
public class AdultListPresenter extends DefaultSubscriber<List<VideoMetadataInfo>> implements Presenter {

    private static final String TAG = AdultListPresenter.class.getSimpleName();

    private VideoMetadataInfoListView viewListView;

    private final UseCase getAdultListUseCase;
    private final VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;

    @Inject
    public AdultListPresenter( @Named( "adultList" ) UseCase getAdultListUseCase, VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper ) {

        this.getAdultListUseCase = getAdultListUseCase;
        this.videoMetadataInfoModelDataMapper = videoMetadataInfoModelDataMapper;

    }

    public void setView( @NonNull VideoMetadataInfoListView view ) {
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

        this.getAdultListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the videoMetadataInfo list.
     */
    public void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.loadAdultList();

        Log.d( TAG, "initialize : exit" );
    }

    /**
     * Loads all videoMetadataInfos.
     */
    private void loadAdultList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getAdultList();

    }

    public void onVideoClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {

        this.viewListView.viewVideoMetadataInfo( videoMetadataInfoModel );

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

    private void showAdultCollectionInView( Collection<VideoMetadataInfo> videoMetadataInfosCollection ) {

        final Collection<VideoMetadataInfoModel> videoMetadataInfoModelsCollection = this.videoMetadataInfoModelDataMapper.transform( videoMetadataInfosCollection );
        this.viewListView.renderVideoMetadataInfoList( videoMetadataInfoModelsCollection);

    }

    private void getAdultList() {

        this.getAdultListUseCase.execute( new AdultListSubscriber() );

    }

    private final class AdultListSubscriber extends DefaultSubscriber<List<VideoMetadataInfo>> {

        @Override
        public void onCompleted() {
            AdultListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            AdultListPresenter.this.hideViewLoading();
            AdultListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            AdultListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<VideoMetadataInfo> videoMetadataInfos ) {

            AdultListPresenter.this.showAdultCollectionInView( videoMetadataInfos );

        }

    }

}

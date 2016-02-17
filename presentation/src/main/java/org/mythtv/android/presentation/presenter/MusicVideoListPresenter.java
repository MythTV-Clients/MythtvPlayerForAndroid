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
public class MusicVideoListPresenter extends DefaultSubscriber<List<VideoMetadataInfo>> implements Presenter {

    private static final String TAG = MusicVideoListPresenter.class.getSimpleName();

    private VideoListView viewListView;

    private final UseCase getMusicVideoListUseCase;
    private final VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;

    @Inject
    public MusicVideoListPresenter( @Named( "musicVideoList" ) UseCase getMusicVideoListUseCase, VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper ) {

        this.getMusicVideoListUseCase = getMusicVideoListUseCase;
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

        this.getMusicVideoListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the videoMetadataInfo list.
     */
    public void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.loadMusicVideoList();

        Log.d( TAG, "initialize : exit" );
    }

    /**
     * Loads all videoMetadataInfos.
     */
    private void loadMusicVideoList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getMusicVideoList();

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

    private void showMusicVideoCollectionInView( Collection<VideoMetadataInfo> videoMetadataInfosCollection ) {

        final Collection<VideoMetadataInfoModel> videoMetadataInfoModelsCollection = this.videoMetadataInfoModelDataMapper.transform( videoMetadataInfosCollection );
        this.viewListView.renderVideoList( videoMetadataInfoModelsCollection);

    }

    private void getMusicVideoList() {

        this.getMusicVideoListUseCase.execute( new MusicVideoListSubscriber() );

    }

    private final class MusicVideoListSubscriber extends DefaultSubscriber<List<VideoMetadataInfo>> {

        @Override
        public void onCompleted() {
            MusicVideoListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            MusicVideoListPresenter.this.hideViewLoading();
            MusicVideoListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            MusicVideoListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<VideoMetadataInfo> videoMetadataInfos ) {

            MusicVideoListPresenter.this.showMusicVideoCollectionInView( videoMetadataInfos );

        }

    }

}

package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.VideoDetailsView;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.LiveStreamInfoModelDataMapper;
import org.mythtv.android.presentation.mapper.ProgramModelDataMapper;
import org.mythtv.android.presentation.mapper.VideoMetadataInfoModelDataMapper;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.ProgramDetailsView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 11/25/15.
 */
public class VideoDetailsPresenter implements Presenter {

    private static final String TAG = VideoDetailsPresenter.class.getSimpleName();

    /** id used to retrieve program details */
    private int id;

    private VideoDetailsView viewDetailsView;

    private final UseCase getVideoDetailsUseCase;
    private final UseCase addLiveStreamDetailsUseCase;
    private final UseCase getLiveStreamsListUseCase;
    private final VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;
    private final LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper;

    @Inject
    public VideoDetailsPresenter(
            @Named( "videoDetails" ) UseCase getVideoDetailsUseCase,
            @Named( "addLiveStream" ) UseCase addLiveStreamDetailsUseCase,
            @Named( "getLiveStreams" ) UseCase getLiveStreamsListUseCase,
            VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper,
            LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper ) {

        this.getVideoDetailsUseCase = getVideoDetailsUseCase;
        this.addLiveStreamDetailsUseCase = addLiveStreamDetailsUseCase;
        this.getLiveStreamsListUseCase = getLiveStreamsListUseCase;
        this.videoMetadataInfoModelDataMapper = videoMetadataInfoModelDataMapper;
        this.liveStreamInfoModelDataMapper = liveStreamInfoModelDataMapper;

    }

    public void setView( @NonNull VideoDetailsView view ) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {

        this.getLiveStreamsListUseCase.unsubscribe();

    }

    @Override
    public void destroy() {

        this.getVideoDetailsUseCase.unsubscribe();
        this.addLiveStreamDetailsUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving video details.
     */
    public void initialize( int id ) {

        this.id = id;
        this.loadVideoDetails();

    }

    /**
     * Add a new Live Stream
     */
    public void addLiveStream() {
        Log.d( TAG, "addLiveStream : enter" );

        this.hideViewRetry();
        this.showViewLoading();
        this.addLiveStreamDetailsUseCase.execute( new LiveStreamInfoDetailsSubscriber() );

        Log.d( TAG, "addLiveStream : exit" );
    }

    /**
     * Loads video details.
     */
    private void loadVideoDetails() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getVideoDetails();
        this.getLiveStreamDetails();

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

        String errorMessage = ErrorMessageFactory.create( this.viewDetailsView.getContext(), errorBundle.getException() );
        this.viewDetailsView.showError( errorMessage );

    }

    private void showVideoDetailsInView( VideoMetadataInfo videoMetadataInfo ) {
        Log.d( TAG, "showVideoDetailsInView : videoMetadataInfo=" + videoMetadataInfo.toString() );

        final VideoMetadataInfoModel videoMetadataInfoModel = this.videoMetadataInfoModelDataMapper.transform( videoMetadataInfo );
        this.viewDetailsView.renderVideo( videoMetadataInfoModel );

    }

    private void showLiveStreamDetailsInView( LiveStreamInfo liveStreamInfo ) {
        Log.d( TAG, "showLiveStreamDetailsInView : liveStreamInfo=" + liveStreamInfo.toString() );

        final LiveStreamInfoModel liveStreamInfoModel = this.liveStreamInfoModelDataMapper.transform( liveStreamInfo );
        this.viewDetailsView.updateLiveStream( liveStreamInfoModel );

    }

    private void getVideoDetails() {
        Log.d( TAG, "getVideoDetails : enter" );

        this.getVideoDetailsUseCase.execute( new VideoDetailsSubscriber() );

        Log.d( TAG, "getVideoDetails : exit" );
    }

    private void getLiveStreamDetails() {
        Log.d( TAG, "getLiveStreamDetails : enter" );

        this.getLiveStreamsListUseCase.execute( new LiveStreamInfosListSubscriber() );

        Log.d( TAG, "getLiveStreamDetails : enter" );
    }

    private final class VideoDetailsSubscriber extends DefaultSubscriber<VideoMetadataInfo> {

        @Override
        public void onCompleted() {
            VideoDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            VideoDetailsPresenter.this.hideViewLoading();
            VideoDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            VideoDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( VideoMetadataInfo videoMetadataInfo ) {

            VideoDetailsPresenter.this.showVideoDetailsInView( videoMetadataInfo );

        }

    }

    private final class LiveStreamInfoDetailsSubscriber extends DefaultSubscriber<LiveStreamInfo> {

        @Override
        public void onCompleted() {
            VideoDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            VideoDetailsPresenter.this.hideViewLoading();
            VideoDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            VideoDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( LiveStreamInfo liveStreamInfo ) {

            VideoDetailsPresenter.this.showLiveStreamDetailsInView(liveStreamInfo);

        }

    }

    private final class LiveStreamInfosListSubscriber extends DefaultSubscriber<List<LiveStreamInfo>> {

        @Override
        public void onCompleted() {
            VideoDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            VideoDetailsPresenter.this.hideViewLoading();
            VideoDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            VideoDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<LiveStreamInfo> liveStreamInfos ) {

            if( null != liveStreamInfos && !liveStreamInfos.isEmpty() ) {

                VideoDetailsPresenter.this.showLiveStreamDetailsInView( liveStreamInfos.get( 0 ) );

                if( liveStreamInfos.get( 0 ).getPercentComplete() == 100 ) {

                    VideoDetailsPresenter.this.getLiveStreamsListUseCase.unsubscribe();

                }

            }

        }

    }

}

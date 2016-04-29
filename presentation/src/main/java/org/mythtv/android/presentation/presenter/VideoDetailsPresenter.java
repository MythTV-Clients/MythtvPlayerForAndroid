package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.VideoDetailsView;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.LiveStreamInfoModelDataMapper;
import org.mythtv.android.presentation.mapper.VideoMetadataInfoModelDataMapper;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 11/25/15.
 */
public class VideoDetailsPresenter implements Presenter {

    private static final String TAG = VideoDetailsPresenter.class.getSimpleName();

    private enum StreamState{ COMPLETE, AVAILABLE, ADDED, REMOVED, NOT_SET }
    private StreamState currentStreamState = StreamState.NOT_SET;
    private Thread streamUpdates;

    private VideoMetadataInfoModel videoMetadataInfoModel;
    private LiveStreamInfoModel liveStreamInfoModel;
    private VideoDetailsView viewDetailsView;

    private final UseCase getVideoDetailsUseCase;
    private final DynamicUseCase addLiveStreamDetailsUseCase;
    private final DynamicUseCase removeLiveStreamDetailsUseCase;
    private final DynamicUseCase getLiveStreamsListUseCase;
    private final DynamicUseCase getLiveStreamDetailsUseCase;
    private final DynamicUseCase postUpdateVideoWatchedStatusUseCase;
    private final VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;
    private final LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper;

    @Inject
    public VideoDetailsPresenter(
            @Named( "videoDetails" ) UseCase getVideoDetailsUseCase,
            @Named( "addLiveStream" ) DynamicUseCase addLiveStreamDetailsUseCase,
            @Named( "removeLiveStream" ) DynamicUseCase removeLiveStreamDetailsUseCase,
            @Named( "getLiveStreams" ) DynamicUseCase getLiveStreamsListUseCase,
            @Named( "getLiveStream" ) DynamicUseCase getLiveStreamDetailsUseCase,
            @Named( "updateVideoWatchedStatus" ) DynamicUseCase postUpdateVideoWatchedStatusUseCase,
            VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper,
            LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper
    ) {

        this.getVideoDetailsUseCase = getVideoDetailsUseCase;
        this.addLiveStreamDetailsUseCase = addLiveStreamDetailsUseCase;
        this.removeLiveStreamDetailsUseCase = removeLiveStreamDetailsUseCase;
        this.getLiveStreamsListUseCase = getLiveStreamsListUseCase;
        this.getLiveStreamDetailsUseCase = getLiveStreamDetailsUseCase;
        this.postUpdateVideoWatchedStatusUseCase = postUpdateVideoWatchedStatusUseCase;
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

        if( null != this.streamUpdates ) {

            this.streamUpdates.interrupt();
            this.streamUpdates = null;

        }

    }

    @Override
    public void destroy() {

        this.addLiveStreamDetailsUseCase.unsubscribe();
        this.removeLiveStreamDetailsUseCase.unsubscribe();
        this.getLiveStreamsListUseCase.unsubscribe();
        this.getLiveStreamDetailsUseCase.unsubscribe();
        this.getVideoDetailsUseCase.unsubscribe();
        this.postUpdateVideoWatchedStatusUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving video details.
     */
    public void initialize() {

        this.loadVideoDetails();

    }

    /**
     * Update the watched status for a video
     */
    public void updateWatchedStatus() {
        Log.d( TAG, "updateWatchedStatus : enter" );

        boolean watchedStatus = videoMetadataInfoModel.isWatched();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "VIDEO_ID", this.videoMetadataInfoModel.getId() );
        parameters.put( "WATCHED", !watchedStatus );

        this.hideViewRetry();
        this.showViewLoading();
        this.postUpdateVideoWatchedStatusUseCase.execute( new WatchedStatusSubscriber(), parameters );

        Log.d( TAG, "updateWatchedStatus : enter" );
    }

    /**
     * Update Live Stream, either add new stream or delete existing one
     */
    public void updateHlsStream() {
        Log.d( TAG, "updateHlsStream : enter" );

        this.hideViewRetry();
        this.showViewLoading();

        if( null == this.liveStreamInfoModel ) {

            addLiveStream();

        } else {

            removeLiveStream();

        }

        Log.d( TAG, "updateHlsStream : exit" );
    }

    /**
     * Loads video details.
     */
    private void loadVideoDetails() {

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

        String errorMessage = ErrorMessageFactory.create( this.viewDetailsView.getContext(), errorBundle.getException() );
        this.viewDetailsView.showError( errorMessage );

    }

    private synchronized void updateDetails( VideoMetadataInfo video ) {
        Log.d( TAG, "updateDetails : enter" );

        this.videoMetadataInfoModel = this.videoMetadataInfoModelDataMapper.transform( video );
        this.videoMetadataInfoModel.setLiveStreamInfo( this.liveStreamInfoModel );
        this.getLiveStreamListDetails();

        showDetailsInView();

        Log.d( TAG, "updateDetails : exit" );
    }

    private synchronized void updateLiveStreamDetails( StreamState streamState, LiveStreamInfo liveStreamInfo ) {
        Log.d( TAG, "updateLiveStreamDetails : enter" );

        this.liveStreamInfoModel = null;

        switch( streamState ) {

            case AVAILABLE :
                Log.d( TAG, "updateLiveStreamDetails : stream is available" );

                if( null != liveStreamInfo ) {

                    this.currentStreamState = streamState;

                    this.liveStreamInfoModel = this.liveStreamInfoModelDataMapper.transform( liveStreamInfo );

                    if( this.currentStreamState.equals( StreamState.NOT_SET ) ) {

                        notifyLiveStreamAvailable();

                    }

                }

                break;

            case COMPLETE :
                Log.d( TAG, "updateLiveStreamDetails : stream is complete" );

                if( null != liveStreamInfo ) {

                    this.currentStreamState = streamState;

                    this.liveStreamInfoModel = this.liveStreamInfoModelDataMapper.transform( liveStreamInfo );
                    notifyLiveStreamComplete();

                    this.streamUpdates = null;

                }

                break;

            case ADDED :
                Log.d( TAG, "updateLiveStreamDetails : stream added" );

                if( null != liveStreamInfo ) {

                    this.currentStreamState = streamState;

                    this.liveStreamInfoModel = this.liveStreamInfoModelDataMapper.transform( liveStreamInfo );
                    notifyLiveStreamAdded();

                }

                break;

            case REMOVED :
                Log.d( TAG, "updateLiveStreamDetails : stream removed" );

                this.currentStreamState = StreamState.NOT_SET;

                notifyLiveStreamRemoved();

                break;
        }

        this.videoMetadataInfoModel.setLiveStreamInfo( this.liveStreamInfoModel );
        showLiveStreamDetailsInView();

        if( null != this.liveStreamInfoModel && ( this.currentStreamState.equals( StreamState.AVAILABLE ) || this.currentStreamState.equals( StreamState.ADDED ) ) ) {
            Log.d( TAG, "updateLiveStreamDetails : stream not fully processed check again" );

            getLiveStreamDetails();

        }

        Log.d( TAG, "updateLiveStreamDetails : exit" );
    }

    private void showDetailsInView() {
        Log.d( TAG, "showDetailsInView : enter" );

        this.viewDetailsView.renderVideo( this.videoMetadataInfoModel );

        Log.d( TAG, "showDetailsInView : exit" );
    }

    private void showLiveStreamDetailsInView() {
        Log.d( TAG, "showLiveStreamDetailsInView : enter" );

        this.viewDetailsView.updateLiveStream( this.videoMetadataInfoModel );

        Log.d( TAG, "showLiveStreamDetailsInView : exit" );
    }

    private void getDetails() {
        Log.d( TAG, "getDetails : enter" );

        this.getVideoDetailsUseCase.execute( new VideoDetailsSubscriber() );

        Log.d( TAG, "getDetails : exit" );
    }

    private void getLiveStreamListDetails() {
        Log.d( TAG, "getLiveStreamListDetails : enter" );

        Map<String, String> parameters = Collections.singletonMap( "FILE_NAME", videoMetadataInfoModel.getFileName() );

        this.getLiveStreamsListUseCase.execute( new LiveStreamListSubscriber(), parameters );

        Log.d( TAG, "getLiveStreamListDetails : exit" );
    }

    private void getLiveStreamDetails() {
        Log.d( TAG, "getLiveStreamDetails : enter" );

        final DynamicUseCase useCase = this.getLiveStreamDetailsUseCase;
        streamUpdates = new Thread( new Runnable() {

            @Override
            public void run() {
                Log.d( TAG, "getLiveStreamDetails.runnable : enter" );

                try {

                    Thread.sleep( 5000 );

                    Map<String, Integer> parameters = Collections.singletonMap( "LIVE_STREAM_ID", liveStreamInfoModel.getId() );

                    useCase.execute( new LiveStreamSubscriber(), parameters );

                } catch( InterruptedException e ) {
                    Log.e( TAG, "getLiveStreamDetails.runnable : error", e );
                }

                Log.d( TAG, "getLiveStreamDetails.runnable : exit" );
            }

        });
        streamUpdates.start();

        Log.d( TAG, "getLiveStreamDetails : exit" );
    }

    private void addLiveStream() {
        Log.d( TAG, "addLiveStream : enter" );

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "FILE_NAME", videoMetadataInfoModel.getFileName() );
        parameters.put( "HOST_NAME", videoMetadataInfoModel.getHostName() );

        this.addLiveStreamDetailsUseCase.execute( new LiveStreamAddedSubscriber(), parameters );

        Log.d( TAG, "addLiveStream : exit" );
    }

    private void removeLiveStream() {
        Log.d( TAG, "removeLiveStream : enter" );

        if( null != streamUpdates ) {

            streamUpdates.interrupt();
            streamUpdates = null;

        }

        Map<String, Integer> parameters = Collections.singletonMap( "LIVE_STREAM_ID", liveStreamInfoModel.getId() );

        this.removeLiveStreamDetailsUseCase.execute( new LiveStreamRemovedSubscriber(), parameters );

        Log.d( TAG, "removeLiveStream : exit" );
    }

    private void notifyLiveStreamComplete() {
        Log.d( TAG, "notifyLiveStreamComplete : enter" );

        notifyInView( "Stream processing complete!" );

        Log.d( TAG, "notifyLiveStreamComplete : exit" );
    }

    private void notifyLiveStreamAvailable() {
        Log.d( TAG, "notifyLiveStreamAvailable : enter" );

        notifyInView( "Stream available!" );

        Log.d( TAG, "notifyLiveStreamAvailable : exit" );
    }

    private void notifyLiveStreamAdded() {
        Log.d( TAG, "notifyLiveStreamAdded : enter" );

        notifyInView( "Stream added!" );

        Log.d( TAG, "notifyLiveStreamAdded : exit" );
    }

    private void notifyLiveStreamRemoved() {
        Log.d( TAG, "notifyLiveStreamRemoved : enter" );

        notifyInView( "Stream removed!" );

        Log.d( TAG, "notifyLiveStreamRemoved : exit" );
    }

    private void notifyLiveStreamFailure( StreamState streamState ) {
        Log.d( TAG, "notifyLiveStreamRemoved : enter" );

        String message = "";

        switch( streamState ) {

            case ADDED :
                message = "Error adding stream";
                break;

            case REMOVED :
                message = "Error removing stream";
                break;
        }

        if( !"".equals( message ) ) {

            notifyInView( message );

        }

        Log.d( TAG, "notifyLiveStreamRemoved : exit" );
    }

    private void notifyWatchedStatus() {
        Log.d( TAG, "notifyWatchedStatus : enter" );

        notifyInView( "Video Watched Status updated!" );

        Log.d( TAG, "notifyWatchedStatus : exit" );
    }

    private void notifyInView( String message ) {
        Log.d( TAG, "notifyInView : enter" );

        this.viewDetailsView.showMessage( message );

        Log.d( TAG, "notifyInView : exit" );
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

            VideoDetailsPresenter.this.updateDetails( videoMetadataInfo );

        }

    }

    private final class LiveStreamListSubscriber extends DefaultSubscriber<List<LiveStreamInfo>> {

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

                LiveStreamInfo liveStreamInfo = liveStreamInfos.get( 0 );
                StreamState streamState = StreamState.AVAILABLE;
                if( liveStreamInfo.getPercentComplete() == 100 ) {
                    streamState = StreamState.COMPLETE;
                }
                VideoDetailsPresenter.this.updateLiveStreamDetails( streamState, liveStreamInfo );

            } else {

                VideoDetailsPresenter.this.updateLiveStreamDetails( StreamState.AVAILABLE, null );

            }

        }

    }

    private final class LiveStreamSubscriber extends DefaultSubscriber<LiveStreamInfo> {

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

            if( null != liveStreamInfo ) {

                StreamState streamState = StreamState.AVAILABLE;
                if( liveStreamInfo.getPercentComplete() == 100 ) {
                    streamState = StreamState.COMPLETE;
                }
                VideoDetailsPresenter.this.updateLiveStreamDetails( streamState, liveStreamInfo );

            } else {

                VideoDetailsPresenter.this.updateLiveStreamDetails( StreamState.AVAILABLE, null );

            }

        }

    }

    private final class LiveStreamAddedSubscriber extends DefaultSubscriber<LiveStreamInfo> {

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

            if( null != liveStreamInfo ) {

                VideoDetailsPresenter.this.updateLiveStreamDetails( StreamState.ADDED, liveStreamInfo );

            } else {

                VideoDetailsPresenter.this.notifyLiveStreamFailure( StreamState.ADDED );

            }

        }

    }

    private final class LiveStreamRemovedSubscriber extends DefaultSubscriber<Boolean> {

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
        public void onNext( Boolean status ) {

            if( status ) {

                VideoDetailsPresenter.this.updateLiveStreamDetails( StreamState.REMOVED, null );

            } else {

                VideoDetailsPresenter.this.notifyLiveStreamFailure( StreamState.REMOVED );

            }

        }

    }

    private final class WatchedStatusSubscriber extends DefaultSubscriber<Boolean> {

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
        public void onNext( Boolean status ) {

            if( status ) {

                VideoDetailsPresenter.this.notifyWatchedStatus();
                VideoDetailsPresenter.this.getDetails();

            }

        }

    }

}

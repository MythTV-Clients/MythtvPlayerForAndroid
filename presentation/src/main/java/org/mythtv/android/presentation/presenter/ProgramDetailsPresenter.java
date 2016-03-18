package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.LiveStreamInfoModelDataMapper;
import org.mythtv.android.presentation.mapper.ProgramModelDataMapper;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.ProgramDetailsView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramDetailsPresenter implements Presenter {

    private static final String TAG = ProgramDetailsPresenter.class.getSimpleName();

    private ProgramDetailsView viewDetailsView;

    private final UseCase getProgramDetailsUseCase;
    private final UseCase addLiveStreamDetailsUseCase;
    private final UseCase getLiveStreamsListUseCase;
    private final ProgramModelDataMapper programModelDataMapper;
    private final LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper;

    @Inject
    public ProgramDetailsPresenter(
            @Named( "programDetails" ) UseCase getProgramDetailsUseCase,
            @Named( "addLiveStream" ) UseCase addLiveStreamDetailsUseCase,
            @Named( "getLiveStreams" ) UseCase getLiveStreamsListUseCase,
            ProgramModelDataMapper programModelDataMapper,
            LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper ) {

        this.getProgramDetailsUseCase = getProgramDetailsUseCase;
        this.addLiveStreamDetailsUseCase = addLiveStreamDetailsUseCase;
        this.getLiveStreamsListUseCase = getLiveStreamsListUseCase;
        this.programModelDataMapper = programModelDataMapper;
        this.liveStreamInfoModelDataMapper = liveStreamInfoModelDataMapper;

    }

    public void setView( @NonNull ProgramDetailsView view ) {
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

        this.getProgramDetailsUseCase.unsubscribe();
        this.addLiveStreamDetailsUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving program details.
     */
    public void initialize( int chanId, DateTime startTime ) {

        /* id used to retrieve program details */
        int chanId1 = chanId;
        DateTime startTime1 = startTime;
        this.loadProgramDetails();

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
     * Loads program details.
     */
    private void loadProgramDetails() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getProgramDetails();
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

    private void showProgramDetailsInView( Program program ) {
        Log.d( TAG, "showProgramDetailsInView : program=" + program.toString() );

        final ProgramModel programModel = this.programModelDataMapper.transform( program );
        this.viewDetailsView.renderProgram( programModel );

    }

    private void showLiveStreamDetailsInView( LiveStreamInfo liveStreamInfo ) {
        Log.d( TAG, "showLiveStreamDetailsInView : liveStreamInfo=" + liveStreamInfo.toString() );

        final LiveStreamInfoModel liveStreamInfoModel = this.liveStreamInfoModelDataMapper.transform( liveStreamInfo );
        this.viewDetailsView.updateLiveStream( liveStreamInfoModel );

    }

    private void getProgramDetails() {
        Log.d( TAG, "getProgramDetails : enter" );

        this.getProgramDetailsUseCase.execute( new ProgramDetailsSubscriber() );

        Log.d( TAG, "getProgramDetails : exit" );
    }

    private void getLiveStreamDetails() {
        Log.d( TAG, "getLiveStreamDetails : enter" );

        this.getLiveStreamsListUseCase.execute( new LiveStreamInfosListSubscriber() );

        Log.d( TAG, "getLiveStreamDetails : enter" );
    }

    private final class ProgramDetailsSubscriber extends DefaultSubscriber<Program> {

        @Override
        public void onCompleted() {
            ProgramDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            ProgramDetailsPresenter.this.hideViewLoading();
            ProgramDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( Program program ) {

            ProgramDetailsPresenter.this.showProgramDetailsInView( program );

        }

    }

    private final class LiveStreamInfoDetailsSubscriber extends DefaultSubscriber<LiveStreamInfo> {

        @Override
        public void onCompleted() {
            ProgramDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            ProgramDetailsPresenter.this.hideViewLoading();
            ProgramDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( LiveStreamInfo liveStreamInfo ) {

            ProgramDetailsPresenter.this.showLiveStreamDetailsInView(liveStreamInfo);

        }

    }

    private final class LiveStreamInfosListSubscriber extends DefaultSubscriber<List<LiveStreamInfo>> {

        @Override
        public void onCompleted() {
            ProgramDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            ProgramDetailsPresenter.this.hideViewLoading();
            ProgramDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramDetailsPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<LiveStreamInfo> liveStreamInfos ) {

            if( null != liveStreamInfos && !liveStreamInfos.isEmpty() ) {

                ProgramDetailsPresenter.this.showLiveStreamDetailsInView( liveStreamInfos.get( 0 ) );

                if( liveStreamInfos.get( 0 ).getPercentComplete() == 100 ) {

                    ProgramDetailsPresenter.this.getLiveStreamsListUseCase.unsubscribe();

                }

            }

        }

    }

}

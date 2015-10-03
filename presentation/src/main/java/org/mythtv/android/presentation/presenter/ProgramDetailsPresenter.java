package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.ProgramModelDataMapper;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.ProgramDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramDetailsPresenter implements Presenter {

    private static final String TAG = ProgramDetailsPresenter.class.getCanonicalName();

    /** id used to retrieve program details */
    private int chanId;
    private DateTime startTime;

    private ProgramDetailsView viewDetailsView;

    private final UseCase getProgramDetailsUseCase;
    private final ProgramModelDataMapper programModelDataMapper;

    @Inject
    public ProgramDetailsPresenter( @Named( "programDetails" ) UseCase getProgramDetailsUseCase, ProgramModelDataMapper programModelDataMapper ) {

        this.getProgramDetailsUseCase = getProgramDetailsUseCase;
        this.programModelDataMapper = programModelDataMapper;

    }

    public void setView( @NonNull ProgramDetailsView view ) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        this.getProgramDetailsUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving program details.
     */
    public void initialize( int chanId, DateTime startTime ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.loadProgramDetails();

    }

    /**
     * Loads program details.
     */
    private void loadProgramDetails() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getProgramDetails();

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
        Log.i( TAG, "showProgramDetailsInView : program=" + program.toString() );

        final ProgramModel programModel = this.programModelDataMapper.transform( program );
        this.viewDetailsView.renderProgram( programModel );

    }

    private void getProgramDetails() {

        this.getProgramDetailsUseCase.execute( new ProgramDetailsSubscriber() );

    }

    private final class ProgramDetailsSubscriber extends DefaultSubscriber<Program> {

        @Override public void onCompleted() {
            ProgramDetailsPresenter.this.hideViewLoading();
        }

        @Override public void onError( Throwable e ) {

            ProgramDetailsPresenter.this.hideViewLoading();
            ProgramDetailsPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramDetailsPresenter.this.showViewRetry();

        }

        @Override public void onNext( Program program ) {

            ProgramDetailsPresenter.this.showProgramDetailsInView( program );

        }

    }

}

package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.ProgramModelDataMapper;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.ProgramListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class RecentListPresenter extends DefaultSubscriber<List<Program>> implements Presenter {

    private static final String TAG = RecentListPresenter.class.getSimpleName();

    private ProgramListView viewListView;

    private final UseCase getProgramListUseCase;
    private final ProgramModelDataMapper programModelDataMapper;

    @Inject
    public RecentListPresenter( @Named( "recentProgramList" ) UseCase getProgramListUseCase, ProgramModelDataMapper programModelDataMapper ) {

        this.getProgramListUseCase = getProgramListUseCase;
        this.programModelDataMapper = programModelDataMapper;

    }

    public void setView( @NonNull ProgramListView view ) {
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

        this.getProgramListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the recent program list.
     */
    public void initialize() {

        this.loadRecentProgramList();

    }

    /**
     * Loads recent programs.
     */
    private void loadRecentProgramList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getProgramList();

    }

    public void onProgramClicked( ProgramModel programModel ) {
        Log.i( TAG, "onProgramClicked : programModel=" + programModel.toString() );

        this.viewListView.viewProgram( programModel );

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

    private void showProgramsCollectionInView( Collection<Program> programsCollection ) {

        final Collection<ProgramModel> programModelsCollection = this.programModelDataMapper.transform(programsCollection);
        this.viewListView.renderProgramList(programModelsCollection);

    }

    private void getProgramList() {

        this.getProgramListUseCase.execute( new ProgramListSubscriber() );

    }

    private final class ProgramListSubscriber extends DefaultSubscriber<List<Program>> {

        @Override
        public void onCompleted() {
            RecentListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            RecentListPresenter.this.hideViewLoading();
            RecentListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            RecentListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Program> programs ) {

            RecentListPresenter.this.showProgramsCollectionInView( programs );

        }

    }

}

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
public class UpcomingListPresenter extends DefaultSubscriber<List<Program>> implements Presenter {

    private static final String TAG = UpcomingListPresenter.class.getSimpleName();

    private ProgramListView viewListView;

    private final UseCase getUpcomingProgramListUseCase;
    private final ProgramModelDataMapper programModelDataMapper;

    @Inject
    public UpcomingListPresenter( @Named( "upcomingProgramsList" ) UseCase getUpcomingProgramListUseCase, ProgramModelDataMapper programModelDataMapper ) {

        this.getUpcomingProgramListUseCase = getUpcomingProgramListUseCase;
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

        this.getUpcomingProgramListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the upcoming program list.
     */
    public void initialize() {

        this.loadUpcomingProgramList();

    }

    /**
     * Loads upcoming programs.
     */
    private void loadUpcomingProgramList() {

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

        final Collection<ProgramModel> programModelsCollection = this.programModelDataMapper.transform( programsCollection );
        this.viewListView.renderProgramList( programModelsCollection );

    }

    private void getProgramList() {

        this.getUpcomingProgramListUseCase.execute( new ProgramListSubscriber() );

    }

    private final class ProgramListSubscriber extends DefaultSubscriber<List<Program>> {

        @Override
        public void onCompleted() {
            UpcomingListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            UpcomingListPresenter.this.hideViewLoading();
            UpcomingListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            UpcomingListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Program> programs ) {

            UpcomingListPresenter.this.showProgramsCollectionInView( programs );

        }

    }

}

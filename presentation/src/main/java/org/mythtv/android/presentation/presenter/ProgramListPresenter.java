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
public class ProgramListPresenter extends DefaultSubscriber<List<Program>> implements Presenter {

    private static final String TAG = ProgramListPresenter.class.getSimpleName();

    private ProgramListView viewListView;

    private final UseCase getProgramListUseCase;
    private final ProgramModelDataMapper programModelDataMapper;

    @Inject
    public ProgramListPresenter( @Named( "programList" ) UseCase getProgramListUseCase, ProgramModelDataMapper programModelDataMapper ) {

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
     * Initializes the presenter by start retrieving the program list.
     */
    public void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.loadProgramList();

        Log.d( TAG, "initialize : exit" );
    }

    /**
     * Loads all programs.
     */
    private void loadProgramList() {
        Log.d( TAG, "loadProgramList : enter" );

        this.hideViewRetry();
        this.showViewLoading();
        this.getProgramList();

        Log.d( TAG, "loadProgramList : exit" );
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
        Log.d( TAG, "showProgramsCollectionInView : enter" );

        final Collection<ProgramModel> programModelsCollection = this.programModelDataMapper.transform( programsCollection );
        this.viewListView.renderProgramList( programModelsCollection );

        Log.d( TAG, "showProgramsCollectionInView : exit" );
    }

    private void getProgramList() {
        Log.d( TAG, "getProgramList : enter" );

        this.getProgramListUseCase.execute( new ProgramListSubscriber() );

        Log.d( TAG, "getProgramList : exit" );
    }

    private final class ProgramListSubscriber extends DefaultSubscriber<List<Program>> {

        @Override
        public void onCompleted() {
            Log.d( TAG, "ProgramListSubscriber.onCompleted : enter" );

            ProgramListPresenter.this.hideViewLoading();

            Log.d( TAG, "ProgramListSubscriber.onCompleted : exit" );
        }

        @Override
        public void onError( Throwable e ) {
            Log.d( TAG, "ProgramListSubscriber.onError : enter" );
            Log.e( TAG, "ProgramListSubscriber.onError : error", e );

            ProgramListPresenter.this.hideViewLoading();
            ProgramListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            ProgramListPresenter.this.showViewRetry();

            Log.d( TAG, "ProgramListSubscriber.onError : exit" );
        }

        @Override
        public void onNext( List<Program> programs ) {
            Log.d( TAG, "ProgramListSubscriber.onNext : enter" );

            ProgramListPresenter.this.showProgramsCollectionInView( programs );

            Log.d( TAG, "ProgramListSubscriber.onNext : exit" );
        }

    }

}

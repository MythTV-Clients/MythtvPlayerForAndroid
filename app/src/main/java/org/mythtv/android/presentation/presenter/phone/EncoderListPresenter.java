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

import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.EncoderModelDataMapper;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.view.EncoderListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
@SuppressWarnings( "PMD" )
public class EncoderListPresenter extends DefaultSubscriber<List<Encoder>> implements Presenter {

    private static final String TAG = EncoderListPresenter.class.getSimpleName();

    private EncoderListView viewListView;

    private final UseCase getEncoderListUseCase;
    private final EncoderModelDataMapper encoderModelDataMapper;

    @Inject
    public EncoderListPresenter( @Named( "encoderList" ) UseCase getEncoderListUseCase, EncoderModelDataMapper encoderModelDataMapper ) {

        this.getEncoderListUseCase = getEncoderListUseCase;
        this.encoderModelDataMapper = encoderModelDataMapper;

    }

    public void setView( @NonNull EncoderListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
        Log.v( TAG, "resume : enter" );

        Log.v( TAG, "resume : exit" );
    }

    @Override
    public void pause() {
        Log.v( TAG, "pause : enter" );

        Log.v( TAG, "pause : exit" );
    }

    @Override
    public void destroy() {

        this.getEncoderListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the encoder list.
     */
    public void initialize() {

        this.loadEncoderList();

    }

    /**
     * Loads all encoders.
     */
    private void loadEncoderList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getEncoderList();

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

    private void showEncodersCollectionInView( Collection<Encoder> encodersCollection ) {

        final Collection<EncoderModel> encoderModelsCollection = this.encoderModelDataMapper.transform( encodersCollection );
        this.viewListView.renderEncoderList( encoderModelsCollection );

    }

    private void getEncoderList() {

        this.getEncoderListUseCase.execute( new EncoderListSubscriber() );

    }

    private final class EncoderListSubscriber extends DefaultSubscriber<List<Encoder>> {

        @Override
        public void onCompleted() {
            EncoderListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            EncoderListPresenter.this.hideViewLoading();
            EncoderListPresenter.this.showErrorMessage( new DefaultErrorBundle( new Exception( e ) ) );
            EncoderListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Encoder> encoders ) {

            EncoderListPresenter.this.showEncodersCollectionInView( encoders );

        }

    }

}

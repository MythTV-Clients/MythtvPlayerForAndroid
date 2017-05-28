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

import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultObserver;
import org.mythtv.android.domain.interactor.GetEncoderList;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.mapper.EncoderModelDataMapper;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.view.EncoderListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
@PerActivity
public class EncoderListPresenter extends DefaultObserver<List<Encoder>> implements Presenter {

    private EncoderListView viewListView;

    private final GetEncoderList getEncoderListUseCase;
    private final EncoderModelDataMapper encoderModelDataMapper;

    @Inject
    public EncoderListPresenter( GetEncoderList getEncoderListUseCase, EncoderModelDataMapper encoderModelDataMapper ) {

        this.getEncoderListUseCase = getEncoderListUseCase;
        this.encoderModelDataMapper = encoderModelDataMapper;

    }

    public void setView( @NonNull EncoderListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
        // this method is intentionally left blank
    }

    @Override
    public void pause() {
        // this method is intentionally left blank
    }

    @Override
    public void destroy() {
        // this method is intentionally left blank
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

    /* private */ void showViewLoading() {
        this.viewListView.showLoading();
    }

    /* private */ void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    /* private */ void showViewRetry() {
        this.viewListView.showRetry();
    }

    /* private */ void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    /* private */ void showErrorMessage(ErrorBundle errorBundle) {

        String errorMessage = ErrorMessageFactory.create( this.viewListView.context(), errorBundle.getException() );
        this.viewListView.showError( errorMessage );

    }

    /* private */ void showEncodersCollectionInView(Collection<Encoder> encodersCollection) {

        final Collection<EncoderModel> encoderModelsCollection = this.encoderModelDataMapper.transform( encodersCollection );
        this.viewListView.renderEncoderList( encoderModelsCollection );

    }

    /* private */ void getEncoderList() {

        this.getEncoderListUseCase.execute( new EncoderListObserver(), null );

    }

    private final class EncoderListObserver extends DefaultObserver<List<Encoder>> {

        @Override
        public void onComplete() {
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

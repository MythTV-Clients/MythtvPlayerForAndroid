package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;

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
 * Created by dmfrey on 8/31/15.
 */
public class EncoderListPresenter extends DefaultSubscriber<List<Encoder>> implements Presenter {

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

    }

    @Override
    public void pause() {

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

    public void onEncoderClicked( EncoderModel encoderModel ) {

//        this.viewListView.viewEncoder( encoderModel );

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
            EncoderListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            EncoderListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<Encoder> encoders ) {

            EncoderListPresenter.this.showEncodersCollectionInView( encoders );

        }

    }

}

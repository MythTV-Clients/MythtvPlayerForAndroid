package org.mythtv.android.presentation.presenter;

import android.support.annotation.NonNull;

import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.domain.exception.DefaultErrorBundle;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.mapper.TitleInfoModelDataMapper;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.view.TitleInfoListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dmfrey on 8/31/15.
 */
public class TitleInfoListPresenter extends DefaultSubscriber<List<TitleInfo>> implements Presenter {

    private TitleInfoListView viewListView;

    private final UseCase getTitleInfoListUseCase;
    private final TitleInfoModelDataMapper titleInfoModelDataMapper;

    @Inject
    public TitleInfoListPresenter( @Named( "titleInfoList" ) UseCase getTitleInfoListUseCase, TitleInfoModelDataMapper titleInfoModelDataMapper ) {

        this.getTitleInfoListUseCase = getTitleInfoListUseCase;
        this.titleInfoModelDataMapper = titleInfoModelDataMapper;

    }

    public void setView( @NonNull TitleInfoListView view ) {
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

        this.getTitleInfoListUseCase.unsubscribe();

    }

    /**
     * Initializes the presenter by start retrieving the titleInfo list.
     */
    public void initialize() {

        this.loadTitleInfoList();

    }

    /**
     * Loads all titleInfos.
     */
    private void loadTitleInfoList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getTitleInfoList();

    }

    public void onTitleInfoClicked( TitleInfoModel titleInfoModel ) {

        this.viewListView.viewTitleInfo( titleInfoModel );

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

    private void showTitleInfosCollectionInView( Collection<TitleInfo> titleInfosCollection ) {

        final Collection<TitleInfoModel> titleInfoModelsCollection = this.titleInfoModelDataMapper.transform( titleInfosCollection );
        this.viewListView.renderTitleInfoList( titleInfoModelsCollection );

    }

    private void getTitleInfoList() {

        this.getTitleInfoListUseCase.execute( new TitleInfoListSubscriber() );

    }

    private final class TitleInfoListSubscriber extends DefaultSubscriber<List<TitleInfo>> {

        @Override
        public void onCompleted() {
            TitleInfoListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError( Throwable e ) {

            TitleInfoListPresenter.this.hideViewLoading();
            TitleInfoListPresenter.this.showErrorMessage( new DefaultErrorBundle( (Exception) e ) );
            TitleInfoListPresenter.this.showViewRetry();

        }

        @Override
        public void onNext( List<TitleInfo> titleInfos ) {

            TitleInfoListPresenter.this.showTitleInfosCollectionInView( titleInfos );

        }

    }

}

package org.mythtv.android.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.interactor.GetMediaItemList;
import org.mythtv.android.domain.interactor.GetSeriesList;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.mapper.SeriesModelDataMapper;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.presenter.phone.SeriesListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.SeriesListView;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 5/25/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class SeriesListPresenterTest {

    private SeriesListPresenter presenter;

    @Mock
    private Context mockContext;

    @Mock
    private SeriesListView mockListView;

    @Mock
    private GetSeriesList mockUseCase;

    @Mock
    private SeriesModelDataMapper mockMapper;

    @Before
    public void setup() {

        presenter = new SeriesListPresenter( mockUseCase, mockMapper );
        presenter.setView( mockListView );

    }

    @Test
    public void testPresenterInitialize() {

        given( mockListView.context() ).willReturn( mockContext );

        presenter.initialize( Media.PROGRAM );

        verify( mockListView ).hideRetry();
        verify( mockListView ).showLoading();
        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( GetSeriesList.Params.class ) );

    }

}

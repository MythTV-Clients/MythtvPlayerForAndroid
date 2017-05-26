package org.mythtv.android.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.domain.interactor.GetSearchResultList;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.presenter.phone.SearchResultListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 5/25/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class SearchResultListPresenterTest {

    private SearchResultListPresenter presenter;

    @Mock
    private Context mockContext;

    @Mock
    private MediaItemListView mockListView;

    @Mock
    private GetSearchResultList mockUseCase;

    @Mock
    private MediaItemModelDataMapper mockMapper;

    @Before
    public void setup() {

        presenter = new SearchResultListPresenter( mockUseCase, mockMapper );
        presenter.setView( mockListView );

    }

    @Test
    public void testPresenterInitialize() {

        given( mockListView.context() ).willReturn( mockContext );

        presenter.initialize( "test" );

        verify( mockListView ).hideRetry();
        verify( mockListView ).showLoading();
        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( GetSearchResultList.Params.class ) );

    }

}

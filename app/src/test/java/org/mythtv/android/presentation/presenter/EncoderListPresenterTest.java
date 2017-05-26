package org.mythtv.android.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.domain.interactor.GetEncoderList;
import org.mythtv.android.presentation.mapper.EncoderModelDataMapper;
import org.mythtv.android.presentation.presenter.phone.EncoderListPresenter;
import org.mythtv.android.presentation.view.EncoderListView;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 5/25/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class EncoderListPresenterTest {

    private EncoderListPresenter presenter;

    @Mock
    private Context mockContext;

    @Mock
    private EncoderListView mockListView;

    @Mock
    private GetEncoderList mockUseCase;

    @Mock
    private EncoderModelDataMapper mockMapper;

    @Before
    public void setup() {

        presenter = new EncoderListPresenter( mockUseCase, mockMapper );
        presenter.setView( mockListView );

    }

    @Test
    public void testPresenterInitialize() {

        given( mockListView.context() ).willReturn( mockContext );

        presenter.initialize();

        verify( mockListView ).hideRetry();
        verify( mockListView ).showLoading();
        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( Void.class ) );

    }

}

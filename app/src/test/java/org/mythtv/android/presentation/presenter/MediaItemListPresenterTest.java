package org.mythtv.android.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.interactor.GetEncoderList;
import org.mythtv.android.domain.interactor.GetMediaItemList;
import org.mythtv.android.presentation.mapper.EncoderModelDataMapper;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.presenter.phone.EncoderListPresenter;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.EncoderListView;
import org.mythtv.android.presentation.view.MediaItemListView;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 5/25/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class MediaItemListPresenterTest {

    private MediaItemListPresenter presenter;

    @Mock
    private Context mockContext;

    @Mock
    private MediaItemListView mockListView;

    @Mock
    private GetMediaItemList mockUseCase;

    @Mock
    private MediaItemModelDataMapper mockMapper;

    @Before
    public void setup() {

        presenter = new MediaItemListPresenter( mockUseCase, mockMapper );
        presenter.setView( mockListView );

    }

    @Test
    public void testPresenterInitialize() {

        given( mockListView.context() ).willReturn( mockContext );

        presenter.initialize( Media.PROGRAM, null, false );

        verify( mockListView ).hideRetry();
        verify( mockListView ).showLoading();
        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( GetMediaItemList.Params.class ) );

    }

}

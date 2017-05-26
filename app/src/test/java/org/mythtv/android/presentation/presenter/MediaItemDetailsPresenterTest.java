package org.mythtv.android.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.interactor.AddLiveStreamUseCase;
import org.mythtv.android.domain.interactor.GetMediaItemDetails;
import org.mythtv.android.domain.interactor.GetMediaItemList;
import org.mythtv.android.domain.interactor.PostUpdatedWatchedStatus;
import org.mythtv.android.domain.interactor.RemoveLiveStreamUseCase;
import org.mythtv.android.presentation.mapper.MediaItemModelDataMapper;
import org.mythtv.android.presentation.presenter.phone.MediaItemDetailsPresenter;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.MediaItemDetailsView;
import org.mythtv.android.presentation.view.MediaItemListView;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 5/25/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class MediaItemDetailsPresenterTest {

    private MediaItemDetailsPresenter presenter;

    @Mock
    private Context mockContext;

    @Mock
    private MediaItemDetailsView mockDetailsView;

    @Mock
    private GetMediaItemDetails mockUseCase;

    @Mock
    private AddLiveStreamUseCase mockAddLiveStreamUseCase;

    @Mock
    private RemoveLiveStreamUseCase mockRemoveLiveStreamUseCase;

    @Mock
    private PostUpdatedWatchedStatus mockPostUpdateWatchedUseCase;

    @Mock
    private MediaItemModelDataMapper mockMapper;

    @Before
    public void setup() {

        presenter = new MediaItemDetailsPresenter( mockUseCase, mockAddLiveStreamUseCase, mockRemoveLiveStreamUseCase, mockPostUpdateWatchedUseCase, mockMapper );
        presenter.setView( mockDetailsView );

    }

    @Test
    public void testPresenterInitialize() {

        given( mockDetailsView.context() ).willReturn( mockContext );

        presenter.initialize( Media.PROGRAM, 999 );

        verify( mockDetailsView ).hideRetry();
        verify( mockDetailsView ).showLoading();
        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( GetMediaItemDetails.Params.class ) );

    }

    @Test
    public void testPresenterReload() {

        given( mockDetailsView.context() ).willReturn( mockContext );

        presenter.reload();

        verify( mockUseCase ).execute( any( DisposableObserver.class ), any( GetMediaItemDetails.Params.class ) );

    }

    @Test
    public void testPresenterAddLiveStream() {

        given( mockDetailsView.context() ).willReturn( mockContext );

        presenter.addLiveStream();

        verify( mockAddLiveStreamUseCase ).execute( any( DisposableObserver.class ), any( AddLiveStreamUseCase.Params.class ) );

    }

    @Test
    public void testPresenterRemoveLiveStream() {

        given( mockDetailsView.context() ).willReturn( mockContext );

        presenter.removeLiveStream();

        verify( mockRemoveLiveStreamUseCase ).execute( any( DisposableObserver.class ), any( RemoveLiveStreamUseCase.Params.class ) );

    }

    @Test
    public void testPresenterMarkWatched() {

        given( mockDetailsView.context() ).willReturn( mockContext );

        presenter.markWatched( true );

        verify( mockPostUpdateWatchedUseCase ).execute( any( DisposableObserver.class ), any( PostUpdatedWatchedStatus.Params.class ) );

    }

}

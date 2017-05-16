package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetMediaItemDetailsTest extends TestData {

    @Mock private MediaItemRepository mockMediaItemRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void whenGetMediaItemDetails() {

        given( mockMediaItemRepository.mediaItem( any( Media.class ), anyInt() ) ).willReturn( setupMediaItem() );

        GetMediaItemDetails useCase = new GetMediaItemDetails( FAKE_MEDIA, FAKE_RECORDED_ID, mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<MediaItem> observable = useCase.buildUseCaseObservable();
        TestSubscriber<MediaItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isInstanceOf( MediaItem.class );

        verify( mockMediaItemRepository ).mediaItem( FAKE_MEDIA, FAKE_RECORDED_ID );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    private Observable<MediaItem> setupMediaItem() {

        return Observable.just( createFakeMediaItem() );
    }

}

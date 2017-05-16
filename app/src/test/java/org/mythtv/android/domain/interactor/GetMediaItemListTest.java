package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mythtv.android.domain.interactor.GetMediaItemList.MEDIA_KEY;
import static org.mythtv.android.domain.interactor.GetMediaItemList.TITLE_REGEX_KEY;
import static org.mythtv.android.domain.interactor.GetMediaItemList.TV_KEY;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetMediaItemListTest extends TestData {

    @Mock private MediaItemRepository mockMediaItemRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test( expected = UnsupportedOperationException.class )
    public void givenGetMediaItemList_whenNoParameters_verifyUnsupportedOperationExceptionThrown() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable();

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenGetMediaItemList_whenNullParameters_verifyIllegalArgumentExceptionThrown() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( null );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenGetMediaItemList_whenEmptyParameters_verifyIllegalArgumentExceptionThrown() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( Collections.emptyMap() );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaProgram_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.PROGRAM );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaProgramAndTitle_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.PROGRAM );
        parameters.put( TITLE_REGEX_KEY, FAKE_TITLE );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaRecent_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.RECENT );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaUpcoming_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.UPCOMING );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.VIDEO );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaHomeVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.HOMEVIDEO );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaMusicVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.MUSICVIDEO );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMovie_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.MOVIE );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenTelevisionWithTvKey_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.TELEVISION );
        parameters.put( TV_KEY, Boolean.TRUE );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenTelevisionWithoutTvKey_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.TELEVISION );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<Series>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    private Observable<List<MediaItem>> setupMediaItems() {

        return Observable.just( Collections.singletonList( createFakeMediaItem() ) );
    }

    private Observable<List<Series>> setupSeries() {

        return Observable.just( Collections.singletonList( createFakeSeries() ) );
    }

}

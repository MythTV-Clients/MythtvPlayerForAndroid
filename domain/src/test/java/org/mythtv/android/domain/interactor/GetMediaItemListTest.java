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
import org.mythtv.android.domain.interactor.GetMediaItemList.Params;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

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

    @Test( expected = NullPointerException.class )
    public void givenGetMediaItemList_whenNullParameters_verifyIllegalArgumentExceptionThrown() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( null );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaProgram_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.PROGRAM ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaProgramAndTitle_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.PROGRAM, FAKE_TITLE ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaRecent_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.RECENT ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaUpcoming_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.UPCOMING ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.VIDEO ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaHomeVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.HOMEVIDEO ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMediaMusicVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.MUSICVIDEO ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenMovie_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.MOVIE ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetMediaItemList_whenTelevisionWithTvKey_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItems() );

        GetMediaItemList useCase = new GetMediaItemList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( Params.forMedia( Media.TELEVISION, Boolean.TRUE ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).mediaItems( any( Media.class ), anyString() );
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

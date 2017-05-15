package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.repository.datasource.MediaItemDataStore;
import org.mythtv.android.data.repository.datasource.MediaItemDataStoreFactory;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/26/17.
 */
@RunWith( MockitoJUnitRunner.class )
public class MediaItemDataRepositoryTest extends TestData {

    private MediaItemDataRepository mediaItemDataRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private MediaItemDataStoreFactory mockMediaItemDataStoreFactory;

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Mock
    private MediaItemDataStore mockMediaItemDataStore;

    @Mock
    private SearchDataStore mockSearchDataStore;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        mediaItemDataRepository = new MediaItemDataRepository( mockMediaItemDataStoreFactory );

        given( mockMediaItemDataStoreFactory.create( anyInt()) ).willReturn( mockMediaItemDataStore );
        given( mockMediaItemDataStoreFactory.createMasterBackendDataStore() ).willReturn( mockMediaItemDataStore );
        given( mockMediaItemDataStoreFactory.createSearchDataStore() ).willReturn( mockSearchDataStore );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenCreateMediaItemDataRepository_whenMediaItemDataStoreFactoryIsNull_verifyIllegalArgumentException() {

        new MediaItemDataRepository( null );

    }

    @Test
    public void whenSeries_verifySeriesListReturned() {

        given( mockMediaItemDataStore.series( any( Media.class )) ).willReturn( setupSeriesEntities() );

        Observable<List<Series>> observable = mediaItemDataRepository.series( FAKE_MEDIA );
        TestSubscriber<List<Series>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).series( any( Media.class ) );

    }

    @Test
    public void whenMediaItems_verifyMediaItemListReturned() {

        given( mockMediaItemDataStore.mediaItems( any( Media.class ), anyString() ) ).willReturn( setupMediaItemEntities() );

        Observable<List<MediaItem>> observable = mediaItemDataRepository.mediaItems( FAKE_MEDIA, FAKE_TITLE );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).mediaItems( any( Media.class ), anyString() );

    }

    @Test
    public void whenMediaItem_verifyMediaItemReturned() {

        given( mockMediaItemDataStore.mediaItem( any( Media.class ), anyInt() ) ).willReturn( setupMediaItemEntity() );

        Observable<MediaItem> observable = mediaItemDataRepository.mediaItem( FAKE_MEDIA, FAKE_ID );
        TestSubscriber<MediaItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 1 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).mediaItem( any( Media.class ), anyInt() );

    }

    @Test
    public void whenAddLiveStream_verifyMediaItemReturned() {

        given( mockMediaItemDataStore.addLiveStream( any( Media.class ), anyInt() ) ).willReturn( setupMediaItemEntity() );

        Observable<MediaItem> observable = mediaItemDataRepository.addLiveStream( FAKE_MEDIA, FAKE_ID );
        TestSubscriber<MediaItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).addLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void whenRemoveLiveStream_verifyMediaItemReturned() {

        given( mockMediaItemDataStore.removeLiveStream( any( Media.class ), anyInt() ) ).willReturn( setupMediaItemEntity() );

        Observable<MediaItem> observable = mediaItemDataRepository.removeLiveStream( FAKE_MEDIA, FAKE_ID );
        TestSubscriber<MediaItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).removeLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void whenUpdateWatchedStatus_verifyMediaItemReturned() {

        given( mockMediaItemDataStore.updateWatchedStatus( any( Media.class ), anyInt(), anyBoolean() ) ).willReturn( setupMediaItemEntity() );

        Observable<MediaItem> observable = mediaItemDataRepository.updateWatchedStatus( FAKE_MEDIA, FAKE_ID, FAKE_WATCHED );
        TestSubscriber<MediaItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createSearchDataStore();
        verify( mockMediaItemDataStore ).updateWatchedStatus( any( Media.class ), anyInt(), anyBoolean() );

    }

    @Test
    public void whenSearch_verifyMediaItemListReturned() {

        given( mockSearchDataStore.search( anyString() ) ).willReturn( setupMediaItemEntities() );

        Observable<List<MediaItem>> observable = mediaItemDataRepository.search( FAKE_TITLE );
        TestSubscriber<List<MediaItem>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createSearchDataStore();
        verify( mockSearchDataStore ).search( anyString() );

    }

    private Observable<List<SeriesEntity>> setupSeriesEntities() {

        return Observable.just( Collections.singletonList( createFakeSeriesEntity() ) );
    }

    private Observable<List<MediaItemEntity>> setupMediaItemEntities() {

        return Observable.just( Collections.singletonList( createFakeMediaItemEntity() ) );
    }

    private Observable<MediaItemEntity> setupMediaItemEntity() {

        return Observable.just( createFakeMediaItemEntity() );
    }

}

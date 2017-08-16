package org.mythtv.android.data.repository;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.repository.datasource.MediaItemDataStore;
import org.mythtv.android.data.repository.datasource.MediaItemDataStoreFactory;
import org.mythtv.android.domain.Media;

import java.util.ArrayList;
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
public class MediaItemDataRepositoryTest {

    private static final String FAKE_ARTWORK = "fake artwork";
    private static final int FAKE_COUNT = 1;

    private static final int FAKE_ID = 1;
    private static final Media FAKE_MEDIA = Media.PROGRAM;
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUBTITLE = "fake subtitle";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final DateTime FAKE_START_DATE = DateTime.now();
    private static final int FAKE_PROGRAM_FLAGS = 1;
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final String FAKE_STUDIO = "fake studio";
    private static final String FAKE_CAST_MEMBERS = "fake cast members";
    private static final String FAKE_CHARACTERS = "fake characters";
    private static final String FAKE_URL = "fake url";
    private static final String FAKE_FANART_URL = "fake fanart url";
    private static final String FAKE_COVERART_URL = "fake coverart url";
    private static final String FAKE_BANNER_URL = "fake bannart url";
    private static final String FAKE_PREVIEW_URL = "fake preview url";
    private static final String FAKE_CONTENT_TYPE = "fake content type";
    private static final long FAKE_DURATION = 1L;
    private static final int FAKE_PERCENT_COMPLETE = 1;
    private static final boolean FAKE_RECORDING = false;
    private static final int FAKE_LIVESTREAM_ID = 999;
    private static final boolean FAKE_WATCHED = false;
    private static final String FAKE_UPDATE_SAVED_BOOKMARK_URL = "fake update saved bookmark url";
    private static final long FAKE_BOOKMARK = 1L;
    private static final String FAKE_CERTIFICATION = "fake certification";
    private static final int FAKE_PARENTAL_LEVEL = 1;
    private static final String FAKE_RECORDING_GROUP = "fake recording group";

    private MediaItemDataRepository mediaItemDataRepository;

    @Mock
    private MediaItemDataStoreFactory mockMediaItemDataStoreFactory;

    @Mock
    private MediaItemDataStore mockMediaItemDataStore;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        mediaItemDataRepository = new MediaItemDataRepository( mockMediaItemDataStoreFactory );

        given( mockMediaItemDataStoreFactory.create( anyInt()) ).willReturn( mockMediaItemDataStore );
        given( mockMediaItemDataStoreFactory.createMasterBackendDataStore() ).willReturn( mockMediaItemDataStore );

    }

    @Test
    public void testSeriesHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        List<SeriesEntity> seriesList = new ArrayList<>();
        seriesList.add( seriesEntity() );

        given( mockMediaItemDataStore.series( any( Media.class )) ).willReturn( Observable.just( seriesList ) );

        mediaItemDataRepository.series( FAKE_MEDIA )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).series( any( Media.class ) );

    }

    @Test
    public void testMediaItemsHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        List<MediaItemEntity> mediaItemsList = new ArrayList<>();
        mediaItemsList.add( mediaItemEntity() );

        given( mockMediaItemDataStore.mediaItems( any( Media.class ), anyString() ) ).willReturn( Observable.just( mediaItemsList ) );

        mediaItemDataRepository.mediaItems( FAKE_MEDIA, FAKE_TITLE )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).mediaItems( any( Media.class ), anyString() );

    }

    @Test
    public void testMediaItemHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        given( mockMediaItemDataStore.mediaItem( any( Media.class ), anyInt() ) ).willReturn( Observable.just( mediaItemEntity() ) );

        mediaItemDataRepository.mediaItem( FAKE_MEDIA, FAKE_ID )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 0 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 1 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).mediaItem( any( Media.class ), anyInt() );

    }

    @Test
    public void testAddLiveStreamHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        given( mockMediaItemDataStore.addLiveStream( any( Media.class ), anyInt() ) ).willReturn( Observable.just( mediaItemEntity() ) );

        mediaItemDataRepository.addLiveStream( FAKE_MEDIA, FAKE_ID )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).addLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void testRemoveLiveStreamHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        given( mockMediaItemDataStore.removeLiveStream( any( Media.class ), anyInt() ) ).willReturn( Observable.just( mediaItemEntity() ) );

        mediaItemDataRepository.removeLiveStream( FAKE_MEDIA, FAKE_ID )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).removeLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void testUpdateWatchedStatusHappyCase() {

        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        given( mockMediaItemDataStore.updateWatchedStatus( any( Media.class ), anyInt(), anyBoolean() ) ).willReturn( Observable.just( mediaItemEntity() ) );

        mediaItemDataRepository.updateWatchedStatus( FAKE_MEDIA, FAKE_ID, FAKE_WATCHED )
                .subscribe( subscriber );

        assertThat( subscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemDataStoreFactory, times( 1 ) ).createMasterBackendDataStore();
        verify( mockMediaItemDataStoreFactory, times( 0 ) ).create( anyInt() );
        verify( mockMediaItemDataStore ).updateWatchedStatus( any( Media.class ), anyInt(), anyBoolean() );

    }

    private SeriesEntity seriesEntity() {

        return SeriesEntity.create( FAKE_TITLE, FAKE_MEDIA, FAKE_ARTWORK, FAKE_COUNT, FAKE_INETREF );
    }

    private MediaItemEntity mediaItemEntity() {

        return MediaItemEntity.create(
                FAKE_ID, FAKE_MEDIA, FAKE_TITLE, FAKE_SUBTITLE, FAKE_DESCRIPTION, FAKE_START_DATE,
                FAKE_PROGRAM_FLAGS, FAKE_SEASON, FAKE_EPISODE, FAKE_STUDIO, FAKE_CAST_MEMBERS,
                FAKE_CHARACTERS, FAKE_URL, FAKE_FANART_URL, FAKE_COVERART_URL, FAKE_BANNER_URL,
                FAKE_PREVIEW_URL, FAKE_CONTENT_TYPE, FAKE_DURATION, FAKE_PERCENT_COMPLETE,
                FAKE_RECORDING, FAKE_LIVESTREAM_ID, FAKE_WATCHED, FAKE_UPDATE_SAVED_BOOKMARK_URL,
                FAKE_BOOKMARK, FAKE_INETREF, FAKE_CERTIFICATION, FAKE_PARENTAL_LEVEL,
                FAKE_RECORDING_GROUP, Collections.emptyList()
        );
    }

}

package org.mythtv.android.data.repository.datasource;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/14/17.
 */

public class MasterBackendMediaItemDataStoreTest extends ApplicationTestCase {

    private MasterBackendMediaItemDataStore masterBackendMediaItemDataStore;

    @Mock
    private DvrDataStoreFactory mockDvrDataStoreFactory;

   @Mock
    private VideoDataStoreFactory mockVideoDataStoreFactory;

    @Mock
    private ContentDataStoreFactory mockContentDataStoreFactory;

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Mock
    private DualCache<MediaItemEntity> mockCache;

    @Before
    public void setup() throws Exception {
        super.setup();

        MockitoAnnotations.initMocks( this );

        given( mockDvrDataStoreFactory.createMasterBackendDataStore() ).willReturn( new FakeDvrDataStore() );
        given( mockVideoDataStoreFactory.createMasterBackendDataStore() ).willReturn( new FakeVideoDataStore() );
        given( mockContentDataStoreFactory.createMasterBackendDataStore() ).willReturn( new FakeContentDataStore() );
        given( mockSearchDataStoreFactory.createWriteSearchDataStore() ).willReturn( new FakeSearchDataStore() );
        given( mockSearchDataStoreFactory.createReadSearchDataStore() ).willReturn( new FakeSearchDataStore() );

        masterBackendMediaItemDataStore = new MasterBackendMediaItemDataStore( mockDvrDataStoreFactory, mockVideoDataStoreFactory, mockContentDataStoreFactory, mockSearchDataStoreFactory, mockCache );

    }

    @Test
    public void givenSeries_whenMediaProgram_verifySeriesListReturned() {

        Observable<List<SeriesEntity>> observable = masterBackendMediaItemDataStore.series( Media.PROGRAM );
        TestSubscriber<List<SeriesEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test
    public void givenSeries_whenMediaTelevision_verifySeriesListReturned() {

        Observable<List<SeriesEntity>> observable = masterBackendMediaItemDataStore.series( Media.TELEVISION );
        TestSubscriber<List<SeriesEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenSeries_whenMediaOther_verifyIllegalArgumentExceptionThrown() {

        masterBackendMediaItemDataStore.series( Media.VIDEO );

    }

    @Test
    public void givenMediaItems_whenMediaUpcoming_verifyMediaItemListReturned() {

        Observable<List<MediaItemEntity>> observable = masterBackendMediaItemDataStore.mediaItems( Media.UPCOMING, null );
        TestSubscriber<List<MediaItemEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test
    public void givenMediaItems_whenMediaRecent_verifyMediaItemListReturned() {

        Observable<List<MediaItemEntity>> observable = masterBackendMediaItemDataStore.mediaItems( Media.RECENT, null );
        TestSubscriber<List<MediaItemEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test
    public void givenMediaItems_whenMediaProgram_verifyMediaItemListReturned() {

        Observable<List<MediaItemEntity>> observable = masterBackendMediaItemDataStore.mediaItems( Media.PROGRAM, null );
        TestSubscriber<List<MediaItemEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test
    public void givenMediaItems_whenMediaVideo_verifyMediaItemListReturned() {

        Observable<List<MediaItemEntity>> observable = masterBackendMediaItemDataStore.mediaItems( Media.VIDEO, null );
        TestSubscriber<List<MediaItemEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

    }

    @Test
    public void givenMediaItem_whenMediaUpcoming_verifyMediaItemReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.mediaItem( Media.UPCOMING, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenMediaItem_whenMediaRecent_verifyMediaItemReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.mediaItem( Media.RECENT, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenMediaItem_whenMediaProgram_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.mediaItem( Media.PROGRAM, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenMediaItem_whenMediaVideo_verifyMediaItemReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.mediaItem( Media.VIDEO, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenAddLiveStream_whenMediaProgram_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.addLiveStream( Media.PROGRAM, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenAddLiveStream_whenMediaVideo_verifyMediaItemReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.addLiveStream( Media.VIDEO, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenRemoveLiveStream_whenMediaProgram_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.removeLiveStream( Media.PROGRAM, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenRemoveLiveStream_whenMediaVideo_verifyMediaItemReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.removeLiveStream( Media.VIDEO, FAKE_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenUpdateWatchedStatus_whenMediaUpcoming_verifyIllegalArgumentExceptionThrown() {

        masterBackendMediaItemDataStore.updateWatchedStatus( Media.UPCOMING, FAKE_ID, Boolean.TRUE );

    }

    @Test
    public void givenUpdateWatchedStatus_whenMediaRecent_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.updateWatchedStatus( Media.RECENT, FAKE_ID, Boolean.TRUE );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenUpdateWatchedStatus_whenMediaProgram_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.updateWatchedStatus( Media.PROGRAM, FAKE_ID, Boolean.TRUE );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test
    public void givenUpdateWatchedStatus_whenMediaVideo_verifyMediaListReturned() {

        Observable<MediaItemEntity> observable = masterBackendMediaItemDataStore.updateWatchedStatus( Media.VIDEO, FAKE_ID, Boolean.TRUE );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    private class FakeDvrDataStore implements DvrDataStore {

        @Override
        public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

            return Observable.just( Collections.singletonList( createFakeTitleInfoEntity() ) );
        }

        @Override
        public Observable<List<ProgramEntity>> recordedProgramEntityList( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {

            // Set recording status to -2 for recorded and recent
            RecordingInfoEntity recording = RecordingInfoEntity.create(
                    String.valueOf( FAKE_RECORDED_ID ), -2, FAKE_PRIORITY, FAKE_START_TS, FAKE_END_TS,
                    FAKE_RECORD_ID, FAKE_REC_GROUP, FAKE_PLAY_GROUP, FAKE_STORAGE_GROUP, FAKE_REC_TYPE,
                    FAKE_DUP_IN_TYPE, FAKE_DUP_METHOD, FAKE_ENCODER_ID, FAKE_ENCODER_NAME, FAKE_PROFILE
            );

            return Observable.just( Collections.singletonList( ProgramEntity.create(
                    FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                    FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                    FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                    FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                    FAKE_TOTALEPISODES, createFakeChannelInfoEntity(), recording,
                    createFakeArtworkEntity(), createFakeCastEntity()
            )));
        }

        @Override
        public Observable<ProgramEntity> recordedProgramEntityDetails( int recordedId ) {

            // Set recording status to -2 for recorded and recent
            RecordingInfoEntity recording = RecordingInfoEntity.create(
                    String.valueOf( FAKE_RECORDED_ID ), -2, FAKE_PRIORITY, FAKE_START_TS, FAKE_END_TS,
                    FAKE_RECORD_ID, FAKE_REC_GROUP, FAKE_PLAY_GROUP, FAKE_STORAGE_GROUP, FAKE_REC_TYPE,
                    FAKE_DUP_IN_TYPE, FAKE_DUP_METHOD, FAKE_ENCODER_ID, FAKE_ENCODER_NAME, FAKE_PROFILE
            );

            return Observable.just( ProgramEntity.create(
                    FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                    FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                    FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                    FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                    FAKE_TOTALEPISODES, createFakeChannelInfoEntity(), recording,
                    createFakeArtworkEntity(), createFakeCastEntity()
            ));
        }

        @Override
        public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {

            // Set recording status to -1 for upcoming
            RecordingInfoEntity recording = RecordingInfoEntity.create(
                    String.valueOf( FAKE_RECORDED_ID ), -1, FAKE_PRIORITY, FAKE_START_TS, FAKE_END_TS,
                    FAKE_RECORD_ID, FAKE_REC_GROUP, FAKE_PLAY_GROUP, FAKE_STORAGE_GROUP, FAKE_REC_TYPE,
                    FAKE_DUP_IN_TYPE, FAKE_DUP_METHOD, FAKE_ENCODER_ID, FAKE_ENCODER_NAME, FAKE_PROFILE
            );

            return Observable.just( Collections.singletonList( ProgramEntity.create(
                    FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                    FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                    FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                    FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                    FAKE_TOTALEPISODES, createFakeChannelInfoEntity(), recording,
                    createFakeArtworkEntity(), createFakeCastEntity()
            )));
        }

        @Override
        public Observable<List<EncoderEntity>> encoderEntityList() {

            return Observable.just( Collections.singletonList( createFakeEncoderEntity() ) );
        }

        @Override
        public Observable<Boolean> updateWatchedStatus( int id, boolean watched ) {

            return Observable.just( Boolean.TRUE );
        }

        @Override
        public Observable<Long> getBookmark( int recordedId, String offsetType ) {

            return Observable.just( 1L );
        }

    }

    private class FakeVideoDataStore implements VideoDataStore {

        @Override
        public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {

            return Observable.just( Collections.singletonList( createFakeVideoMetadataInfoEntity() ) );
        }

        @Override
        public Observable<List<VideoMetadataInfoEntity>> getCategory( String category ) {

            return Observable.just( Collections.singletonList( VideoMetadataInfoEntity.create(
                    FAKE_ID, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_TAG_LINE, FAKE_DIRECTOR, FAKE_STUDIO,
                    FAKE_DESCRIPTION, FAKE_CERTIFICATION, FAKE_INETREF, FAKE_COLLECTION_REF,
                    FAKE_HOMEPAGE, FAKE_RELEASE_DATE, FAKE_ADD_DATE, FAKE_USER_RATING, FAKE_LENGTH,
                    FAKE_PLAY_COUNT, FAKE_SEASON, FAKE_EPISODE, FAKE_PARENTAL_LEVEL, FAKE_VISIBLE,
                    FAKE_WATCHED, FAKE_PROCESSED, Media.TELEVISION.name(), FAKE_FILENAME, FAKE_HASH,
                    FAKE_HOSTNAME, FAKE_COVERART, FAKE_FANART, FAKE_BANNER, FAKE_SCREENSHOT,
                    FAKE_TRAILER, createFakeArtworkEntity(), createFakeCastEntity()
            )));
        }

        @Override
        public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {

            return Observable.just( createFakeVideoMetadataInfoEntity() );
        }

        @Override
        public Observable<Boolean> updateWatchedStatus( int videoId, boolean watched ) {

            return Observable.just( Boolean.TRUE );
        }

    }

    private class FakeContentDataStore implements ContentDataStore {

        @Override
        public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( String filename ) {

            return Observable.just( Collections.singletonList( createFakeLiveStreamInfoEntity() ) );
        }

        @Override
        public Observable<LiveStreamInfoEntity> addLiveStream( Media media, int id ) {

            return Observable.just( createFakeLiveStreamInfoEntity() );
        }

        @Override
        public Observable<Boolean> removeLiveStream( int id ) {

            return Observable.just( Boolean.TRUE );
        }

    }

    private class FakeSearchDataStore implements SearchDataStore {

        @Override
        public Observable<List<MediaItemEntity>> search( String searchString ) {

            return Observable.just( Collections.singletonList( createFakeMediaItemEntity() ) );
        }

        @Override
        public void refreshSeriesData( Collection<SeriesEntity> seriesEntityCollection ) {

        }

        @Override
        public void refreshRecordedProgramData( Collection<MediaItemEntity> mediaItemEntityCollection ) {

        }

        @Override
        public void refreshVideoData( Collection<MediaItemEntity> mediaItemEntityCollection ) {

        }

    }

}

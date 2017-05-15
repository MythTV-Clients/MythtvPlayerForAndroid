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
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.domain.Media;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/14/17.
 */
public class DiskMediaItemDataStoreTest extends ApplicationTestCase {

    private DiskMediaItemDataStore diskMediaItemDataStore;

    @Mock
    private DvrDataStoreFactory mockDvrDataStoreFactory;

    @Mock
    private ContentDataStoreFactory mockContentDataStoreFactory;

    @Mock
    private DualCache<MediaItemEntity> mockCache;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks( this );

        diskMediaItemDataStore = new DiskMediaItemDataStore( mockDvrDataStoreFactory, mockContentDataStoreFactory, mockCache );

        given( mockDvrDataStoreFactory.createMasterBackendDataStore() ).willReturn( new FakeDvrDataStore() );
        given( mockContentDataStoreFactory.createMasterBackendDataStore() ).willReturn( new FakeContentDataStore() );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_withDvrDataStoreFactoryIsNull_verifyIllegalArguementException() {

        new DiskMediaItemDataStore( null, mockContentDataStoreFactory, mockCache );
    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_withContentDataStoreFactoryIsNull_verifyIllegalArguementException() {

        new DiskMediaItemDataStore( mockDvrDataStoreFactory, null, mockCache );
    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_withCacheIsNull_verifyIllegalArguementException() {

        new DiskMediaItemDataStore( mockDvrDataStoreFactory, mockContentDataStoreFactory, null );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void whenSeries_verifyUnsupportedOperationException() {

        diskMediaItemDataStore.series( Media.PROGRAM );

    }

    @Test( expected = UnsupportedOperationException.class )
    public void whenMediaItems_verifyUnsupportedOperationException() {

        diskMediaItemDataStore.mediaItems( Media.PROGRAM, null );

    }

    @Test
    public void whenMediaItem_verifyMediaItem() {

        given( mockCache.get( anyString() ) ).willReturn( createFakeMediaItemEntity() );

        Observable<MediaItemEntity> observable = diskMediaItemDataStore.mediaItem( Media.PROGRAM, FAKE_RECORDED_ID );
        TestSubscriber<MediaItemEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

    }

    @Test( expected = UnsupportedOperationException.class )
    public void whenAddLiveStream_verifyUnsupportedOperationException() {

        diskMediaItemDataStore.addLiveStream( Media.PROGRAM, FAKE_ID );

    }

    @Test( expected = UnsupportedOperationException.class )
    public void whenRemoveLiveStream_verifyUnsupportedOperationException() {

        diskMediaItemDataStore.removeLiveStream( Media.PROGRAM, FAKE_ID );

    }

    @Test( expected = UnsupportedOperationException.class )
    public void whenUpdateWatchedStatus_verifyUnsupportedOperationException() {

        diskMediaItemDataStore.updateWatchedStatus( Media.PROGRAM, FAKE_ID, FAKE_WATCHED );

    }

    private class FakeDvrDataStore implements DvrDataStore {

        @Override
        public Observable<List<TitleInfoEntity>> titleInfoEntityList() {
            return null;
        }

        @Override
        public Observable<List<ProgramEntity>> recordedProgramEntityList(boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup) {
            return null;
        }

        @Override
        public Observable<ProgramEntity> recordedProgramEntityDetails(int recordedId) {
            return null;
        }

        @Override
        public Observable<List<ProgramEntity>> upcomingProgramEntityList(int startIndex, int count, boolean showAll, int recordId, int recStatus) {
            return null;
        }

        @Override
        public Observable<List<EncoderEntity>> encoderEntityList() {
            return null;
        }

        @Override
        public Observable<Boolean> updateWatchedStatus(int id, boolean watched) {
            return null;
        }

        @Override
        public Observable<Long> getBookmark( int recordedId, String offsetType ) {

            return Observable.just( 1L );
        }

    }

    private class FakeContentDataStore implements ContentDataStore {

        @Override
        public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( String filename ) {

            return Observable.just( Collections.singletonList( createFakeLiveStreamInfoEntity() ) );
        }

        @Override
        public Observable<LiveStreamInfoEntity> addLiveStream(Media media, int id) {
            return null;
        }

        @Override
        public Observable<Boolean> removeLiveStream(int id) {
            return null;
        }

    }

}

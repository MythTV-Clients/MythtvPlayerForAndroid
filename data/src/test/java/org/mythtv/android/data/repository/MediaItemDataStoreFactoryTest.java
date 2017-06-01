package org.mythtv.android.data.repository;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

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
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DiskMediaItemDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.MasterBackendMediaItemDataStore;
import org.mythtv.android.data.repository.datasource.MediaItemDataStore;
import org.mythtv.android.data.repository.datasource.MediaItemDataStoreFactory;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
public class MediaItemDataStoreFactoryTest extends TestData {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MediaItemDataStoreFactory mediaItemDataStoreFactory;

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
    public void setup() {

        MockitoAnnotations.initMocks( this );

        mediaItemDataStoreFactory = new MediaItemDataStoreFactory( mockDvrDataStoreFactory, mockVideoDataStoreFactory, mockContentDataStoreFactory, mockSearchDataStoreFactory, mockCache );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenDvrDataStoreIsNull_verifyIllegalArgumentException() {

        new MediaItemDataStoreFactory( null, mockVideoDataStoreFactory, mockContentDataStoreFactory, mockSearchDataStoreFactory, mockCache );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenVideoDataStoreIsNull_verifyIllegalArgumentException() {

        new MediaItemDataStoreFactory( mockDvrDataStoreFactory, null, mockContentDataStoreFactory, mockSearchDataStoreFactory, mockCache );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenContentDataStoreIsNull_verifyIllegalArgumentException() {

        new MediaItemDataStoreFactory( mockDvrDataStoreFactory, mockVideoDataStoreFactory, null, mockSearchDataStoreFactory, mockCache );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenSearchDataStoreIsNull_verifyIllegalArgumentException() {

        new MediaItemDataStoreFactory( mockDvrDataStoreFactory, mockVideoDataStoreFactory, mockContentDataStoreFactory, null, mockCache );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenCacheIsNull_verifyIllegalArgumentException() {

        new MediaItemDataStoreFactory( mockDvrDataStoreFactory, mockVideoDataStoreFactory, mockContentDataStoreFactory, mockSearchDataStoreFactory, null );

    }

    @Test
    public void whenCreate_withCache_verifyDiskDataStoreReturned() {

        given( mockCache.contains( anyString() ) ).willReturn( true );

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.create( FAKE_ID );

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( DiskMediaItemDataStore.class );

        verify( mockCache, times( 1 ) ).contains( anyString() );

    }

    @Test
    public void whenCreate_withCacheMiss_verifyMasterBackendDataStoreReturned() {

        given( mockCache.contains( anyString() ) ).willReturn( false );

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.create( FAKE_ID );

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendMediaItemDataStore.class );

        verify( mockCache, times( 1 ) ).contains( anyString() );

    }

    @Test
    public void whenCreateMasterBackend_verifyMasterBackendDataStoreReturned() {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendMediaItemDataStore.class );

    }

    @Test
    public void whenCreateSearchDataStore_verifyFakeSearchDataStoreReturned() {

        given( mockSearchDataStoreFactory.createReadSearchDataStore() ).willReturn( new FakeSearchDataStore() );

        SearchDataStore searchDataStore = mediaItemDataStoreFactory.createSearchDataStore();

        assertThat( searchDataStore )
                .isNotNull()
                .isInstanceOf( FakeSearchDataStore.class );

    }

    private class FakeSearchDataStore implements SearchDataStore {

        @Override
        public Observable<List<MediaItemEntity>> search( String searchString ) {

            throw new UnsupportedOperationException();
        }

        @Override
        public void refreshSeriesData( Collection<SeriesEntity> seriesEntityCollection ) {

            throw new UnsupportedOperationException();
        }

        @Override
        public void refreshRecordedProgramData( Collection<MediaItemEntity> mediaItemEntityCollection ) {

            throw new UnsupportedOperationException();
        }

        @Override
        public void refreshVideoData( Collection<MediaItemEntity> mediaItemEntityCollection ) {

            throw new UnsupportedOperationException();
        }

    }

}

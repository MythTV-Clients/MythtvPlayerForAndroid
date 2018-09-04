package org.mythtv.android.data.repository.datasource;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.MediaItemEntity;

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
public class MediaItemDataStoreFactoryTest extends ApplicationTestCase {

    private static final int FAKE_ID = 1;

    private MediaItemDataStoreFactory mediaItemDataStoreFactory;

    @Mock
    DvrDataStoreFactory mockDvrDataStoreFactory;

    @Mock
    VideoDataStoreFactory mockVideoDataStoreFactory;

    @Mock
    ContentDataStoreFactory mockContentDataStoreFactory;

    @Mock
    SearchDataStoreFactory mockSearchDataStoreFactory;

    @Mock
    DualCache<MediaItemEntity> mockCache;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks( this );

        mediaItemDataStoreFactory = new MediaItemDataStoreFactory( mockDvrDataStoreFactory, mockVideoDataStoreFactory, mockContentDataStoreFactory, mockSearchDataStoreFactory, mockCache );

    }

    @Test
    public void testCreateWithCache() {

        given( mockCache.contains( anyString() ) ).willReturn( true );

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.create( FAKE_ID );

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( DiskMediaItemDataStore.class );

        verify( mockCache, times( 1 ) ).contains( anyString() );

    }

    @Test
    public void testCreateWithoutCache() {

        given( mockCache.contains( anyString() ) ).willReturn( false );

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.create( FAKE_ID );

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendMediaItemDataStore.class );

        verify( mockCache, times( 1 ) ).contains( anyString() );

    }

    @Test
    public void testCreateMasterBackend() {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        assertThat( mediaItemDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendMediaItemDataStore.class );

    }

}

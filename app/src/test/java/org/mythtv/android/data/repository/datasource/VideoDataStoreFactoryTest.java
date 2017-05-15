package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.net.VideoApi;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on /18/15.
 */
public class VideoDataStoreFactoryTest extends ApplicationTestCase {

    private VideoDataStoreFactory videoDataStoreFactory;

    @Mock
    private VideoApi mockVideoApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

        videoDataStoreFactory = new VideoDataStoreFactory( mockVideoApi );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenVideoApiIsNull_verifyIllegalArgumentException() {

        new VideoDataStoreFactory( null );

    }

    @Test
    public void whenCreateMasterBackendDataStore_verifyMasterBackendVideoDataStoreReturned() {

        VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();

        assertThat( videoDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendVideoDataStore.class );

    }

}

package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.net.VideoApi;
import org.mythtv.android.data.repository.datasource.MasterBackendVideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on /18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class VideoDataStoreFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

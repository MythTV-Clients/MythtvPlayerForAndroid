package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.ProgramCache;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 9/18/15.
 */
public class DvrDataStoreFactoryTest extends ApplicationTestCase {

    private static final int FAKE_CHAN_ID = 999;
    private static final DateTime FAKE_START_TIME = new DateTime();

    private DvrDataStoreFactory dvrDataStoreFactory;

    @Mock
    private ProgramCache mockProgramCache;

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataStoreFactory = new DvrDataStoreFactory( RuntimeEnvironment.application, mockProgramCache, mockSearchDataStoreFactory );

    }

    @Test
    public void testCreateDiskDataStore() {

        given( mockProgramCache.isCached( FAKE_CHAN_ID, FAKE_START_TIME ) ).willReturn( true );
        given( mockProgramCache.isExpired() ).willReturn( false );

        DvrDataStore dvrDataStore = dvrDataStoreFactory.create( FAKE_CHAN_ID, FAKE_START_TIME );

        assertThat( dvrDataStore, is( notNullValue() ) );
        assertThat( dvrDataStore, is( instanceOf( DiskDvrDataStore.class ) ) );

        verify( mockProgramCache ).isCached( FAKE_CHAN_ID, FAKE_START_TIME );
        verify( mockProgramCache ).isExpired();

    }

    @Test
    public void testCreateMasterBackendDvrDataStore() {

        given( mockProgramCache.isExpired() ).willReturn( true );
        given( mockProgramCache.isCached( FAKE_CHAN_ID, FAKE_START_TIME ) ).willReturn( false );

        DvrDataStore userDataStore = dvrDataStoreFactory.create( FAKE_CHAN_ID, FAKE_START_TIME );

        assertThat( userDataStore, is( notNullValue() ) );
        assertThat( userDataStore, is( instanceOf( MasterBackendDvrDataStore.class ) ) );

        verify( mockProgramCache ).isExpired();

    }

}

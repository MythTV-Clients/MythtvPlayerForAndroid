package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.ProgramCache;

import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 9/19/15.
 */
public class DiskDvrDataStoreTest extends ApplicationTestCase {

    private static final int FAKE_CHAN_ID = 999;
    private static final DateTime FAKE_START_TIME = new DateTime();

    private DiskDvrDataStore diskDvrDataStore;

    @Mock
    private ProgramCache mockProgramCache;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        
        MockitoAnnotations.initMocks( this );
        diskDvrDataStore = new DiskDvrDataStore( mockProgramCache );
    }

    @Test
    public void testGetTitleInfoEntityListUnsupported() {

        expectedException.expect( UnsupportedOperationException.class );
        diskDvrDataStore.titleInfoEntityList();

    }

    @Test
    public void testGetRecordedEntityListUnsupported() {

        expectedException.expect( UnsupportedOperationException.class );
        diskDvrDataStore.recordedProgramEntityList( false, -1, -1, null, null, null );
        
    }

    @Test
    public void testGetRecordedProgramEntityDetailsFromCache() {

        diskDvrDataStore.recordedProgramEntityDetails( FAKE_CHAN_ID, FAKE_START_TIME );
        verify( mockProgramCache ).get( FAKE_CHAN_ID, FAKE_START_TIME );

    }

}

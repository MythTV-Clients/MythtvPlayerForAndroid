package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.domain.Program;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 9/19/15.
 */
public class MasterBackendDvrDataStoreTest extends ApplicationTestCase {

    private static final int FAKE_CHAN_ID = 999;
    private static final DateTime FAKE_START_TIME = new DateTime();

    private MasterBackendDvrDataStore masterBackendDvrDataStore;

    @Mock
    private DvrApi mockDvrApi;

    @Mock
    private ProgramCache mockProgramCache;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendDvrDataStore = new MasterBackendDvrDataStore( mockDvrApi, mockProgramCache );

    }

    @Test
    public void testGetTitleInfoEntityListFromApi() {

        masterBackendDvrDataStore.titleInfoEntityList();
        verify( mockDvrApi ).titleInfoEntityList();

    }

    @Test
    public void testGetUserEntityListFromApi() {

        masterBackendDvrDataStore.recordedProgramEntityList( false, -1, -1, null, null, null );
        verify( mockDvrApi ).recordedProgramEntityList( false, -1, -1, null, null, null );

    }

    @Test
    public void testGetUserEntityDetailsFromApi() {

        ProgramEntity fakeProgramEntity = new ProgramEntity();
        Observable<ProgramEntity> fakeObservable = Observable.just( fakeProgramEntity );
        given( mockDvrApi.recordedProgramById( FAKE_CHAN_ID, FAKE_START_TIME ) ).willReturn(fakeObservable);

        masterBackendDvrDataStore.recordedProgramEntityDetails(FAKE_CHAN_ID, FAKE_START_TIME);

        verify( mockDvrApi ).recordedProgramById( FAKE_CHAN_ID, FAKE_START_TIME );

    }

}

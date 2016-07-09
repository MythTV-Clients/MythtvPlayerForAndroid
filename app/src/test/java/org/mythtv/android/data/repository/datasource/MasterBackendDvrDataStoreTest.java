package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.net.DvrApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendDvrDataStore = new MasterBackendDvrDataStore( mockDvrApi, mockProgramCache, mockSearchDataStoreFactory );

    }

    @Test
    public void testGetTitleInfoEntityListFromApi() {

        Observable<List<TitleInfoEntity>> fakeTitleInfoEntityListObservable = Observable.just( Collections.emptyList() );
        given( mockDvrApi.titleInfoEntityList() ).willReturn( fakeTitleInfoEntityListObservable );

        masterBackendDvrDataStore.titleInfoEntityList();
        verify( mockDvrApi ).titleInfoEntityList();

    }

    @Test
    public void testGetProgramEntityListFromApi() {

        ProgramEntity fakeProgramEntity = new ProgramEntity();
        Observable<List<TitleInfoEntity>> fakeTitleInfoEntityListObservable = Observable.just( Collections.emptyList() );
        Observable<List<ProgramEntity>> fakeObservable = Observable.just( Collections.singletonList( fakeProgramEntity ) );
        given( mockDvrApi.recordedProgramEntityList( false, -1, -1, null, null, null ) ).willReturn( fakeObservable );
        given( mockDvrApi.titleInfoEntityList() ).willReturn( fakeTitleInfoEntityListObservable );

        masterBackendDvrDataStore.recordedProgramEntityList( false, -1, -1, null, null, null );
        verify( mockDvrApi ).recordedProgramEntityList( false, -1, -1, null, null, null );

    }

    @Test
    public void testGetProgramEntityDetailsFromApi() {

        ProgramEntity fakeProgramEntity = new ProgramEntity();
        Observable<ProgramEntity> fakeObservable = Observable.just( fakeProgramEntity );
        given( mockDvrApi.recordedProgramById( FAKE_CHAN_ID, FAKE_START_TIME ) ).willReturn( fakeObservable );

        masterBackendDvrDataStore.recordedProgramEntityDetails( FAKE_CHAN_ID, FAKE_START_TIME );

        verify( mockDvrApi ).recordedProgramById( FAKE_CHAN_ID, FAKE_START_TIME );

    }

}

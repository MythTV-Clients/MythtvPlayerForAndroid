package org.mythtv.android.data.repository;

import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.ProgramEntityDataMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.domain.Program;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class ProgramDataRepositoryTest extends ApplicationTestCase {

    private static final int FAKE_CHAN_ID = 999;
    private static final DateTime FAKE_START_TIME = new DateTime();

    private DvrDataRepository dvrDataRepository;

    @Mock private DvrDataStoreFactory mockDvrDataStoreFactory;
    @Mock private TitleInfoEntityDataMapper mockTitleInfoEntityDataMapper;
    @Mock private ProgramEntityDataMapper mockProgramEntityDataMapper;
    @Mock private DvrDataStore mockDvrDataStore;
    @Mock private ProgramEntity mockProgramEntity;
    @Mock private Program mockProgram;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataRepository = new DvrDataRepository(mockDvrDataStoreFactory, mockTitleInfoEntityDataMapper, mockProgramEntityDataMapper );

        given( mockDvrDataStoreFactory.create( anyInt(), any( DateTime.class ) ) ).willReturn(mockDvrDataStore);
        given( mockDvrDataStoreFactory.createMasterBackendDataStore() ).willReturn(mockDvrDataStore);

    }

    @Test
    public void testGetTitleInfossHappyCase() {

        List<TitleInfoEntity> titleInfosList = new ArrayList<>();
        titleInfosList.add( new TitleInfoEntity() );
        given( mockDvrDataStore.titleInfoEntityList() ).willReturn( Observable.just( titleInfosList ) );

        dvrDataRepository.titleInfos();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).titleInfoEntityList();

    }

    @Test
    public void testGetRecordedProgramsHappyCase() {

        List<ProgramEntity> recordedProgramsList = new ArrayList<>();
        recordedProgramsList.add( new ProgramEntity() );
        given( mockDvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) ).willReturn( Observable.just( recordedProgramsList ) );

        dvrDataRepository.recordedPrograms( true, -1, -1, null, null, null );

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).recordedProgramEntityList( true, -1, -1, null, null, null );

    }

    @Test
    public void testGetRecordedProgramHappyCase() {

        ProgramEntity programEntity = new ProgramEntity();
        given( mockDvrDataStore.recordedProgramEntityDetails( FAKE_CHAN_ID, FAKE_START_TIME ) ).willReturn( Observable.just( programEntity ) );

        dvrDataRepository.recordedProgram( FAKE_CHAN_ID, FAKE_START_TIME );

        verify( mockDvrDataStoreFactory ).create( FAKE_CHAN_ID, FAKE_START_TIME );
        verify( mockDvrDataStore ).recordedProgramEntityDetails( FAKE_CHAN_ID, FAKE_START_TIME );

    }

}

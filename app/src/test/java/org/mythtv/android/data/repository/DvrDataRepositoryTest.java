package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.domain.Program;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DvrDataRepositoryTest extends ApplicationTestCase {

    private static final int FAKE_RECORDED_ID = 999;

    private DvrDataRepository dvrDataRepository;

    @Mock private DvrDataStoreFactory mockDvrDataStoreFactory;
    @Mock private ContentDataStoreFactory mockContentDataStoreFactory;
    @Mock private DvrDataStore mockDvrDataStore;
    @Mock private ContentDataStore mockContentDataStore;
    @Mock private ProgramEntity mockProgramEntity;
    @Mock private Program mockProgram;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataRepository = new DvrDataRepository( mockDvrDataStoreFactory, mockContentDataStoreFactory );

        given( mockDvrDataStoreFactory.createMasterBackendDataStore() ).willReturn( mockDvrDataStore );
        given( mockContentDataStoreFactory.createMasterBackendDataStore() ).willReturn( mockContentDataStore );

    }

    @Test
    public void testGetTitleInfosHappyCase() {

        List<TitleInfoEntity> titleInfosList = new ArrayList<>();
        titleInfosList.add( new TitleInfoEntity() );

        List<ProgramEntity> recordedProgramsList = new ArrayList<>();
        recordedProgramsList.add( new ProgramEntity() );

        given( mockDvrDataStore.titleInfoEntityList() ).willReturn( Observable.just( titleInfosList ) );
        given( mockDvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) ).willReturn( Observable.just( recordedProgramsList ) );

        dvrDataRepository.titleInfos();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).titleInfoEntityList();

    }

    @Test
    public void testGetRecordedProgramsHappyCase() {

        List<ProgramEntity> recordedProgramsList = new ArrayList<>();
        recordedProgramsList.add( new ProgramEntity() );
        given( mockDvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) ).willReturn( Observable.just( recordedProgramsList ) );

        List<LiveStreamInfoEntity> liveStreamInfoEntityList = new ArrayList<>();
        liveStreamInfoEntityList.add( new LiveStreamInfoEntity() );
        given( mockContentDataStore.liveStreamInfoEntityList( null ) ).willReturn( Observable.just( liveStreamInfoEntityList ) );

        dvrDataRepository.recordedPrograms( true, -1, -1, null, null, null );

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).recordedProgramEntityList( true, -1, -1, null, null, null );

        verify( mockContentDataStoreFactory ).createMasterBackendDataStore();
        verify( mockContentDataStore ).liveStreamInfoEntityList( null );

    }

    @Test
    public void testGetRecordedProgramHappyCase() {

        ProgramEntity programEntity = new ProgramEntity();
        given( mockDvrDataStore.recordedProgramEntityDetails( FAKE_RECORDED_ID ) ).willReturn( Observable.just( programEntity ) );

        dvrDataRepository.recordedProgram( FAKE_RECORDED_ID );

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).recordedProgramEntityDetails( FAKE_RECORDED_ID );

    }

    @Test
    public void testGetEncodersHappyCase() {

        List<EncoderEntity> encodersList = new ArrayList<>();
        encodersList.add( new EncoderEntity() );

        given( mockDvrDataStore.encoderEntityList() ).willReturn( Observable.just( encodersList ) );

        dvrDataRepository.encoders();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).encoderEntityList();

    }

}

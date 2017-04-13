package org.mythtv.android.data.repository;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DvrDataRepositoryTest extends ApplicationTestCase {

    private static final int FAKE_RECORDED_ID = 999;

    private static final DateTime FAKE_START_TIME = new DateTime();
    private static final DateTime FAKE_END_TIME = new DateTime();
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_CATEGORY = "fake category";
    private static final String FAKE_CATTYPE = "fake catType";
    private static final boolean FAKE_REPEAT = false;
    private static final int FAKE_VIDEOPROPS = 1;
    private static final int FAKE_AUDIOPROPS = 1;
    private static final int FAKE_SUBPROPS = 1;
    private static final String FAKE_SERIESID = "fake seriesId";
    private static final String FAKE_PROGRAMID = "fake programId";
    private static final double FAKE_STARS = 1.0;
    private static final long FAKE_FILESIZE = 1L;
    private static final DateTime FAKE_LASTMODIFIED = new DateTime();
    private static final int FAKE_PROGRAMFLAGS = 1;
    private static final String FAKE_FILENAME = "fake fileName";
    private static final String FAKE_HOSTNAME = "fake hostName";
    private static final LocalDate FAKE_AIRDATE = new LocalDate();
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final int FAKE_TOTALEPISODES = 1;

    private static final int FAKE_ID = 1;
    private static final int FAKE_WIDTH = 1;
    private static final int FAKE_HEIGHT = 1;
    private static final int FAKE_BITRATE = 1;
    private static final int FAKE_AUDIO_BITRATE = 1;
    private static final int FAKE_SEGMENT_SIZE = 1;
    private static final int FAKE_MAX_SEGMENTS = 1;
    private static final int FAKE_START_SEGMENT = 1;
    private static final int FAKE_CURRENT_SEGMENT = 1;
    private static final int FAKE_SEGMENT_COUNT = 1;
    private static final int FAKE_PERCENT_COMPLETE = 1;
    private static final DateTime FAKE_CREATED = new DateTime();
    private static final DateTime FAKE_LAST_MODIFIED = new DateTime();
    private static final String FAKE_RELATIVE_URL = "fake relative url";
    private static final String FAKE_FULL_URL = "fake full url";
    private static final String FAKE_STATUS_STR = "fake status str";
    private static final int FAKE_STATUS_INT = 1;
    private static final String FAKE_STATUS_MESSAGE = "fake status message";
    private static final String FAKE_SOURCE_FILE = "fake source file";
    private static final String FAKE_SOURCE_HOST = "fake source host";
    private static final int FAKE_SOURCE_WIDTH = 1;
    private static final int FAKE_SOURCE_HEIGHT = 1;
    private static final int FAKE_AUDIO_ONLY_BITRATE = 1;

    private DvrDataRepository dvrDataRepository;

    @Mock private DvrDataStoreFactory mockDvrDataStoreFactory;
    @Mock private ContentDataStoreFactory mockContentDataStoreFactory;
    @Mock private DvrDataStore mockDvrDataStore;
    @Mock private ContentDataStore mockContentDataStore;
    @Mock private ProgramEntity mockProgramEntity;

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
        titleInfosList.add( TitleInfoEntity.create( "", "", -1 ) );

        List<ProgramEntity> recordedProgramsList = new ArrayList<>();
        recordedProgramsList.add( createFakeProgramEntity() );

        given( mockDvrDataStore.titleInfoEntityList() ).willReturn( Observable.just( titleInfosList ) );
        given( mockDvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) ).willReturn( Observable.just( recordedProgramsList ) );

        dvrDataRepository.titleInfos();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).titleInfoEntityList();

    }

    @Test
    public void testGetRecordedProgramsHappyCase() {

        List<ProgramEntity> recordedProgramsList = new ArrayList<>();
        recordedProgramsList.add( createFakeProgramEntity() );
        given( mockDvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) ).willReturn( Observable.just( recordedProgramsList ) );

        List<LiveStreamInfoEntity> liveStreamInfoEntityList = new ArrayList<>();
        liveStreamInfoEntityList.add( createFakeLiveStreamInfoEntity() );
        given( mockContentDataStore.liveStreamInfoEntityList( null ) ).willReturn( Observable.just( liveStreamInfoEntityList ) );

        dvrDataRepository.recordedPrograms( true, -1, -1, null, null, null );

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).recordedProgramEntityList( true, -1, -1, null, null, null );

        verify( mockContentDataStoreFactory ).createMasterBackendDataStore();
        verify( mockContentDataStore ).liveStreamInfoEntityList( null );

    }

    @Test
    public void testGetRecordedProgramHappyCase() {

        ProgramEntity programEntity = createFakeProgramEntity();
        given( mockDvrDataStore.recordedProgramEntityDetails( FAKE_RECORDED_ID ) ).willReturn( Observable.just( programEntity ) );

        dvrDataRepository.recordedProgram( FAKE_RECORDED_ID );

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).recordedProgramEntityDetails( FAKE_RECORDED_ID );

    }

    @Test
    public void testGetEncodersHappyCase() {

        List<EncoderEntity> encodersList = new ArrayList<>();
        encodersList.add( EncoderEntity.create( -1, "", false, false, -1, -1, false, Collections.emptyList(), createFakeProgramEntity() ) );

        given( mockDvrDataStore.encoderEntityList() ).willReturn( Observable.just( encodersList ) );

        dvrDataRepository.encoders();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).encoderEntityList();

    }

    private ProgramEntity createFakeProgramEntity() {

        return ProgramEntity.create(
                FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                FAKE_TOTALEPISODES, null, null, null, null );
    }

    private LiveStreamInfoEntity createFakeLiveStreamInfoEntity() {

        return LiveStreamInfoEntity.create(
                FAKE_ID, FAKE_WIDTH, FAKE_HEIGHT, FAKE_BITRATE, FAKE_AUDIO_BITRATE,
                FAKE_SEGMENT_SIZE, FAKE_MAX_SEGMENTS, FAKE_START_SEGMENT,  FAKE_CURRENT_SEGMENT,
                FAKE_SEGMENT_COUNT, FAKE_PERCENT_COMPLETE, FAKE_CREATED, FAKE_LAST_MODIFIED,
                FAKE_RELATIVE_URL, FAKE_FULL_URL, FAKE_STATUS_STR, FAKE_STATUS_INT,
                FAKE_STATUS_MESSAGE, FAKE_SOURCE_FILE, FAKE_SOURCE_HOST, FAKE_SOURCE_WIDTH,
                FAKE_SOURCE_HEIGHT, FAKE_AUDIO_ONLY_BITRATE );
    }

}

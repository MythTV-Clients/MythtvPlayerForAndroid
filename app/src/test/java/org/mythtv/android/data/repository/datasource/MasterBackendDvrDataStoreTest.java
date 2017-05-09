package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.net.DvrApi;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 9/19/15.
 */
public class MasterBackendDvrDataStoreTest extends ApplicationTestCase {

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

    private MasterBackendDvrDataStore masterBackendDvrDataStore;

    @Mock
    private DvrApi mockDvrApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendDvrDataStore = new MasterBackendDvrDataStore( mockDvrApi );

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

        ProgramEntity fakeProgramEntity = createFakeProgramEntity();
        Observable<List<TitleInfoEntity>> fakeTitleInfoEntityListObservable = Observable.just( Collections.emptyList() );
        Observable<List<ProgramEntity>> fakeObservable = Observable.just( Collections.singletonList( fakeProgramEntity ) );
        given( mockDvrApi.recordedProgramEntityList( false, -1, -1, null, null, null ) ).willReturn( fakeObservable );
        given( mockDvrApi.titleInfoEntityList() ).willReturn( fakeTitleInfoEntityListObservable );

        masterBackendDvrDataStore.recordedProgramEntityList( false, -1, -1, null, null, null );
        verify( mockDvrApi ).recordedProgramEntityList( false, -1, -1, null, null, null );

    }

    @Test
    public void testGetProgramEntityDetailsFromApi() {

        ProgramEntity fakeProgramEntity = createFakeProgramEntity();
        Observable<ProgramEntity> fakeObservable = Observable.just( fakeProgramEntity );
        given( mockDvrApi.recordedProgramById( FAKE_RECORDED_ID, -1, null ) ).willReturn( fakeObservable );

        masterBackendDvrDataStore.recordedProgramEntityDetails( FAKE_RECORDED_ID );

        verify( mockDvrApi ).recordedProgramById( FAKE_RECORDED_ID, -1, null );

    }

    private ProgramEntity createFakeProgramEntity() {

        return ProgramEntity.create(
                FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                FAKE_TOTALEPISODES, null, null, null, null );
    }

}

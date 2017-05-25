package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.net.DvrApi;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 9/19/15.
 */
public class MasterBackendDvrDataStoreTest extends ApplicationTestCase {

    private MasterBackendDvrDataStore masterBackendDvrDataStore;

    @Mock
    private DvrApi mockDvrApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendDvrDataStore = new MasterBackendDvrDataStore( mockDvrApi );

    }

    @Test
    public void whenGetTitleInfoEntityList_verifyTitleInfoListReturned() {

        given( mockDvrApi.titleInfoEntityList() ).willReturn( setupTitleInfos() );

        Observable<List<TitleInfoEntity>> observable = masterBackendDvrDataStore.titleInfoEntityList();
        TestObserver<List<TitleInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockDvrApi, times( 1 ) ).titleInfoEntityList();

    }

    @Test
    public void whenRecordedProgramEntityList_verifyProgramListReturned() {

        given( mockDvrApi.titleInfoEntityList() ).willReturn( setupTitleInfos() );
        given( mockDvrApi.recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() ) ).willReturn( setupPrograms() );

        Observable<List<ProgramEntity>> observable = masterBackendDvrDataStore.recordedProgramEntityList( false, -1, -1, null, null, null );
        TestObserver<List<ProgramEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockDvrApi, times( 1 ) ).recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() );

    }

    @Test
    public void whenRecordedProgramEntityDetails_verifyProgramReturned() {

        given( mockDvrApi.recordedProgramById( anyInt() ) ).willReturn( setupProgram() );

        Observable<ProgramEntity> observable = masterBackendDvrDataStore.recordedProgramEntityDetails( FAKE_RECORDED_ID );
        TestObserver<ProgramEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        verify( mockDvrApi, times( 1 ) ).recordedProgramById( anyInt() );

    }

    @Test
    public void whenUpcomingProgramEntityList_verifyProgramListReturned() {

        given( mockDvrApi.upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() ) ).willReturn( setupPrograms() );

        Observable<List<ProgramEntity>> observable = masterBackendDvrDataStore.upcomingProgramEntityList( 1, -1, true, -1, -1 );
        TestObserver<List<ProgramEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockDvrApi, times( 1 ) ).upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() );

    }

    @Test
    public void whenEncoderEntityList_verifyProgramListReturned() {

        given( mockDvrApi.encoderEntityList() ).willReturn( setupEncoders() );

        Observable<List<EncoderEntity>> observable = masterBackendDvrDataStore.encoderEntityList();
        TestObserver<List<EncoderEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockDvrApi, times( 1 ) ).encoderEntityList();

    }

    @Test
    public void whenUpdateWatchedStatus_verifyBooleanReturned() {

        given( mockDvrApi.updateWatchedStatus( anyInt(), anyBoolean() ) ).willReturn( setupWatched() );

        Observable<Boolean> observable = masterBackendDvrDataStore.updateWatchedStatus( 1, Boolean.TRUE );
        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isTrue();

        verify( mockDvrApi, times( 1 ) ).updateWatchedStatus( anyInt(), anyBoolean() );

    }

    @Test
    public void whenGetBookmark_verifyLongReturned() {

        given( mockDvrApi.getBookmark( anyInt(), anyString() ) ).willReturn( setupBookmark() );

        Observable<Long> observable = masterBackendDvrDataStore.getBookmark( 1, null );
        TestObserver<Long> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isEqualTo( 1 );

        verify( mockDvrApi, times( 1 ) ).getBookmark( anyInt(), anyString() );

    }

    private Observable<List<TitleInfoEntity>> setupTitleInfos() {

        return Observable.just( Collections.singletonList( createFakeTitleInfoEntity() ) );
    }

    private Flowable<ProgramEntity> setupPrograms() {

        return Flowable.just( createFakeProgramEntity() );
    }

    private Observable<ProgramEntity> setupProgram() {

        return Observable.just( createFakeProgramEntity() );
    }

    private Flowable<EncoderEntity> setupEncoders() {

        return Flowable.just( createFakeEncoderEntity() );
    }

    private Observable<Boolean> setupWatched() {

        return Observable.just( Boolean.TRUE );
    }

    private Observable<Long> setupBookmark() {

        return Observable.just( 1L );
    }

}

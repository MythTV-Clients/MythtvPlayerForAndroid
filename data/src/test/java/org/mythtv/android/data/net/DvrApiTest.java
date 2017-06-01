package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.robolectric.shadows.ShadowLog;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dmfrey on 9/27/15.
 */
public class DvrApiTest extends TestData {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private DvrApi api;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

        ShadowLog.stream = System.out;

    }

    @Test
    public void testTitleInfoEntityList() throws Exception{

        when( api.titleInfoEntityList() ).thenReturn( setupTitleInfos() );

        Observable<List<TitleInfoEntity>> observable = api.titleInfoEntityList();
        TestObserver<List<TitleInfoEntity>> testSubscriber = new TestObserver<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).titleInfoEntityList();

    }

    @Test
    public void testRecordedProgramEntityList() throws Exception{

        when( api.recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() ) ).thenReturn( setupRecordedPrograms() );

        Flowable<ProgramEntity> observable = api.recordedProgramEntityList( true, 1, 1, null, null, null );
        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() );

    }

    @Test
    public void testRecordedProgramEntity() throws Exception{

        when( api.recordedProgramById( anyInt() ) ).thenReturn( setupRecordedProgram() );

        Observable<ProgramEntity> observable = api.recordedProgramById( 1 );
        TestObserver<ProgramEntity> testSubscriber = new TestObserver<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull();

        verify( api, times( 1 ) ).recordedProgramById( anyInt() );

    }

    @Test
    public void testUpcomingProgramEntityList() throws Exception{

        when( api.upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() ) ).thenReturn( setupRecordedPrograms() );

        Flowable<ProgramEntity> observable = api.upcomingProgramEntityList( 1, 1, false, -1, -1 );
        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() );

    }

    @Test
    public void testEncoderEntityList() throws Exception{

        when( api.encoderEntityList() ).thenReturn( setupEncoders() );

        Flowable<EncoderEntity> observable = api.encoderEntityList();
        TestSubscriber<EncoderEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).encoderEntityList();

    }

    private Observable<List<TitleInfoEntity>> setupTitleInfos() {

        return Observable.just( Collections.singletonList( createFakeTitleInfoEntity() ) );
    }

    private Flowable<ProgramEntity> setupRecordedPrograms() {

        return Flowable.just( createFakeProgramEntity() );
    }

    private Observable<ProgramEntity> setupRecordedProgram() {

        return Observable.just( createFakeProgramEntity() );
    }

    private Flowable<EncoderEntity> setupEncoders() {

        return Flowable.just( createFakeEncoderEntity() );
    }

}

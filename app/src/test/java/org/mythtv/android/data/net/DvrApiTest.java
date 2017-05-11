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

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

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

    }

    @Test
    public void testTitleInfoEntityList() throws Exception{

        when( api.titleInfoEntityList() ).thenReturn( setupTitleInfos() );

        Observable<List<TitleInfoEntity>> observable = api.titleInfoEntityList();
        TestSubscriber<List<TitleInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).titleInfoEntityList();

    }

    @Test
    public void testRecordedProgramEntityList() throws Exception{

        when( api.recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() ) ).thenReturn( setupRecordedPrograms() );

        Observable<List<ProgramEntity>> observable = api.recordedProgramEntityList( true, 1, 1, null, null, null );
        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).recordedProgramEntityList( anyBoolean(), anyInt(), anyInt(), anyString(), anyString(), anyString() );

    }

    @Test
    public void testRecordedProgramEntity() throws Exception{

        when( api.recordedProgramById( anyInt() ) ).thenReturn( setupRecordedProgram() );

        Observable<ProgramEntity> observable = api.recordedProgramById( 1 );
        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents() )
                .isNotNull();

        verify( api, times( 1 ) ).recordedProgramById( anyInt() );

    }

    @Test
    public void testUpcomingProgramEntityList() throws Exception{

        when( api.upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() ) ).thenReturn( setupRecordedPrograms() );

        Observable<List<ProgramEntity>> observable = api.upcomingProgramEntityList( 1, 1, false, -1, -1 );
        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).upcomingProgramEntityList( anyInt(), anyInt(), anyBoolean(), anyInt(), anyInt() );

    }

    @Test
    public void testEncoderEntityList() throws Exception{

        when( api.encoderEntityList() ).thenReturn( setupEncoders() );

        Observable<List<EncoderEntity>> observable = api.encoderEntityList();
        TestSubscriber<List<EncoderEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents() )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).encoderEntityList();

    }

    private Observable<List<TitleInfoEntity>> setupTitleInfos() {

        return Observable.just( Collections.singletonList( TitleInfoEntity.create( "test title", "test inetref", 1 ) ) );
    }

    private Observable<List<ProgramEntity>> setupRecordedPrograms() {

        return Observable.just( Collections.singletonList( createFakeProgramEntity() ) );
    }

    private Observable<ProgramEntity> setupRecordedProgram() {

        return Observable.just( createFakeProgramEntity() );
    }

    private Observable<List<EncoderEntity>> setupEncoders() {

        return Observable.just( Collections.singletonList( createFakeEncoderEntity() ) );
    }

}

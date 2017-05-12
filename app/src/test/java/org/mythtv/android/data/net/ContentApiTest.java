package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dmfrey on 9/27/15.
 */
public class ContentApiTest extends TestData {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ContentApi api;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testLiveStreamInfoEntityList() throws Exception{

        when( api.liveStreamInfoEntityList( anyString() ) ).thenReturn( setupLiveStreamInfoList() );

        Observable<List<LiveStreamInfoEntity>> observable = api.liveStreamInfoEntityList( FAKE_FILENAME );
        TestSubscriber<List<LiveStreamInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).liveStreamInfoEntityList( anyString() );

    }

    private Observable<List<LiveStreamInfoEntity>> setupLiveStreamInfoList() {

        return Observable.just( Collections.singletonList( createFakeLiveStreamInfoEntity() ) );
    }

}

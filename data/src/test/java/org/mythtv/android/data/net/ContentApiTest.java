package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.robolectric.shadows.ShadowLog;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

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
    public TestRule injectMocksRule = (base, description ) -> {

        MockitoAnnotations.initMocks(ContentApiTest.this );

        return base;
    };

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ContentApi api;

    @Before
    public void setup() {

        ShadowLog.stream = System.out;

    }

    @Test
    public void testLiveStreamInfoEntityList() throws Exception{

        when( api.liveStreamInfoEntityList( anyString() ) ).thenReturn( setupLiveStreamInfoList() );

        Observable<List<LiveStreamInfoEntity>> observable = api.liveStreamInfoEntityList( FAKE_FILENAME );
        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).liveStreamInfoEntityList( anyString() );

    }

    private Observable<List<LiveStreamInfoEntity>> setupLiveStreamInfoList() {

        return Observable.just( Collections.singletonList( createFakeLiveStreamInfoEntity() ) );
    }

}

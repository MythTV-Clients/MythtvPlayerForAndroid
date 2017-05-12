package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

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
public class VideoApiTest extends TestData {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private VideoApi api;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testGetVideoList() throws Exception{

        when( api.getVideoList( anyString(), anyString(), anyBoolean(), anyInt(), anyInt() ) ).thenReturn( setupVideoList() );

        Observable<List<VideoMetadataInfoEntity>> observable = api.getVideoList( FAKE_FILENAME, null, false, -1, -1 );
        TestSubscriber<List<VideoMetadataInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( api, times( 1 ) ).getVideoList( anyString(), anyString(), anyBoolean(), anyInt(), anyInt() );

    }

    @Test
    public void testGetVideoById() throws Exception{

        when( api.getVideoById( anyInt() ) ).thenReturn( setupVideo() );

        Observable<VideoMetadataInfoEntity> observable = api.getVideoById( FAKE_ID );
        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        verify( api, times( 1 ) ).getVideoById( anyInt() );

    }

    private Observable<List<VideoMetadataInfoEntity>> setupVideoList() {

        return Observable.just( Collections.singletonList( createFakeVideoMetadataInfoEntity() ) );
    }

    private Observable<VideoMetadataInfoEntity> setupVideo() {

        return Observable.just( createFakeVideoMetadataInfoEntity() );
    }

}

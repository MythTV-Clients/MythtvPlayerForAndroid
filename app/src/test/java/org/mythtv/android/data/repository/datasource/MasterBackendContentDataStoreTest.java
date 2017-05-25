package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.domain.Media;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 *
 * Created by dmfrey on 11/12/15.
 */
public class MasterBackendContentDataStoreTest extends ApplicationTestCase {

    private MasterBackendContentDataStore masterBackendContentDataStore;

    @Mock
    private ContentApi mockContentApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendContentDataStore = new MasterBackendContentDataStore( mockContentApi );

    }

    @Test
    public void whenLiveStreamInfoEntityList_verifyLiveStreamListReturned() {

        given( mockContentApi.liveStreamInfoEntityList( anyString() ) ).willReturn( setupLiveStreams() );

        Observable<List<LiveStreamInfoEntity>> observable = masterBackendContentDataStore.liveStreamInfoEntityList( FAKE_FILENAME );
        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockContentApi, times( 1 ) ).liveStreamInfoEntityList( anyString() );

    }

    @Test
    public void whenAddLiveStream_whenMediaProgram_verifyLiveStreamReturned() {

        given( mockContentApi.addLiveStream( any( Media.class ), anyInt() ) ).willReturn( setupLiveStream() );

        Observable<LiveStreamInfoEntity> observable = masterBackendContentDataStore.addLiveStream( Media.PROGRAM, 1 );
        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        verify( mockContentApi, times( 1 ) ).addLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void whenAddLiveStream_whenMediaVideo_verifyLiveStreamReturned() {

        given( mockContentApi.addLiveStream( any( Media.class ), anyInt() ) ).willReturn( setupLiveStream() );

        Observable<LiveStreamInfoEntity> observable = masterBackendContentDataStore.addLiveStream( Media.VIDEO, 1 );
        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        verify( mockContentApi, times( 1 ) ).addLiveStream( any( Media.class ), anyInt() );

    }

    @Test
    public void whenRemoveLiveStream_verifyBooleanReturned() {

        given( mockContentApi.removeLiveStream( anyInt() ) ).willReturn( setupRemoved() );

        Observable<Boolean> observable = masterBackendContentDataStore.removeLiveStream( FAKE_ID );
        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isTrue();

        verify( mockContentApi ).removeLiveStream( anyInt() );

    }

    private Observable<List<LiveStreamInfoEntity>> setupLiveStreams() {

        return Observable.just( Collections.singletonList( createFakeLiveStreamInfoEntity() ) );
    }

    private Observable<LiveStreamInfoEntity> setupLiveStream() {

        return Observable.just( createFakeLiveStreamInfoEntity() );
    }

    private Observable<Boolean> setupRemoved() {

        return Observable.just( Boolean.TRUE );
    }

}

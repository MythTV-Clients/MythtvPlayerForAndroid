package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.Media;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/11/17.
 */
public class ContentApiIntegrationTest extends ApplicationTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MockWebServer server;

    private OkHttpClient client = new OkHttpClient();

    @Before
    public void setup() {

        this.server = new MockWebServer();

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenContentApiImplIsCreatedWithInvalidArguments_whereContextIsNull_verifyIllegalArgumentException() {

        new ContentApiImpl( null, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenContentApiImplIsCreatedWithInvalidArguments_whereSharedPreferencesIsNull_verifyIllegalArgumentException() {

        new ContentApiImpl( context, null, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenContentApiImplIsCreatedWithInvalidArguments_whereOkHttpClientIsNull_verifyIllegalArgumentException() {

        new ContentApiImpl( context, sharedPreferences, null, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenContentApiImplIsCreatedWithInvalidArguments_whereLiveStreamInfoEntityJsonMapperIsNull_verifyIllegalArgumentException() {

        new ContentApiImpl( context, sharedPreferences, client, null, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenContentApiImplIsCreatedWithInvalidArguments_whereBooleanJsonMapperIsNull_verifyIllegalArgumentException() {

        new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, null );

    }

    @Test
    public void whenLiveStreamInfoList_withNullFilename_verifyLiveStreamInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Content_GetLiveStreamList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<List<LiveStreamInfoEntity>> observable = contentApi.liveStreamInfoEntityList( null );

        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 8 );

        server.shutdown();

    }

    @Test
    public void whenLiveStreamInfoList_withEmptyFilename_verifyLiveStreamInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Content_GetLiveStreamList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<List<LiveStreamInfoEntity>> observable = contentApi.liveStreamInfoEntityList( "" );

        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 8 );

        server.shutdown();

    }

    @Test
    public void whenLiveStreamInfoList_withFilename_verifyLiveStreamInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Content_GetLiveStreamList-Filename.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<List<LiveStreamInfoEntity>> observable = contentApi.liveStreamInfoEntityList( "2006_20170503000000.ts" );

        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        server.shutdown();

    }

    @Test
    public void whenLiveStreamInfoList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<List<LiveStreamInfoEntity>> observable = contentApi.liveStreamInfoEntityList( null );

        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenLiveStreamInfoList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<List<LiveStreamInfoEntity>> observable = contentApi.liveStreamInfoEntityList( null );

        TestObserver<List<LiveStreamInfoEntity>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenAddLiveStreamInfo_withWithMediaProgram_verifyLiveStreamInfoEntityReturned() throws Exception {

        String json = getUrlContents( "Content_GetLiveStream.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.PROGRAM, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        LiveStreamInfoEntity entity = testObserver.values().get( 0 );
        assertThat( entity.id() ).isEqualTo( 297 );
        assertThat( entity.width() ).isEqualTo( 960 );
        assertThat( entity.height() ).isEqualTo( 544 );
        assertThat( entity.bitrate() ).isEqualTo( 800000 );
        assertThat( entity.audioBitrate() ).isEqualTo( 64000 );
        assertThat( entity.segmentSize() ).isEqualTo( 4 );
        assertThat( entity.maxSegments() ).isEqualTo( 0 );
        assertThat( entity.startSegment() ).isEqualTo( 1 );
        assertThat( entity.currentSegment() ).isEqualTo( 449 );
        assertThat( entity.segmentCount() ).isEqualTo( 449 );
        assertThat( entity.percentComplete() ).isEqualTo( 100 );
        assertThat( entity.created() ).isNotNull();
        assertThat( entity.lastModified() ).isNotNull();
        assertThat( entity.relativeUrl() ).isEqualTo( "/StorageGroup/Streaming/2006_20170503000000.ts.960x544_800kV_64kA.m3u8" );
        assertThat( entity.fullUrl() ).isEqualTo( "http://192.168.100.200:6544/StorageGroup/Streaming/2006_20170503000000.ts.960x544_800kV_64kA.m3u8" );
        assertThat( entity.statusString() ).isEqualTo( "Completed" );
        assertThat( entity.statusInt() ).isEqualTo( 3 );
        assertThat( entity.statusMessage() ).isEqualTo( "Transcoding Completed" );
        assertThat( entity.sourceFile() ).isEqualTo( "/mnt/mythtv1/2006_20170503000000.ts" );
        assertThat( entity.sourceHost() ).isEqualTo( "mythcenter" );
        assertThat( entity.sourceWidth() ).isEqualTo( 1280 );
        assertThat( entity.sourceHeight() ).isEqualTo( 720 );
        assertThat( entity.audioOnlyBitrate() ).isEqualTo( 64000 );

        server.shutdown();

    }

    @Test
    public void whenAddLiveStream_whenMediaProgramServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.PROGRAM, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenAddiveStream_whenMediaProgramServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.PROGRAM, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenAddLiveStreamInfo_withWithMediaVideo_verifyLiveStreamInfoEntityReturned() throws Exception {

        String json = getUrlContents( "Content_GetLiveStream.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.VIDEO, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        LiveStreamInfoEntity entity = testObserver.values().get( 0 );
        assertThat( entity.id() ).isEqualTo( 297 );
        assertThat( entity.width() ).isEqualTo( 960 );
        assertThat( entity.height() ).isEqualTo( 544 );
        assertThat( entity.bitrate() ).isEqualTo( 800000 );
        assertThat( entity.audioBitrate() ).isEqualTo( 64000 );
        assertThat( entity.segmentSize() ).isEqualTo( 4 );
        assertThat( entity.maxSegments() ).isEqualTo( 0 );
        assertThat( entity.startSegment() ).isEqualTo( 1 );
        assertThat( entity.currentSegment() ).isEqualTo( 449 );
        assertThat( entity.segmentCount() ).isEqualTo( 449 );
        assertThat( entity.percentComplete() ).isEqualTo( 100 );
        assertThat( entity.created() ).isNotNull();
        assertThat( entity.lastModified() ).isNotNull();
        assertThat( entity.relativeUrl() ).isEqualTo( "/StorageGroup/Streaming/2006_20170503000000.ts.960x544_800kV_64kA.m3u8" );
        assertThat( entity.fullUrl() ).isEqualTo( "http://192.168.100.200:6544/StorageGroup/Streaming/2006_20170503000000.ts.960x544_800kV_64kA.m3u8" );
        assertThat( entity.statusString() ).isEqualTo( "Completed" );
        assertThat( entity.statusInt() ).isEqualTo( 3 );
        assertThat( entity.statusMessage() ).isEqualTo( "Transcoding Completed" );
        assertThat( entity.sourceFile() ).isEqualTo( "/mnt/mythtv1/2006_20170503000000.ts" );
        assertThat( entity.sourceHost() ).isEqualTo( "mythcenter" );
        assertThat( entity.sourceWidth() ).isEqualTo( 1280 );
        assertThat( entity.sourceHeight() ).isEqualTo( 720 );
        assertThat( entity.audioOnlyBitrate() ).isEqualTo( 64000 );

        server.shutdown();

    }

    @Test
    public void whenAddLiveStream_whenMediaVideoServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.VIDEO, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenAddiveStream_whenMediaVideoServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.VIDEO, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenAddLiveStreamInfo_withWithMediaOther_verifyNetworkConnectionException() throws Exception {

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<LiveStreamInfoEntity> observable = contentApi.addLiveStream( Media.ADULT, 1 );

        TestObserver<LiveStreamInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

    }

    @Test
    public void whenRemoveLiveStream_verifyBooleanReturned() throws Exception {

        String json = getUrlContents( "Content_PostRemoveLiveStream.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = contentApi.removeLiveStream( 297 );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isTrue();

        server.shutdown();

    }

    @Test
    public void whenRemoveLiveStream_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = contentApi.removeLiveStream( 297 );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenRemoveLiveStream_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        ContentApi contentApi = new ContentApiImpl( context, sharedPreferences, client, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = contentApi.removeLiveStream( 297 );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

}

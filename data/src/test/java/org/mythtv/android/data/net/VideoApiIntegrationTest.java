package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

import io.reactivex.Flowable;
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

public class VideoApiIntegrationTest extends ApplicationTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MockWebServer server;

    private OkHttpClient client;
    private VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;
    private BooleanJsonMapper booleanJsonMapper;

    @Before
    public void setup() {

        ShadowLog.stream = System.out;

        this.client = new OkHttpClient();
        this.videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper();
        this.booleanJsonMapper = new BooleanJsonMapper();

        this.server = new MockWebServer();

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenVideoApiImplIsCreatedWithInvalidArguments_whereContextIsNull_verifyIllegalArgumentException() {

        new VideoApiImpl( null, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenVideoApiImplIsCreatedWithInvalidArguments_whereSharedPreferencesIsNull_verifyIllegalArgumentException() {

        new VideoApiImpl( RuntimeEnvironment.application, null, client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenVideoApiImplIsCreatedWithInvalidArguments_whereOkHttpClientIsNull_verifyIllegalArgumentException() {

        new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), null, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenVideoApiImplIsCreatedWithInvalidArguments_whereVideoMetadataInfoEntityJsonMapperIsNull_verifyIllegalArgumentException() {

        new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, null, booleanJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenVideoApiImplIsCreatedWithInvalidArguments_whereBooleanJsonMapperIsNull_verifyIllegalArgumentException() {

        new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, null );

    }

    @Test
    public void whenGetVideoList_verifyVideoMetadataInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Video_GetVideoList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Flowable<VideoMetadataInfoEntity> observable = videoApi.getVideoList( null, null, false, -1, -1 );

        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 616 );

        server.shutdown();

    }

    @Test
    public void whenGetVideoList_whenParametersDiffer_verifyVideoMetadataInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Video_GetVideoList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Flowable<VideoMetadataInfoEntity> observable = videoApi.getVideoList( "", "", true, 1, 1 );

        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 616 );

        server.shutdown();

    }

    @Test
    public void whenGetVideoList_whenParametersFolderAndSortSet_verifyVideoMetadataInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Video_GetVideoList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Flowable<VideoMetadataInfoEntity> observable = videoApi.getVideoList( "folder", "sort", true, 1, 1 );

        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.values() )
                .isNotNull()
                .hasSize( 616 );

        server.shutdown();

    }

    @Test
    public void whenGetVideoList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Flowable<VideoMetadataInfoEntity> observable = videoApi.getVideoList( null, null, false, -1, -1 );

        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenGetVideoList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Flowable<VideoMetadataInfoEntity> observable = videoApi.getVideoList( null, null, false, -1, -1 );

        TestSubscriber<VideoMetadataInfoEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenGetVideo_verifyVideoMetadataInfoEntityReturned() throws Exception {

        String json = getUrlContents( "Video_GetVideo.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<VideoMetadataInfoEntity> observable = videoApi.getVideoById( 946 );

        TestObserver<VideoMetadataInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull();

        VideoMetadataInfoEntity entity = testObserver.values().get( 0 );
        assertThat( entity.id() ).isEqualTo( 946 );
        assertThat( entity.title() ).isEqualTo( "Ant-Man" );
        assertThat( entity.subTitle() ).isEmpty();
        assertThat( entity.director() ).isEqualTo( "Peyton Reed" );
        assertThat( entity.studio() ).isEqualTo( "Marvel Studios" );
        assertThat( entity.description() ).isEqualTo( "Armed with the astonishing ability to shrink in scale but increase in strength, con-man Scott Lang must embrace his inner-hero and help his mentor, Dr. Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world." );
        assertThat( entity.certification() ).isEqualTo( "PG-13" );
        assertThat( entity.inetref() ).isEqualTo( "tmdb3.py_102899" );
        assertThat( entity.collectionref() ).isEqualTo( -1 );
        assertThat( entity.homePage() ).isEqualTo( "http://marvel.com/movies/movie/180/ant-man" );
        assertThat( entity.releaseDate() ).isNotNull();
        assertThat( entity.addDate() ).isNotNull();
        assertThat( entity.userRating() ).isEqualTo( 7 );
        assertThat( entity.length() ).isEqualTo( 115 );
        assertThat( entity.playCount() ).isEqualTo( 0 );
        assertThat( entity.season() ).isEqualTo( 0 );
        assertThat( entity.episode() ).isEqualTo( 0 );
        assertThat( entity.parentalLevel() ).isEqualTo( 1 );
        assertThat( entity.visible() ).isTrue();
        assertThat( entity.watched() ).isFalse();
        assertThat( entity.processed() ).isTrue();
        assertThat( entity.contentType() ).isEqualTo( "MOVIE" );
        assertThat( entity.fileName() ).isEqualTo( "library/Movies/Marvel/Ant Man/Ant Man.mp4" );
        assertThat( entity.hash() ).isEqualTo( "7ae8e8c71e95d9be" );
        assertThat( entity.hostName() ).isEqualTo( "mythcenter" );
        assertThat( entity.coverart() ).isEqualTo( "tmdb3.py_102899_coverart.jpg" );
        assertThat( entity.fanart() ).isEqualTo( "tmdb3.py_102899_fanart.jpg" );
        assertThat( entity.banner() ).isEmpty();
        assertThat( entity.screenshot() ).isEmpty();
        assertThat( entity.trailer() ).isEmpty();
        assertThat( entity.artwork() ).isNotNull();
        assertThat( entity.artwork().artworkInfos() ).isNotNull().hasSize( 2 );
        assertThat( entity.artwork().artworkInfos().get( 0 ).url() ).isEqualTo( "/Content/GetImageFile?StorageGroup=Fanart&FileName=tmdb3.py_102899_fanart.jpg" );
        assertThat( entity.artwork().artworkInfos().get( 0 ).fileName() ).isEmpty();
        assertThat( entity.artwork().artworkInfos().get( 0 ).storageGroup() ).isEqualTo( "Fanart" );
        assertThat( entity.artwork().artworkInfos().get( 0 ).type() ).isEqualTo( "fanart" );
        assertThat( entity.artwork().artworkInfos().get( 1 ).url() ).isEqualTo( "/Content/GetImageFile?StorageGroup=Coverart&FileName=tmdb3.py_102899_coverart.jpg" );
        assertThat( entity.artwork().artworkInfos().get( 1 ).fileName() ).isEmpty();
        assertThat( entity.artwork().artworkInfos().get( 1 ).storageGroup() ).isEqualTo( "Coverart" );
        assertThat( entity.artwork().artworkInfos().get( 1 ).type() ).isEqualTo( "coverart" );
        assertThat( entity.cast() ).isNotNull();
        assertThat( entity.cast().castMembers() ).isEmpty();

        server.shutdown();

    }

    @Test
    public void whenGetVideo_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<VideoMetadataInfoEntity> observable = videoApi.getVideoById( 946 );

        TestObserver<VideoMetadataInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenGetVideo_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<VideoMetadataInfoEntity> observable = videoApi.getVideoById( 946 );

        TestObserver<VideoMetadataInfoEntity> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenWatchedIsTrue_verifyStatusReturned() throws Exception {

        String json = getUrlContents( "Video_PostUpdateWatchedStatus.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = videoApi.updateWatchedStatus( 946, true );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isTrue();

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenWatchedIsFalse_verifyStatusReturned() throws Exception {

        String json = getUrlContents( "Video_PostUpdateUnwatchedStatus.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = videoApi.updateWatchedStatus( 946, true );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertNoErrors();

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .isFalse();

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = videoApi.updateWatchedStatus( 946, true );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        VideoApi videoApi = new VideoApiImpl( RuntimeEnvironment.application, sharedPreferences(), client, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Observable<Boolean> observable = videoApi.updateWatchedStatus( 946, true );

        TestObserver<Boolean> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        testObserver.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

}

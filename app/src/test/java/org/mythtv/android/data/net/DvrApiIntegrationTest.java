package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.exception.NetworkConnectionException;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/10/17.
 */
public class DvrApiIntegrationTest extends ApplicationTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MockWebServer server;

    private OkHttpClient client = new OkHttpClient();

    @Before
    public void setup() {

        this.server = new MockWebServer();

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereContextIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( null, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereSharedPreferencesIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, null, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereOkHttpClientIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, null, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereTitleInfoEntityJsonMapperIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, client, null, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereProgramEntityJsonMapperIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, null, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereEncoderEntityJsonMapperIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, null, booleanJsonMapper, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereBooleanJsonMapperIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, null, longJsonMapper );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenDvrApiImplIsCreatedWithInvalidArguments_whereBLongJsonMapperIsNull_verifyIllegalArgumentException() {

        new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, null );

    }

    @Test
    public void whenTitleInfoList_verifyTitleInfoEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetTitleInfoList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<TitleInfoEntity>> observable = dvrApi.titleInfoEntityList();

        TestSubscriber<List<TitleInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 12 );

        server.shutdown();

    }

    @Test
    public void whenTitleInfoList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<TitleInfoEntity>> observable = dvrApi.titleInfoEntityList();

        TestSubscriber<List<TitleInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenTitleInfoList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<TitleInfoEntity>> observable = dvrApi.titleInfoEntityList();

        TestSubscriber<List<TitleInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenTitleInfoList_whenServerIsGone_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );
        server.shutdown();

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<TitleInfoEntity>> observable = dvrApi.titleInfoEntityList();

        TestSubscriber<List<TitleInfoEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityList_verifyProgramEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.recordedProgramEntityList( true, 1, 1, "test", "test", "test" );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 78 );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityList_withDifferentParameters_verifyProgramEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.recordedProgramEntityList( true, -1, -1, null, null, null );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 78 );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityList_withEmptyParameters_verifyProgramEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.recordedProgramEntityList( true, 0, -1, "", "", "" );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 78 );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.recordedProgramEntityList( true, 1, 1, "test", "test", "test" );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.recordedProgramEntityList( true, 1, 1, "test", "test", "test" );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityById_verifyProgramEntityReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedEntity.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<ProgramEntity> observable = dvrApi.recordedProgramById( 17500 );

        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull();

        ProgramEntity entity = testSubscriber.getOnNextEvents().get( 0 );
        assertThat( entity.startTime() ).isNotNull();
        assertThat( entity.endTime() ).isNotNull();
        assertThat( entity.title() ).isEqualTo( "The Goldbergs" );
        assertThat( entity.subTitle() ).isEqualTo( "Jedi Master Adam Skywalker" );
        assertThat( entity.category() ).isEqualTo( "Sitcom" );
        assertThat( entity.catType() ).isEqualTo( "series" );
        assertThat( entity.repeat() ).isFalse();
        assertThat( entity.videoProps() ).isEqualTo( 1 );
        assertThat( entity.audioProps() ).isEqualTo( 9 );
        assertThat( entity.subProps() ).isEqualTo( 1 );
        assertThat( entity.seriesId() ).isEqualTo( "EP01739617" );
        assertThat( entity.programId() ).isEqualTo( "EP017396170095" );
        assertThat( entity.stars() ).isEqualTo( 0 );
        assertThat( entity.lastModified() ).isNotNull();
        assertThat( entity.programFlags() ).isEqualTo( 76 );
        assertThat( entity.airdate() ).isEqualTo( "2017-05-10" );
        assertThat( entity.description() ).isEqualTo( "When Barry and Erica get Adam to show them the rough layout of the yearbook, they accidentally delete the file and damage Adam's relationship with Jackie; Marvin visits to ask for money." );
        assertThat( entity.inetref() ).isEqualTo( "ttvdb.py_269653" );
        assertThat( entity.season() ).isEqualTo( 4 );
        assertThat( entity.episode() ).isEqualTo( 23 );
        assertThat( entity.totalEpisodes() ).isEqualTo( 0 );
        assertThat( entity.fileSize() ).isEqualTo( 818483568 );
        assertThat( entity.fileName() ).isEqualTo( "2006_20170511000000.ts" );
        assertThat( entity.hostName() ).isEqualTo( "mythcenter" );
        assertThat( entity.channel() ).isNotNull();
        assertThat( entity.recording() ).isNotNull();
        assertThat( entity.artwork() ).isNotNull();
        assertThat( entity.artwork().artworkInfos() ).isNotNull().hasSize( 3 );
        assertThat( entity.artwork().artworkInfos().get( 0 ).url() ).isEqualTo( "/Content/GetImageFile?StorageGroup=Coverart&FileName=/The Goldbergs (2013) Season 4_coverart.jpg" );
        assertThat( entity.artwork().artworkInfos().get( 0 ).fileName() ).isEqualTo( "myth://Coverart@mythcenter/The Goldbergs (2013) Season 4_coverart.jpg" );
        assertThat( entity.artwork().artworkInfos().get( 0 ).storageGroup() ).isEqualTo( "Coverart" );
        assertThat( entity.artwork().artworkInfos().get( 0 ).type() ).isEqualTo( "coverart" );
        assertThat( entity.cast() ).isNotNull();
        assertThat( entity.cast().castMembers() ).isNotNull().hasSize( 26 );
        assertThat( entity.cast().castMembers().get( 0 ).name() ).isEqualTo( "AJ Michalka" );
        assertThat( entity.cast().castMembers().get( 0 ).characterName() ).isEmpty();
        assertThat( entity.cast().castMembers().get( 0 ).role() ).isEqualTo( "actor" );
        assertThat( entity.cast().castMembers().get( 0 ).translatedRole() ).isEqualTo( "Actors" );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityById_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<ProgramEntity> observable = dvrApi.recordedProgramById( 17500 );;

        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenRecordedProgramEntityById_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<ProgramEntity> observable = dvrApi.recordedProgramById( 17500 );;

        TestSubscriber<ProgramEntity> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpcomingProgramEntityList_verifyProgramEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetUpcomingList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.upcomingProgramEntityList( 1, -1, true, -1, -1 );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 43 );

        server.shutdown();

    }

    @Test
    public void whenUpcomingProgramEntityList_withDifferentCriteria_verifyProgramEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetUpcomingList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.upcomingProgramEntityList( -1, 1, true, 1, 1 );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 43 );

        server.shutdown();

    }

    @Test
    public void whenUpcomingProgramEntityList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.upcomingProgramEntityList( 1, -1, true, -1, -1 );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpcomingProgramEntityList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<ProgramEntity>> observable = dvrApi.upcomingProgramEntityList( 1, -1, true, -1, -1 );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenEncoderEntityList_verifyEncoderEntitiesListReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetEncoderList.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<EncoderEntity>> observable = dvrApi.encoderEntityList();

        TestSubscriber<List<EncoderEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 6 );

        server.shutdown();

    }

    @Test
    public void whenEncoderEntityList_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<EncoderEntity>> observable = dvrApi.encoderEntityList();

        TestSubscriber<List<EncoderEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenEncoderEntityList_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<List<EncoderEntity>> observable = dvrApi.encoderEntityList();

        TestSubscriber<List<EncoderEntity>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenWatchedIsTrue_verifyStatusReturned() throws Exception {

        String json = getUrlContents( "Dvr_PostUpdateWatchedStatus.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Boolean> observable = dvrApi.updateWatchedStatus( 17504, true );

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isTrue();

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenWatchedIsFalse_verifyStatusReturned() throws Exception {

        String json = getUrlContents( "Dvr_PostUpdateUnwatchedStatus.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Boolean> observable = dvrApi.updateWatchedStatus( 17504, false );

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isFalse();

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenServerReturns404_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Boolean> observable = dvrApi.updateWatchedStatus( 17504, true );

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenUpdateWatchedStatus_whenServerReturns500_verifyNetworkConnectionException() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Boolean> observable = dvrApi.updateWatchedStatus( 17504, true );

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertError( NetworkConnectionException.class );

        server.shutdown();

    }

    @Test
    public void whenBookmark_verifyBookmarkReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetSavedBookmark.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Long> observable = dvrApi.getBookmark( 17500, null );

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isEqualTo( 0 );

        server.shutdown();

    }

    @Test
    public void whenBookmark_withDifferentCriteria_verifyBookmarkReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetSavedBookmark.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Long> observable = dvrApi.getBookmark( 17500, "offsetType" );

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isEqualTo( 0 );

        server.shutdown();

    }

    @Test
    public void whenBookmark_withEmptyCriteria_verifyBookmarkReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetSavedBookmark.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Long> observable = dvrApi.getBookmark( 17500, "" );

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        testSubscriber.assertNoErrors();

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isEqualTo( 0 );

        server.shutdown();

    }

    @Test
    public void whenBookmark_whenServerReturns404_verifyBookmarkIsZero() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Long> observable = dvrApi.getBookmark( 17500, null );

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isEqualTo( 0 );

        server.shutdown();

    }

    @Test
    public void whenBookmark_whenServerReturns500_verifyBookmarkIsZero() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();
        setMasterBackendInSharedPreferences( server.getHostName(), server.getPort() );

        DvrApi dvrApi = new DvrApiImpl( context, sharedPreferences, client, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );

        Observable<Long> observable = dvrApi.getBookmark( 17500, null );

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .isEqualTo( 0 );

        server.shutdown();

    }

}

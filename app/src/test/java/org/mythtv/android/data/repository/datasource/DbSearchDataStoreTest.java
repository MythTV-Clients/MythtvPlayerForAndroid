package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.repository.DatabaseHelper;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class DbSearchDataStoreTest extends ApplicationTestCase {

    private static final String FAKE_SEARCH_STRING = "fake search string";
    private static final int FAKE_ID = 1;
    private static final Media FAKE_MEDIA = Media.PROGRAM;
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final DateTime FAKE_START_DATE = new DateTime();
    private static final int FAKE_PROGRAM_FLAGS = -1;
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final String FAKE_STUDIO = "fake studio";
    private static final String FAKE_CAST_MEMBERS = "fake cast members";
    private static final String FAKE_CHARACTERS = "fake characters";
    private static final String FAKE_URL = "fake url";
    private static final String FAKE_FANART_URL = "fake fanart url";
    private static final String FAKE_COVERART_URL = "fake coverart url";
    private static final String FAKE_BANNER_URL = "fake banner url";
    private static final String FAKE_PREVIEW_URL = "fake preview url";
    private static final String FAKE_CONTENT_TYPE = "fake content type";
    private static final long FAKE_DURATION = -1L;
    private static final int FAKE_PERCENT_COMPLETE = -1;
    private static final boolean FAKE_RECORDING = false;
    private static final int FAKE_LIVE_STREAM_ID = -1;
    private static final String FAKE_CREATE_HTTP_LIVE_STREAM_URL = "fake create http live stream url";
    private static final String FAKE_REMOVE_HTTP_LIVE_STREAM_URL = "fake remove http live stream url";
    private static final boolean FAKE_WATCHED = false;
    private static final String FAKE_MARK_WATCHED_URL = "fake mark watched url";

    private DbSearchDataStore dbSearchDataStore;

    private SQLiteDatabase db;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

        Context context = RuntimeEnvironment.application.getApplicationContext();
        DatabaseHelper dbOpenHelper = new DatabaseHelper( context );
        db = dbOpenHelper.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );

    }

    @Test
    public void testSearch() {

        dbSearchDataStore.search( FAKE_SEARCH_STRING );

    }

    @Test
    public void testRefreshData() {

        MediaItem fakeMediaItemEntity = createFakeSearchResultEntity();
        List<MediaItem> fakeMediaItemList = new ArrayList<>();
        fakeMediaItemList.add( fakeMediaItemEntity );

        dbSearchDataStore.refreshRecordedProgramData( fakeMediaItemList );
        Observable<List<MediaItem>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results, is( instanceOf( Observable.class ) ) );

    }

    private MediaItem createFakeSearchResultEntity() {

        MediaItem mediaItem = new MediaItem();
        mediaItem.setId( FAKE_ID );
        mediaItem.setMedia( FAKE_MEDIA );
        mediaItem.setTitle( FAKE_TITLE );
        mediaItem.setSubTitle( FAKE_SUB_TITLE );
        mediaItem.setDescription( FAKE_DESCRIPTION );
        mediaItem.setStartDate( FAKE_START_DATE );
        mediaItem.setProgramFlags( FAKE_PROGRAM_FLAGS );
        mediaItem.setSeason( FAKE_SEASON );
        mediaItem.setEpisode( FAKE_EPISODE );
        mediaItem.setStudio( FAKE_STUDIO );
        mediaItem.setCastMembers( FAKE_CAST_MEMBERS );
        mediaItem.setCharacters( FAKE_CHARACTERS );
        mediaItem.setUrl( FAKE_URL );
        mediaItem.setFanartUrl( FAKE_FANART_URL );
        mediaItem.setCoverartUrl( FAKE_COVERART_URL );
        mediaItem.setBannerUrl( FAKE_BANNER_URL );
        mediaItem.setPreviewUrl( FAKE_PREVIEW_URL );
        mediaItem.setContentType( FAKE_CONTENT_TYPE );
        mediaItem.setDuration( FAKE_DURATION );
        mediaItem.setPercentComplete( FAKE_PERCENT_COMPLETE );
        mediaItem.setRecording( FAKE_RECORDING );
        mediaItem.setLiveStreamId( FAKE_LIVE_STREAM_ID );
        mediaItem.setCreateHttpLiveStreamUrl( FAKE_CREATE_HTTP_LIVE_STREAM_URL );
        mediaItem.setRemoveHttpLiveStreamUrl( FAKE_REMOVE_HTTP_LIVE_STREAM_URL );
        mediaItem.setWatched( FAKE_WATCHED );
        mediaItem.setMarkWatchedUrl( FAKE_MARK_WATCHED_URL );

        return mediaItem;
    }

}

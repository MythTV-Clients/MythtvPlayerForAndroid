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
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.repository.DatabaseHelper;
import org.mythtv.android.domain.Media;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final boolean FAKE_WATCHED = false;
    private static final String FAKE_UPDATE_SAVED_BOOKMARK_URL = "fake update saved bookmark url";
    private static final long FAKE_BOOKMARK = -1;
    private static final String FAKE_INETREF = "fake inetref";
    private static final String FAKE_CERTIFICATION = "fake certification";
    private static final int FAKE_PARENTAL_LEVEL = -1;
    private static final String FAKE_RECORDING_GROUP = "fake recording group";

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

        MediaItemEntity fakeMediaItemEntity = createFakeSearchResultEntity();
        List<MediaItemEntity> fakeMediaItemList = new ArrayList<>();
        fakeMediaItemList.add( fakeMediaItemEntity );

        dbSearchDataStore.refreshRecordedProgramData( fakeMediaItemList );
        Observable<List<MediaItemEntity>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results, is( instanceOf( Observable.class ) ) );

    }

    private MediaItemEntity createFakeSearchResultEntity() {

        return MediaItemEntity.create(
            FAKE_ID,
            FAKE_MEDIA,
            FAKE_TITLE,
            FAKE_SUB_TITLE,
            FAKE_DESCRIPTION,
            FAKE_START_DATE,
            FAKE_PROGRAM_FLAGS,
            FAKE_SEASON,
            FAKE_EPISODE,
            FAKE_STUDIO,
            FAKE_CAST_MEMBERS,
            FAKE_CHARACTERS,
            FAKE_URL,
            FAKE_FANART_URL,
            FAKE_COVERART_URL,
            FAKE_BANNER_URL,
            FAKE_PREVIEW_URL,
            FAKE_CONTENT_TYPE,
            FAKE_DURATION,
            FAKE_PERCENT_COMPLETE,
            FAKE_RECORDING,
            FAKE_LIVE_STREAM_ID,
            FAKE_WATCHED,
            FAKE_UPDATE_SAVED_BOOKMARK_URL,
            FAKE_BOOKMARK,
            FAKE_INETREF,
            FAKE_CERTIFICATION,
            FAKE_PARENTAL_LEVEL,
            FAKE_RECORDING_GROUP,
            Collections.emptyList()
        );
    }

}

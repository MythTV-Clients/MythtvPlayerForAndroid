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
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.repository.DatabaseHelper;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by dmfrey on 10/10/15.
 */
public class DbSearchDataStoreTest extends ApplicationTestCase {

    private static final String FAKE_SEARCH_STRING = "fake search string";
    private static final int FAKE_CHAN_ID = 1;
    private static final long FAKE_START_TIME = new DateTime().getMillis();
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_CATEGORY = "fake category";
    private static final String FAKE_CALLSIGN = "fake callsign";
    private static final String FAKE_CHANNEL_NUMBER = "fake channel number";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final String FAKE_CAST_MEMBERS = "fake cast members";
    private static final String FAKE_CHARACTERS = "fake characters";
    private static final String FAKE_RATING = "fake rating";
    private static final String FAKE_STORAGE_GROUP = "fake storage group";
    private static final String FAKE_FILENAME = "fake filename";
    private static final String FAKE_HOSTNAME = "fake hostname";
    private static final String FAKE_TYPE = "fake type";

    private DbSearchDataStore dbSearchDataStore;

    SQLiteDatabase db;

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

        SearchResultEntity fakeSearchResultEntity = createFakeSearchResultEntity();
        List<SearchResultEntity> fakeSearchResultEntityList = new ArrayList<>();
        fakeSearchResultEntityList.add( fakeSearchResultEntity );

        dbSearchDataStore.refreshRecordedProgramData( fakeSearchResultEntityList );
        Observable<List<SearchResultEntity>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results, is( instanceOf( Observable.class ) ) );

    }

    private SearchResultEntity createFakeSearchResultEntity() {

        SearchResultEntity searchResultEntity = new SearchResultEntity();
        searchResultEntity.setChanId( FAKE_CHAN_ID );
        searchResultEntity.setStartTime( FAKE_START_TIME );
        searchResultEntity.setTitle( FAKE_TITLE );
        searchResultEntity.setSubTitle( FAKE_SUB_TITLE );
        searchResultEntity.setCategory( FAKE_CATEGORY );
        searchResultEntity.setCallsign( FAKE_CALLSIGN );
        searchResultEntity.setChannelNumber( FAKE_CHANNEL_NUMBER );
        searchResultEntity.setDescription( FAKE_DESCRIPTION );
        searchResultEntity.setInetref( FAKE_INETREF );
        searchResultEntity.setSeason( FAKE_SEASON );
        searchResultEntity.setEpisode( FAKE_EPISODE );
        searchResultEntity.setCastMembers( FAKE_CAST_MEMBERS );
        searchResultEntity.setCharacters( FAKE_CHARACTERS );
        searchResultEntity.setRating( FAKE_RATING );
        searchResultEntity.setStoreageGroup( FAKE_STORAGE_GROUP );
        searchResultEntity.setFilename( FAKE_FILENAME );
        searchResultEntity.setHostname( FAKE_HOSTNAME );
        searchResultEntity.setType( FAKE_TYPE );

        return searchResultEntity;
    }

}

package org.mythtv.android.data.repository.datasource;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.repository.DatabaseHelper;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class DbSearchDataStoreTest extends ApplicationTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String FAKE_SEARCH_STRING = "fake search string";

    private DbSearchDataStore dbSearchDataStore;

    private DatabaseHelper dbOpenHelpe;
    private SQLiteDatabase db;

    @Before
    public void setUp() {

        dbOpenHelpe = new DatabaseHelper( context );

    }

    @Test
    public void whenSearch() {

        db = dbOpenHelpe.getReadableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );

        dbSearchDataStore.search( FAKE_SEARCH_STRING );

    }

    @Test
    public void whenRefreshSeriesData() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshSeriesData( setupSeries() );

        Observable<List<MediaItemEntity>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results ).isInstanceOf( Observable.class );

    }

    @Test
    public void whenRefreshSeriesData_whenCollectionIsNull() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshSeriesData( null );

    }

    @Test
    public void whenRefreshSeriesData_whenCollectionIsEmpty() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshSeriesData( Collections.emptyList() );

    }

    @Test
    public void whenRefreshRecordedProgramData_verifySearchResults() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshRecordedProgramData( setupMediaItems() );

        Observable<List<MediaItemEntity>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results ).isInstanceOf( Observable.class );

    }

    @Test
    public void whenRefreshRecordedProgramData_whenCollectionIsNull() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshRecordedProgramData( null );

    }

    @Test
    public void whenRefreshRecordedProgramData_whenCollectionIsEmpty() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshRecordedProgramData( Collections.emptyList() );

    }

    @Test
    public void whenRefreshVideoData_verifySearchResults() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshVideoData( setupMediaItems() );

        Observable<List<MediaItemEntity>> results = dbSearchDataStore.search( FAKE_TITLE );
        assertThat( results ).isInstanceOf( Observable.class );

    }

    @Test
    public void whenRefreshVideoData_whenCollectionIsNull() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshVideoData( null );

    }

    @Test
    public void whenRefreshVideoData_whenCollectionIsEmpty() {

        db = dbOpenHelpe.getWritableDatabase();
        dbSearchDataStore = new DbSearchDataStore( db );
        dbSearchDataStore.refreshVideoData( Collections.emptyList() );

    }

    public List<SeriesEntity> setupSeries() {

        return Collections.singletonList( createFakeSeriesEntity() );
    }

    public List<MediaItemEntity> setupMediaItems() {

        return Collections.singletonList( createFakeMediaItemEntity() );
    }

}

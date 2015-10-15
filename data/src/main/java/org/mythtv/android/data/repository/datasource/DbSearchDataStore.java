package org.mythtv.android.data.repository.datasource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.exception.DatabaseException;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 10/8/15.
 */
public class DbSearchDataStore implements SearchDataStore {

    private static final String TAG = DbSearchDataStore.class.getSimpleName();

    private final SQLiteDatabase db;

    public DbSearchDataStore( final SQLiteDatabase db ) {

        this.db = db;

    }

    @Override
    public Observable<List<SearchResultEntity>> search( String searchString ) {

        searchString = "*" + searchString + "*";
        searchString = searchString.replaceAll( " ", "*" );

        final String query = searchString;

        return Observable.create( new Observable.OnSubscribe<List<SearchResultEntity>>() {

            @Override
            public void call( Subscriber<? super List<SearchResultEntity>> subscriber ) {
                Log.d( TAG, "search.call : enter" );

                Cursor cursor = null;

                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

                try {

                    List<SearchResultEntity> searchResultEntities = new ArrayList<>();

                    builder.setTables( "search_result" );

                    String selection = SearchResultEntity.SQL_SELECT_MATCH;
                    String[] selectionArgs = new String[] { query };

                    SearchResultEntity searchResultEntity;
                    cursor = builder.query( db, null, selection, selectionArgs, null, null, "START_TIME DESC, TITLE" );
                    while( cursor.moveToNext() ) {

                        searchResultEntity = new SearchResultEntity();
                        searchResultEntity.setChanId( cursor.getInt( cursor.getColumnIndex( "CHAN_ID" ) ) );
                        searchResultEntity.setStartTime(cursor.getLong(cursor.getColumnIndex("START_TIME")));
                        searchResultEntity.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                        searchResultEntity.setSubTitle(cursor.getString(cursor.getColumnIndex("SUB_TITLE")));
                        searchResultEntity.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
                        searchResultEntity.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
                        searchResultEntity.setInetref(cursor.getString(cursor.getColumnIndex("INETREF")));
                        searchResultEntity.setChanId(cursor.getInt(cursor.getColumnIndex("CHAN_ID")));
                        searchResultEntity.setChannelNumber(cursor.getString(cursor.getColumnIndex("CHAN_NUM")));
                        searchResultEntity.setCallsign(cursor.getString(cursor.getColumnIndex("CALLSIGN")));
                        searchResultEntity.setSeason(cursor.getInt(cursor.getColumnIndex("EPISODE")));
                        searchResultEntity.setSeason( cursor.getInt( cursor.getColumnIndex( "SEASON" ) ) );
                        searchResultEntity.setCastMembers(cursor.getString(cursor.getColumnIndex("CAST_MEMBER_NAMES")));
                        searchResultEntity.setCharacters(cursor.getString(cursor.getColumnIndex("CAST_MEMBER_CHARACTERS")));
                        searchResultEntity.setType(cursor.getString(cursor.getColumnIndex("TYPE")));

                        Log.d( TAG, "search.call : searchResultEntity=" + searchResultEntity.toString() );
                        searchResultEntities.add( searchResultEntity );

                    }
                    cursor.close();

                    subscriber.onNext( searchResultEntities );
                    subscriber.onCompleted();

                } catch( Exception e ) {
                    Log.e( TAG, "search.call : error", e );

                    subscriber.onError( new DatabaseException( e.getCause() ) );

                }

                Log.d( TAG, "search.call : exit" );
            }

        });

    }

    @Override
    public void refreshRecordedProgramData(Collection<SearchResultEntity> searchResultEntityCollection) {
        Log.d( TAG, "refreshRecordedProgramData : enter" );

        if( null != searchResultEntityCollection && !searchResultEntityCollection.isEmpty() ) {

            db.beginTransaction();

            Log.d( TAG, "refreshRecordedProgramData : deleting old recordings" );
            db.delete( SearchResultEntity.TABLE_NAME, "type = ?", new String[] { SearchResult.Type.RECORDING.name() } );

            SQLiteStatement statement = db.compileStatement( SearchResultEntity.SQL_INSERT );
            for( SearchResultEntity searchResultEntity : searchResultEntityCollection ) {
                Log.d(TAG, "refreshRecordedProgramData : searchResultEntity=" + searchResultEntity.toString());

                statement.clearBindings();
                statement.bindLong( 1, searchResultEntity.getStartTime() );
                statement.bindString( 2, searchResultEntity.getTitle() );
                statement.bindString( 3, searchResultEntity.getSubTitle() );
                statement.bindString( 4, searchResultEntity.getCategory() );
                statement.bindString( 5, searchResultEntity.getDescription() );
                statement.bindString( 6, searchResultEntity.getInetref() );
                statement.bindLong( 7, searchResultEntity.getSeason() );
                statement.bindLong( 8, searchResultEntity.getEpisode() );
                statement.bindLong( 9, searchResultEntity.getChanId() );
                statement.bindString( 10, searchResultEntity.getChannelNumber() );
                statement.bindString( 11, searchResultEntity.getCallsign() );
                statement.bindString( 12, null != searchResultEntity.getCastMembers() ? searchResultEntity.getCastMembers() : "" );
                statement.bindString( 13, null != searchResultEntity.getCharacters() ? searchResultEntity.getCharacters() : "" );
                statement.bindString( 14, null != searchResultEntity.getRating() ? searchResultEntity.getRating() : "" );
                statement.bindString( 15, searchResultEntity.getType() );
                statement.executeInsert();

            }
            db.setTransactionSuccessful();
            db.endTransaction();

        }

        Log.d( TAG, "refreshRecordedProgramData : exit" );
    }

}

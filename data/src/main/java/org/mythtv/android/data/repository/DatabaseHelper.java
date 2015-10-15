package org.mythtv.android.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mythtv.android.data.entity.SearchResultEntity;

/**
 * Created by dmfrey on 10/8/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "mythtvdb";
    private static final int DATABASE_VERSION = 18;

    public DatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onOpen( SQLiteDatabase db ) {
        super.onOpen( db );
        Log.i( TAG, "onOpen : enter" );

        if( !db.isReadOnly() ) {
            Log.i( TAG, "onOpen : turn on foreign keys" );

//            db.execSQL( "PRAGMA foreign_keys = ON;" );
            db.execSQL( "PRAGMA encoding = \"UTF-8\";" );

        }

        Log.i(TAG, "onOpen : exit");
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.i( TAG, "onCreate : enter" );

        dropTables( db );

        createSearchResultTable( db );
//        createTableLiveStreams(db);
//        createTableTitleInfos(db);
//        createTablePrograms(db);
//        createTableVideos(db);
//        createTableVideoDirs(db);

        Log.i( TAG, "onCreate : exit" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        Log.i(TAG, "onUpgrade : enter");

        if( oldVersion < DATABASE_VERSION ) {
            Log.i( TAG, "onUpgrade : upgrading to db version " + DATABASE_VERSION );

            onCreate( db );

        }

        Log.i( TAG, "onUpgrade : exit" );
    }

    private void dropTables( SQLiteDatabase db ) {
        Log.v(TAG, "dropTables : enter");

//        String dropLiveStreams = LiveStreamConstants.DROP_TABLE;
//        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
//            Log.v( TAG, "dropTable : dropLiveStreams=" + dropLiveStreams );
//        }
//        db.execSQL(dropLiveStreams);
//
//        String dropTitleInfos = TitleInfoConstants.DROP_TABLE;
//        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
//            Log.v( TAG, "dropTable : dropTitleInfos=" + dropTitleInfos );
//        }
//        db.execSQL( dropTitleInfos );
//
//        String dropPrograms = ProgramConstants.DROP_TABLE;
//        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
//            Log.v( TAG, "dropTable : dropPrograms=" + dropPrograms );
//        }
//        db.execSQL( dropPrograms );
//
//        String dropVideos = VideoConstants.DROP_TABLE;
//        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
//            Log.v( TAG, "dropTable : dropVideos=" + dropVideos );
//        }
//        db.execSQL( dropVideos );
//
//        String dropVideoDirs = VideoDirConstants.DROP_TABLE;
//        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
//            Log.v( TAG, "dropTable : dropVideoDirs=" + dropVideoDirs );
//        }
//        db.execSQL( dropVideoDirs );

        Log.v( TAG, "dropTables : exit" );
    }

    private void createSearchResultTable( SQLiteDatabase db) {
        Log.v( TAG, "createSearchResultTable : enter" );

        String sql = SearchResultEntity.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createSearchResultTable : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createSearchResultTable : exit" );
    }

}

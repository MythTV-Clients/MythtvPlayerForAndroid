package org.mythtv.android.library.persistence.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;
import org.mythtv.android.library.persistence.domain.dvr.ProgramFtsConstants;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;

/**
 * Created by dmfrey on 1/25/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "mythtvdb";
    private static final int DATABASE_VERSION = 7;

    public DatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onOpen( SQLiteDatabase db ) {
        super.onOpen( db );
        Log.i( TAG, "onOpen : enter" );

        if( !db.isReadOnly() ) {
            Log.i( TAG, "onOpen : turn on foreign keys" );

            db.execSQL( "PRAGMA foreign_keys = ON;" );

        }

        Log.i(TAG, "onOpen : exit");
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.i(TAG, "onCreate : enter");

        dropTables(db);

        createTableLiveStreams( db );
        createTableTitleInfos( db );
        createTablePrograms( db );
        createTableProgramsFts(db);

        Log.i( TAG, "onCreate : exit" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        Log.i( TAG, "onUpgrade : enter" );

        if( oldVersion < DATABASE_VERSION ) {
            Log.i( TAG, "onUpgrade : upgrading to db version " + DATABASE_VERSION );

            onCreate( db );

        }

        Log.i( TAG, "onUpgrade : exit" );
    }

    private void dropTables( SQLiteDatabase db ) {
        Log.v( TAG, "dropTables : enter" );

        String dropLiveStreams = LiveStreamConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropLiveStreams=" + dropLiveStreams );
        }
        db.execSQL(dropLiveStreams);

        String dropTitleInfos = TitleInfoConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropTitleInfos=" + dropTitleInfos );
        }
        db.execSQL( dropTitleInfos );

        String dropPrograms = ProgramConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropPrograms=" + dropPrograms );
        }
        db.execSQL( dropPrograms );

        String dropProgramsFts = ProgramFtsConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropProgramsFts=" + dropProgramsFts );
        }
        db.execSQL( dropProgramsFts );

        Log.v( TAG, "dropTables : exit" );
    }

    private void createTableLiveStreams( SQLiteDatabase db ) {
        Log.v(TAG, "createTableLiveStreams : enter");

        String sql = LiveStreamConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTableLiveStreams : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTableLiveStreams : exit" );
    }

    private void createTableTitleInfos( SQLiteDatabase db ) {
        Log.v( TAG, "createTableTitleInfos : enter" );

        String sql = TitleInfoConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTableTitleInfos : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTableTitleInfos : exit" );
    }

    private void createTablePrograms( SQLiteDatabase db ) {
        Log.v( TAG, "createTablePrograms : enter" );

        String sql = ProgramConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTablePrograms : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTablePrograms : exit" );
    }

    private void createTableProgramsFts( SQLiteDatabase db ) {
        Log.v( TAG, "createTableProgramsFts : enter" );

        String sql = ProgramFtsConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTableProgramsFts : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTableProgramsFts : exit" );
    }

}

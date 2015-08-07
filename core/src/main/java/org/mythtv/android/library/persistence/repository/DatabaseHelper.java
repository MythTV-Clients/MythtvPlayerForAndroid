/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.library.persistence.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;
import org.mythtv.android.library.persistence.domain.videoDir.VideoDirConstants;

/*
 * Created by dmfrey on 1/25/15.
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

            db.execSQL( "PRAGMA foreign_keys = ON;" );
            db.execSQL( "PRAGMA encoding = \"UTF-8\";" );

        }

        Log.i(TAG, "onOpen : exit");
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.i( TAG, "onCreate : enter" );

        dropTables(db);

        createTableLiveStreams(db);
        createTableTitleInfos(db);
        createTablePrograms(db);
        createTableVideos(db);
        createTableVideoDirs(db);

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

        String dropVideos = VideoConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropVideos=" + dropVideos );
        }
        db.execSQL( dropVideos );

        String dropVideoDirs = VideoDirConstants.DROP_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "dropTable : dropVideoDirs=" + dropVideoDirs );
        }
        db.execSQL( dropVideoDirs );

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

    private void createTableVideos( SQLiteDatabase db ) {
        Log.v( TAG, "createTableVideos : enter" );

        String sql = VideoConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTableVideos : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTableVideos : exit" );
    }

    private void createTableVideoDirs( SQLiteDatabase db ) {
        Log.v( TAG, "createTableVideoDirs : enter" );

        String sql = VideoDirConstants.CREATE_TABLE;
        if( Log.isLoggable( TAG, Log.VERBOSE ) ) {
            Log.v( TAG, "createTableVideoDirs : sql=" + sql );
        }
        db.execSQL( sql );

        Log.v( TAG, "createTableVideoDirs : exit" );
    }

}

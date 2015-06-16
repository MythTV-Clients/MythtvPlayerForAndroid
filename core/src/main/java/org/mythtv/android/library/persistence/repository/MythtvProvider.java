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

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;

import java.util.ArrayList;

/*
 * Created by dmfrey on 1/25/15.
 */
public class MythtvProvider extends ContentProvider {

    private static final String TAG = MythtvProvider.class.getSimpleName();

    public static final String AUTHORITY = "org.mythtv.android.provider";

    private static final UriMatcher URI_MATCHER;

    private DatabaseHelper mOpenHelper;
    private SQLiteDatabase db;

    static {

        URI_MATCHER = new UriMatcher( UriMatcher.NO_MATCH );

        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME, LiveStreamConstants.ALL );
        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME + "/#",  LiveStreamConstants.SINGLE );
        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME + "/recordings", LiveStreamConstants.ALL_RECORDINGS );
        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME + "/recordings/#",  LiveStreamConstants.SINGLE_RECORDING );
        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME + "/videos", LiveStreamConstants.ALL_VIDEOS );
        URI_MATCHER.addURI( AUTHORITY, LiveStreamConstants.TABLE_NAME + "/videos/#",  LiveStreamConstants.SINGLE_VIDEO );

        URI_MATCHER.addURI( AUTHORITY, TitleInfoConstants.TABLE_NAME, TitleInfoConstants.ALL );
        URI_MATCHER.addURI( AUTHORITY, TitleInfoConstants.TABLE_NAME + "/#",  TitleInfoConstants.SINGLE );

        URI_MATCHER.addURI( AUTHORITY, ProgramConstants.TABLE_NAME, ProgramConstants.ALL );
        URI_MATCHER.addURI( AUTHORITY, ProgramConstants.TABLE_NAME + "/#",  ProgramConstants.SINGLE );
        URI_MATCHER.addURI( AUTHORITY, ProgramConstants.TABLE_NAME + "/fts", ProgramConstants.ALL_FTS );
        URI_MATCHER.addURI( AUTHORITY, ProgramConstants.TABLE_NAME + "/recording_groups", ProgramConstants.ALL_RECORDING_GROUPS );
        URI_MATCHER.addURI( AUTHORITY, ProgramConstants.TABLE_NAME + "/titles", ProgramConstants.ALL_TITLES );

        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME, VideoConstants.ALL );
        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME + "/#",  VideoConstants.SINGLE );
        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME + "/fts", VideoConstants.ALL_FTS );
        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME + "/id/#", VideoConstants.SINGLE_ID );
        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME + "/Television/Titles", VideoConstants.ALL_TV_TITLES );
        URI_MATCHER.addURI( AUTHORITY, VideoConstants.TABLE_NAME + "/Television/Titles/Season", VideoConstants.ALL_TV_SEASON );

    }

    @Override
    public boolean onCreate() {
        Log.i( TAG, "onCreate : enter" );

        mOpenHelper = new DatabaseHelper( getContext() );

        Log.i( TAG, "onCreate : exit" );
        return true;
    }

    @Override
    public String getType( Uri uri ) {
//        Log.v( TAG, "getType : enter" );

        switch( URI_MATCHER.match( uri ) ) {

            case LiveStreamConstants.ALL :
            case LiveStreamConstants.ALL_RECORDINGS :
            case LiveStreamConstants.ALL_VIDEOS :
                return LiveStreamConstants.CONTENT_TYPE;

            case LiveStreamConstants.SINGLE :
            case LiveStreamConstants.SINGLE_RECORDING :
            case LiveStreamConstants.SINGLE_VIDEO :
                return LiveStreamConstants.CONTENT_ITEM_TYPE;

            case TitleInfoConstants.ALL :
                return TitleInfoConstants.CONTENT_TYPE;

            case TitleInfoConstants.SINGLE :
                return TitleInfoConstants.CONTENT_ITEM_TYPE;

            case ProgramConstants.ALL :
            case ProgramConstants.ALL_FTS :
            case ProgramConstants.ALL_RECORDING_GROUPS :
            case ProgramConstants.ALL_TITLES :
                return ProgramConstants.CONTENT_TYPE;

            case ProgramConstants.SINGLE :
                return ProgramConstants.CONTENT_ITEM_TYPE;

            case VideoConstants.ALL :
            case VideoConstants.ALL_FTS :
            case VideoConstants.ALL_TV_TITLES :
            case VideoConstants.ALL_TV_SEASON :
                return VideoConstants.CONTENT_TYPE;

            case VideoConstants.SINGLE :
            case VideoConstants.SINGLE_ID :
                return VideoConstants.CONTENT_ITEM_TYPE;

            default :
                throw new IllegalArgumentException( "Unknown URI : " + uri );

        }

    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {
//        Log.v( TAG, "query : enter" );

        db = mOpenHelper.getReadableDatabase();

        Cursor cursor = null;

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch( URI_MATCHER.match( uri ) ) {

            case LiveStreamConstants.ALL :
//                Log.v( TAG, "query : querying for all live streams" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case LiveStreamConstants.ALL_RECORDINGS :
//                Log.v( TAG, "query : querying for all recordings" );

                selection = LiveStreamConstants.FIELD_RECORDED_ID + " IS NOT NULL OR (" + LiveStreamConstants.FIELD_CHAN_ID + " IS NOT NULL AND " + LiveStreamConstants.FIELD_START_TIME + " IS NOT NULL)" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case LiveStreamConstants.ALL_VIDEOS :
//                Log.v( TAG, "query : querying for all videos" );

                selection = LiveStreamConstants.FIELD_VIDEO_ID + " IS NOT NULL" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case LiveStreamConstants.SINGLE :
//                Log.v( TAG, "query : querying for single live stream" );

                selection = LiveStreamConstants._ID + " = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case LiveStreamConstants.SINGLE_RECORDING :
//                Log.v( TAG, "query : querying for single recording" );

                selection = LiveStreamConstants.FIELD_RECORDED_ID + " = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case LiveStreamConstants.SINGLE_VIDEO :
//                Log.v( TAG, "query : querying for single video" );

                selection = LiveStreamConstants.FIELD_VIDEO_ID + " = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( LiveStreamConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case TitleInfoConstants.ALL :
//                Log.v( TAG, "query : querying for all title infos" );

                if( null == sortOrder ) {
                    sortOrder = TitleInfoConstants.FIELD_SORT + ", " + TitleInfoConstants.FIELD_TITLE_SORT;
                }

                cursor = db.query( TitleInfoConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case TitleInfoConstants.SINGLE :
//                Log.v( TAG, "query : querying for single title info" );

                selection = TitleInfoConstants._ID + " = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( TitleInfoConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case ProgramConstants.ALL :
//                Log.v( TAG, "query : querying for all programs" );

                cursor = db.query( ProgramConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case ProgramConstants.SINGLE :
//                Log.v( TAG, "query : querying for single program" );

                selection = "rowid = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( ProgramConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case ProgramConstants.ALL_FTS :
//                Log.v( TAG, "query : querying for all programs" );

                builder.setTables( ProgramConstants.TABLE_NAME );

                cursor = builder.query( db, null, selection, selectionArgs, null, null, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case ProgramConstants.ALL_RECORDING_GROUPS :
//                Log.v( TAG, "query : querying for all programs" );

                cursor = db.query( true, ProgramConstants.TABLE_NAME, projection, selection, selectionArgs, ProgramConstants.FIELD_RECORDING_REC_GROUP, null, sortOrder, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case ProgramConstants.ALL_TITLES :
//                Log.v( TAG, "query : querying for all programs" );

                cursor = db.query( true, ProgramConstants.TABLE_NAME, projection, selection, selectionArgs, ProgramConstants.FIELD_PROGRAM_TITLE + ", " + ProgramConstants.FIELD_PROGRAM_INETREF, null, sortOrder, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.ALL :
//                Log.v( TAG, "query : querying for all videos" );

                cursor = db.query( VideoConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.SINGLE :
//                Log.v( TAG, "query : querying for single video" );

                selection = "rowid = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( VideoConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.ALL_FTS :
//                Log.v( TAG, "query : querying for all videos" );

                builder.setTables( VideoConstants.TABLE_NAME );

                cursor = builder.query( db, null, selection, selectionArgs, null, null, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.SINGLE_ID :
//                Log.v( TAG, "query : querying for single video" );

                selection = VideoConstants.FIELD_VIDEO_ID + " = " + uri.getLastPathSegment() + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                cursor = db.query( VideoConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.ALL_TV_TITLES :
//                Log.v( TAG, "query : querying for all videos" );

                cursor = db.query( true, VideoConstants.TABLE_NAME, projection, selection, selectionArgs, VideoConstants.FIELD_VIDEO_TITLE, null, sortOrder, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            case VideoConstants.ALL_TV_SEASON :
//                Log.v( TAG, "query : querying for all videos" );

                cursor = db.query( true, VideoConstants.TABLE_NAME, projection, selection, selectionArgs, VideoConstants.FIELD_VIDEO_TITLE + ", " + VideoConstants.FIELD_VIDEO_SEASON, null, sortOrder, null );

                cursor.setNotificationUri( getContext().getContentResolver(), uri );

                return cursor;

            default :
                throw new IllegalArgumentException( "Unknown URI : " + uri );

        }

    }

    @Override
    public Uri insert( Uri uri, ContentValues values ) {
//        Log.v( TAG, "insert : enter" );

        db = mOpenHelper.getWritableDatabase();

        Uri newUri = null;

        switch( URI_MATCHER.match( uri ) ) {

            case LiveStreamConstants.ALL:
//                Log.v( TAG, "insert : inserting new live stream" );

                newUri = ContentUris.withAppendedId( LiveStreamConstants.CONTENT_URI, db.insertWithOnConflict( LiveStreamConstants.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE ) );

                getContext().getContentResolver().notifyChange( newUri, null );

                return newUri;

            case TitleInfoConstants.ALL:
//                Log.v( TAG, "insert : inserting new title info" );

                newUri = ContentUris.withAppendedId( TitleInfoConstants.CONTENT_URI, db.insertWithOnConflict( TitleInfoConstants.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE ) );

                getContext().getContentResolver().notifyChange( newUri, null );

                return newUri;

            case ProgramConstants.ALL:
//                Log.v( TAG, "insert : inserting new program" );

                newUri = ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, db.insertWithOnConflict( ProgramConstants.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE ) );

                getContext().getContentResolver().notifyChange( newUri, null );

                return newUri;

            case VideoConstants.ALL:
//                Log.v( TAG, "insert : inserting new video" );

                newUri = ContentUris.withAppendedId( VideoConstants.CONTENT_URI, db.insertWithOnConflict( VideoConstants.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE ) );

                getContext().getContentResolver().notifyChange( newUri, null );

                return newUri;

            default:
                throw new IllegalArgumentException( "Unknown URI " + uri );

        }

    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs ) {
//        Log.v( TAG, "delete : enter" );

        db = mOpenHelper.getWritableDatabase();

        int deleted = 0;

        switch( URI_MATCHER.match( uri ) ) {

            case LiveStreamConstants.ALL:

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case LiveStreamConstants.SINGLE:

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, LiveStreamConstants._ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case LiveStreamConstants.ALL_RECORDINGS:

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case LiveStreamConstants.SINGLE_RECORDING:

                selection = LiveStreamConstants.FIELD_RECORDED_ID + " IS NOT NULL OR (" + LiveStreamConstants.FIELD_CHAN_ID + " IS NOT NULL AND " + LiveStreamConstants.FIELD_START_TIME + " IS NOT NULL)" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, LiveStreamConstants.FIELD_RECORDED_ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case LiveStreamConstants.ALL_VIDEOS:

                selection = LiveStreamConstants.FIELD_VIDEO_ID + " IS NOT NULL" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case LiveStreamConstants.SINGLE_VIDEO:

                deleted = db.delete( LiveStreamConstants.TABLE_NAME, LiveStreamConstants.FIELD_VIDEO_ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case TitleInfoConstants.ALL:

                deleted = db.delete( TitleInfoConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case TitleInfoConstants.SINGLE:

                deleted = db.delete( TitleInfoConstants.TABLE_NAME, TitleInfoConstants._ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case ProgramConstants.ALL:

                deleted = db.delete( ProgramConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case ProgramConstants.SINGLE:

                deleted = db.delete( ProgramConstants.TABLE_NAME,
                        "rowid ="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case VideoConstants.ALL:

                deleted = db.delete( VideoConstants.TABLE_NAME, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            case VideoConstants.SINGLE:

                deleted = db.delete( VideoConstants.TABLE_NAME,
                        "rowid ="
                                + uri.getLastPathSegment()
                                + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" )
                        , selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return deleted;

            default:
                throw new IllegalArgumentException( "Unknown URI " + uri );

        }

    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs ) {
//        Log.v( TAG, "update : enter" );

        db = mOpenHelper.getWritableDatabase();

        int affected = 0;

        switch( URI_MATCHER.match( uri ) ) {

            case LiveStreamConstants.ALL:

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case LiveStreamConstants.SINGLE:

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, LiveStreamConstants._ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case LiveStreamConstants.ALL_RECORDINGS:

                selection = LiveStreamConstants.FIELD_RECORDED_ID + " IS NOT NULL OR (" + LiveStreamConstants.FIELD_CHAN_ID + " IS NOT NULL AND " + LiveStreamConstants.FIELD_START_TIME + " IS NOT NULL)" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case LiveStreamConstants.SINGLE_RECORDING:

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, LiveStreamConstants.FIELD_RECORDED_ID
                                + "="
                                + uri.getLastPathSegment()
                                + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case LiveStreamConstants.ALL_VIDEOS:

                selection = LiveStreamConstants.FIELD_VIDEO_ID + " IS NOT NULL" + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" );

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case LiveStreamConstants.SINGLE_VIDEO:

                affected = db.update( LiveStreamConstants.TABLE_NAME, values, LiveStreamConstants.FIELD_VIDEO_ID
                        + "="
                        + uri.getLastPathSegment()
                        + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case TitleInfoConstants.ALL:

                affected = db.update( TitleInfoConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case TitleInfoConstants.SINGLE:

                affected = db.update( TitleInfoConstants.TABLE_NAME, values, TitleInfoConstants._ID
                                + "="
                                + uri.getLastPathSegment()
                                + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case ProgramConstants.ALL:

                affected = db.update( ProgramConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case ProgramConstants.SINGLE:

                affected = db.update( ProgramConstants.TABLE_NAME, values,
                                "rowid ="
                                + uri.getLastPathSegment()
                                + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case VideoConstants.ALL:

                affected = db.update( VideoConstants.TABLE_NAME, values, selection, selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            case VideoConstants.SINGLE:

                affected = db.update( VideoConstants.TABLE_NAME, values,
                        "rowid ="
                                + uri.getLastPathSegment()
                                + ( !TextUtils.isEmpty( selection ) ? " AND (" + selection + ")" : "" ),
                        selectionArgs );

                getContext().getContentResolver().notifyChange( uri, null );

                return affected;

            default:
                throw new IllegalArgumentException( "Unknown URI " + uri );

        }

    }

    @Override
    public ContentProviderResult[] applyBatch( ArrayList<ContentProviderOperation> operations )	 throws OperationApplicationException {
//        Log.v( TAG, "applyBatch : enter" );

        db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {

            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[ numOperations ];
            for( int i = 0; i < numOperations; i++ ) {
                results[ i ] = operations.get( i ).apply( this, results, i );
            }
            db.setTransactionSuccessful();

//            Log.v( TAG, "applyBatch : exit" );
            return results;

        } finally {
            db.endTransaction();
        }

    }

}

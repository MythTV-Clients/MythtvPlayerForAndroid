/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.data.repository.datasource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.exception.DatabaseException;
import org.mythtv.android.domain.Media;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/8/15.
 */
@SuppressWarnings( "PMD" )
public class DbSearchDataStore implements SearchDataStore {

    private static final String TAG = DbSearchDataStore.class.getSimpleName();

    private final SQLiteDatabase db;

    public DbSearchDataStore( final SQLiteDatabase db ) {

        this.db = db;

    }

    @Override
    public Observable<List<MediaItemEntity>> search( String searchString ) {
        Log.d( TAG, "search : enter" );

        String search = "*" + searchString + "*";
        search = search.replaceAll( " ", "*" );

        final String query = search;
        Log.d( TAG, "search : query=" + query );

        return Observable.create( new Observable.OnSubscribe<List<MediaItemEntity>>() {

            @Override
            public void call( Subscriber<? super List<MediaItemEntity>> subscriber ) {
                Log.d( TAG, "search.call : enter" );

                Cursor cursor;

                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

                try {

                    List<MediaItemEntity> mediaItems = new ArrayList<>();

                    builder.setTables( MediaItemEntity.TABLE_NAME );

                    String selection = MediaItemEntity.SQL_SELECT_MATCH;
                    String[] selectionArgs = new String[] { query };

                    MediaItemEntity mediaItem;
                    cursor = builder.query( db, null, selection, selectionArgs, null, null, MediaItemEntity.FIELD_START_DATE + " DESC, " + MediaItemEntity.FIELD_TITLE );
                    while( cursor.moveToNext() ) {

                        mediaItem = MediaItemEntity.create(
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_ID ) ),
                            Media.valueOf( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_MEDIA ) ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_TITLE ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_SUBTITLE ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_DESCRIPTION ) ),
                            new DateTime( cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_START_DATE ) ) ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_PROGRAM_FLAGS ) ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_SEASON ) ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_EPISODE ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_STUDIO ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CAST_MEMBERS ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CHARACTERS ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_URL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_FANART_URL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_COVERART_URL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_BANNER_URL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_PREVIEW_URL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CONTENT_TYPE ) ),
                            cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_DURATION ) ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_LIVE_STREAM_PERCENT_COMPLETE ) ),
                            ( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_RECORDING ) ) != 0 ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_LIVE_STREAM_ID ) ),
                            ( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_WATCHED_STATUS ) ) != 0 ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_UPDATE_SAVED_BOOKMARK_URL ) ),
                            cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_BOOKMARK ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_INETREF ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CERTIFICATION ) ),
                            cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_PARENTAL_LEVEL ) ),
                            cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_RECORDING_GROUP ) ),
                            Collections.emptyList()
                        );

//                        Log.d( TAG, "search.call : searchResultEntity=" + searchResultEntity.toString() );
                        mediaItems.add( mediaItem );

                    }
                    cursor.close();

                    subscriber.onNext( mediaItems );
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
    public void refreshTitleInfoData( Collection<SeriesEntity> seriesEntityCollection ) {
        Log.d( TAG, "refreshTitleInfoData : enter" );

        if( null == seriesEntityCollection || seriesEntityCollection.isEmpty() ) {
            Log.d( TAG, "refreshTitleInfoData : seriesEntityCollection is empty" );

            return;

        } else {
            Log.d( TAG, "refreshTitleInfoData : seriesEntityCollection is not empty" );

            db.beginTransaction();

            List<String> values = new ArrayList<>();
            values.add( Media.PROGRAM.name() );

            List<String> parameters = new ArrayList<>();
            for( SeriesEntity entity : seriesEntityCollection ) {

                parameters.add( "?" );
                values.add( entity.title() );

            }

            db.delete( MediaItemEntity.TABLE_NAME, MediaItemEntity.FIELD_MEDIA + " = ? and not " + MediaItemEntity.FIELD_TITLE + " in (" + TextUtils.join( ",", parameters ) + ")", values.toArray( new String[ values.size() ] ) );

            db.setTransactionSuccessful();
            db.endTransaction();

        }

        Log.d( TAG, "refreshTitleInfoData : exit" );
    }

    @Override
    public void refreshRecordedProgramData( Collection<MediaItemEntity> mediaItemEntityCollection ) {
        Log.d( TAG, "refreshRecordedProgramData : enter" );

        if( null == mediaItemEntityCollection || mediaItemEntityCollection.isEmpty() ) {
            Log.d( TAG, "refreshRecordedProgramData : mediaItemEntityCollection is empty" );

            return;

        } else {
            Log.d( TAG, "refreshRecordedProgramData : mediaItemEntityCollection is not empty" );

            db.beginTransaction();

            Observable.from( mediaItemEntityCollection )
                    .distinct( MediaItemEntity::title )
                    .flatMap( mediaItemEntity -> Observable.just( mediaItemEntity.title() ) )
                    .doOnNext( title -> db.delete( MediaItemEntity.TABLE_NAME, MediaItemEntity.FIELD_MEDIA + " = ? and " + MediaItemEntity.FIELD_TITLE + " = ?", new String[] { Media.PROGRAM.name(), title } ) )
                    .doOnNext( title -> Log.d( TAG, "refreshRecordedPrograms : deleting old recordings for title=" + title ) )
                    .subscribe();

            processCollection( mediaItemEntityCollection );
        }

        Log.d( TAG, "refreshRecordedProgramData : exit" );
    }

    @Override
    public void refreshVideoData( Collection<MediaItemEntity> mediaItemEntityCollection ) {
        Log.d( TAG, "refreshVideoData : enter" );

        if( null == mediaItemEntityCollection || mediaItemEntityCollection.isEmpty() ) {
            Log.d( TAG, "refreshVideoData : mediaItemEntityCollection is empty" );

            return;

        } else {
            Log.d( TAG, "refreshVideoData : mediaItemEntityCollection is not empty" );

            db.beginTransaction();

            Log.d( TAG, "refreshVideoData : deleting old videos" );
            db.delete( MediaItemEntity.TABLE_NAME, MediaItemEntity.FIELD_MEDIA + " = ?", new String[] { Media.VIDEO.name() } );

            processCollection( mediaItemEntityCollection );
        }

        Log.d( TAG, "refreshVideoData : exit" );
    }

    private void processCollection( Collection<MediaItemEntity> mediaItemEntityCollection ) {

        SQLiteStatement statement = db.compileStatement( MediaItemEntity.SQL_INSERT );
        for( MediaItemEntity mediaItemEntity : mediaItemEntityCollection ) {
//            Log.d(TAG, "processCollection : searchResultEntity=" + searchResultEntity.toString());

            statement.clearBindings();
            statement.bindLong( 1, mediaItemEntity.id() );
            statement.bindString( 2, mediaItemEntity.media().name() );
            statement.bindString( 3, null == mediaItemEntity.title() ? "" : mediaItemEntity.title() );
            statement.bindString( 4, null == mediaItemEntity.subTitle() ? "" : mediaItemEntity.subTitle() );
            statement.bindString( 5, null == mediaItemEntity.description() ? "" : mediaItemEntity.description() );
            statement.bindLong( 6, null == mediaItemEntity.startDate() ? -1 : mediaItemEntity.startDate().getMillis() );
            statement.bindLong( 7, mediaItemEntity.programFlags() );
            statement.bindLong( 8, mediaItemEntity.season() );
            statement.bindLong( 9, mediaItemEntity.episode() );
            statement.bindString( 10, null == mediaItemEntity.studio() ? "" : mediaItemEntity.studio() );
            statement.bindString( 11, null == mediaItemEntity.castMembers() ? "" : mediaItemEntity.castMembers() );
            statement.bindString( 12, null == mediaItemEntity.characters() ? "" : mediaItemEntity.characters() );
            statement.bindString( 13, null == mediaItemEntity.url() ? "" : mediaItemEntity.url() );
            statement.bindString( 14, null == mediaItemEntity.fanartUrl() ? "" : mediaItemEntity.fanartUrl() );
            statement.bindString( 15, null == mediaItemEntity.coverartUrl() ? "" : mediaItemEntity.coverartUrl() );
            statement.bindString( 16, null == mediaItemEntity.bannerUrl() ? "" : mediaItemEntity.bannerUrl() );
            statement.bindString( 17, null == mediaItemEntity.previewUrl() ? "" : mediaItemEntity.previewUrl() );
            statement.bindString( 18, null == mediaItemEntity.contentType() ? "" : mediaItemEntity.contentType() );
            statement.bindLong( 19, mediaItemEntity.duration() );
            statement.bindLong( 20, mediaItemEntity.percentComplete() );
            statement.bindLong( 21, mediaItemEntity.recording() ? 1 : 0 );
            statement.bindLong( 22, mediaItemEntity.liveStreamId() );
            statement.bindLong( 23, mediaItemEntity.watched() ? 1 : 0 );
            statement.bindString( 24, null == mediaItemEntity.updateSavedBookmarkUrl() ? "" : mediaItemEntity.updateSavedBookmarkUrl() );
            statement.bindLong( 25, mediaItemEntity.bookmark() );
            statement.bindString( 26, null == mediaItemEntity.inetref() ? "" : mediaItemEntity.inetref() );
            statement.bindString( 27, null == mediaItemEntity.certification() ? "" : mediaItemEntity.certification() );
            statement.bindLong( 28, mediaItemEntity.parentalLevel() );
            statement.bindString( 29, null == mediaItemEntity.recordingGroup() ? "" : mediaItemEntity.recordingGroup() );
            statement.executeInsert();

        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

}

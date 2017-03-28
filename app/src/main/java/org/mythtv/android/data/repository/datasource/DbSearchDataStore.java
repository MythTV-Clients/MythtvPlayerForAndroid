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
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.exception.DatabaseException;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;

import java.util.ArrayList;
import java.util.Collection;
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
public class DbSearchDataStore implements SearchDataStore {

    private static final String TAG = DbSearchDataStore.class.getSimpleName();

    private final SQLiteDatabase db;

    public DbSearchDataStore( final SQLiteDatabase db ) {

        this.db = db;

    }

    @Override
    public Observable<List<MediaItem>> search( String searchString ) {
        Log.d( TAG, "search : enter" );

        String search = "*" + searchString + "*";
        search = search.replaceAll( " ", "*" );

        final String query = search;
        Log.d( TAG, "search : query=" + query );

        return Observable.create( new Observable.OnSubscribe<List<MediaItem>>() {

            @Override
            public void call( Subscriber<? super List<MediaItem>> subscriber ) {
                Log.d( TAG, "search.call : enter" );

                Cursor cursor;

                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

                try {

                    List<MediaItem> mediaItems = new ArrayList<>();

                    builder.setTables( MediaItemEntity.TABLE_NAME );

                    String selection = MediaItemEntity.SQL_SELECT_MATCH;
                    String[] selectionArgs = new String[] { query };

                    MediaItem mediaItem;
                    cursor = builder.query( db, null, selection, selectionArgs, null, null, MediaItemEntity.FIELD_START_DATE + " DESC, " + MediaItemEntity.FIELD_TITLE );
                    while( cursor.moveToNext() ) {

                        mediaItem = new MediaItem();
                        mediaItem.setId( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_ID ) ) );
                        mediaItem.setMedia( Media.valueOf( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_MEDIA ) ) ) );
                        mediaItem.setTitle( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_TITLE ) ) );
                        mediaItem.setSubTitle( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_SUBTITLE ) ) );
                        mediaItem.setDescription( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_DESCRIPTION ) ) );
                        mediaItem.setStartDate( new DateTime( cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_START_DATE ) ) ) );
                        mediaItem.setProgramFlags( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_PROGRAM_FLAGS ) ) );
                        mediaItem.setSeason( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_SEASON ) ) );
                        mediaItem.setEpisode( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_EPISODE ) ) );
                        mediaItem.setStudio( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_STUDIO ) ) );
                        mediaItem.setCastMembers( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CAST_MEMBERS ) ) );
                        mediaItem.setCharacters( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CHARACTERS ) ) );
                        mediaItem.setUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_URL ) ) );
                        mediaItem.setFanartUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_FANART_URL ) ) );
                        mediaItem.setCoverartUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_COVERART_URL ) ) );
                        mediaItem.setBannerUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_BANNER_URL ) ) );
                        mediaItem.setPreviewUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_PREVIEW_URL ) ) );
                        mediaItem.setContentType( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CONTENT_TYPE ) ) );
                        mediaItem.setDuration( cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_DURATION ) ) );
                        mediaItem.setPercentComplete( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_LIVE_STREAM_PERCENT_COMPLETE ) ) );
                        mediaItem.setRecording( ( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_RECORDING ) ) != 0 ) );
                        mediaItem.setLiveStreamId( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_LIVE_STREAM_ID ) ) );
                        mediaItem.setCreateHttpLiveStreamUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CREATE_LIVE_STREAM_URL ) ) );
                        mediaItem.setRemoveHttpLiveStreamUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_REMOVE_LIVE_STREAM_URL ) ) );
                        mediaItem.setGetHttpLiveStreamUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_GET_LIVE_STREAM_URL ) ) );
                        mediaItem.setWatched( ( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_WATCHED_STATUS ) ) != 0 ) );
                        mediaItem.setMarkWatchedUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_MARK_WATCHED_URL ) ) );
                        mediaItem.setUpdateSavedBookmarkUrl( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_UPDATE_SAVED_BOOKMARK_URL ) ) );
                        mediaItem.setBookmark( cursor.getLong( cursor.getColumnIndex( MediaItemEntity.FIELD_BOOKMARK ) ) );
                        mediaItem.setInetref( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_INETREF ) ) );
                        mediaItem.setCertification( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_CERTIFICATION ) ) );
                        mediaItem.setParentalLevel( cursor.getInt( cursor.getColumnIndex( MediaItemEntity.FIELD_PARENTAL_LEVEL ) ) );
                        mediaItem.setRecordingGroup( cursor.getString( cursor.getColumnIndex( MediaItemEntity.FIELD_RECORDING_GROUP ) ) );

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
    public void refreshTitleInfoData( Collection<TitleInfoEntity> titleInfoEntityCollection ) {
        Log.d( TAG, "refreshTitleInfoData : enter" );

        if( null == titleInfoEntityCollection || titleInfoEntityCollection.isEmpty() ) {
            Log.d( TAG, "refreshTitleInfoData : titleInfoEntityCollection is empty" );

            return;
        } else {
            Log.d( TAG, "refreshTitleInfoData : titleInfoEntityCollection is not empty" );

            db.beginTransaction();

            List<String> values = new ArrayList<>();
            values.add( Media.PROGRAM.name() );

            List<String> parameters = new ArrayList<>();
            for( TitleInfoEntity entity : titleInfoEntityCollection ) {

                parameters.add( "?" );
                values.add( entity.getTitle() );

            }

            db.delete( MediaItemEntity.TABLE_NAME, MediaItemEntity.FIELD_MEDIA + " = ? and not " + MediaItemEntity.FIELD_TITLE + " in (" + TextUtils.join( ",", parameters ) + ")", values.toArray( new String[ values.size() ] ) );

            db.setTransactionSuccessful();
            db.endTransaction();

        }

        Log.d( TAG, "refreshTitleInfoData : exit" );
    }

    @Override
    public void refreshRecordedProgramData( Collection<MediaItem> mediaItemEntityCollection ) {
        Log.d( TAG, "refreshRecordedProgramData : enter" );

        if( null == mediaItemEntityCollection || mediaItemEntityCollection.isEmpty() ) {
            Log.d( TAG, "refreshRecordedProgramData : mediaItemEntityCollection is empty" );

            return;

        } else {
            Log.d( TAG, "refreshRecordedProgramData : mediaItemEntityCollection is not empty" );

            db.beginTransaction();

            Observable.from( mediaItemEntityCollection )
                    .distinct( MediaItem::getTitle )
                    .flatMap( mediaItemEntity -> Observable.just( mediaItemEntity.getTitle() ) )
                    .doOnNext( title -> db.delete( MediaItemEntity.TABLE_NAME, MediaItemEntity.FIELD_MEDIA + " = ? and " + MediaItemEntity.FIELD_TITLE + " = ?", new String[] { Media.PROGRAM.name(), title } ) )
                    .doOnNext( title -> Log.d( TAG, "refreshRecordedPrograms : deleting old recordings for title=" + title ) )
                    .subscribe();

            processCollection( mediaItemEntityCollection );
        }

        Log.d( TAG, "refreshRecordedProgramData : exit" );
    }

    @Override
    public void refreshVideoData( Collection<MediaItem> mediaItemEntityCollection ) {
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

    private void processCollection( Collection<MediaItem> mediaItemEntityCollection ) {

        SQLiteStatement statement = db.compileStatement( MediaItemEntity.SQL_INSERT );
        for( MediaItem mediaItemEntity : mediaItemEntityCollection ) {
//            Log.d(TAG, "processCollection : searchResultEntity=" + searchResultEntity.toString());

            statement.clearBindings();
            statement.bindLong( 1, mediaItemEntity.getId() );
            statement.bindString( 2, mediaItemEntity.getMedia().name() );
            statement.bindString( 3, null == mediaItemEntity.getTitle() ? "" : mediaItemEntity.getTitle() );
            statement.bindString( 4, null == mediaItemEntity.getSubTitle() ? "" : mediaItemEntity.getSubTitle() );
            statement.bindString( 5, null == mediaItemEntity.getDescription() ? "" : mediaItemEntity.getDescription() );
            statement.bindLong( 6, null == mediaItemEntity.getStartDate() ? -1 : mediaItemEntity.getStartDate().getMillis() );
            statement.bindLong( 7, mediaItemEntity.getProgramFlags() );
            statement.bindLong( 8, mediaItemEntity.getSeason() );
            statement.bindLong( 9, mediaItemEntity.getEpisode() );
            statement.bindString( 10, null == mediaItemEntity.getStudio() ? "" : mediaItemEntity.getStudio() );
            statement.bindString( 11, null == mediaItemEntity.getCastMembers() ? "" : mediaItemEntity.getCastMembers() );
            statement.bindString( 12, null == mediaItemEntity.getCharacters() ? "" : mediaItemEntity.getCharacters() );
            statement.bindString( 13, null == mediaItemEntity.getUrl() ? "" : mediaItemEntity.getUrl() );
            statement.bindString( 14, null == mediaItemEntity.getFanartUrl() ? "" : mediaItemEntity.getFanartUrl() );
            statement.bindString( 15, null == mediaItemEntity.getCoverartUrl() ? "" : mediaItemEntity.getCoverartUrl() );
            statement.bindString( 16, null == mediaItemEntity.getBannerUrl() ? "" : mediaItemEntity.getBannerUrl() );
            statement.bindString( 17, null == mediaItemEntity.getPreviewUrl() ? "" : mediaItemEntity.getPreviewUrl() );
            statement.bindString( 18, null == mediaItemEntity.getContentType() ? "" : mediaItemEntity.getContentType() );
            statement.bindLong( 19, mediaItemEntity.getDuration() );
            statement.bindLong( 20, mediaItemEntity.getPercentComplete() );
            statement.bindLong( 21, mediaItemEntity.isRecording() ? 1 : 0 );
            statement.bindLong( 22, mediaItemEntity.getLiveStreamId() );
            statement.bindString( 23, null == mediaItemEntity.getCreateHttpLiveStreamUrl() ? "" : mediaItemEntity.getCreateHttpLiveStreamUrl() );
            statement.bindString( 24, null == mediaItemEntity.getRemoveHttpLiveStreamUrl() ? "" : mediaItemEntity.getRemoveHttpLiveStreamUrl() );
            statement.bindString( 25, null == mediaItemEntity.getGetHttpLiveStreamUrl() ? "" : mediaItemEntity.getGetHttpLiveStreamUrl() );
            statement.bindLong( 26, mediaItemEntity.isWatched() ? 1 : 0 );
            statement.bindString( 27, null == mediaItemEntity.getMarkWatchedUrl() ? "" : mediaItemEntity.getMarkWatchedUrl() );
            statement.bindString( 28, null == mediaItemEntity.getUpdateSavedBookmarkUrl() ? "" : mediaItemEntity.getUpdateSavedBookmarkUrl() );
            statement.bindLong( 29, mediaItemEntity.getBookmark() );
            statement.bindString( 30, null == mediaItemEntity.getInetref() ? "" : mediaItemEntity.getInetref() );
            statement.bindString( 31, null == mediaItemEntity.getCertification() ? "" : mediaItemEntity.getCertification() );
            statement.bindLong( 32, mediaItemEntity.getParentalLevel() );
            statement.bindString( 32, null == mediaItemEntity.getRecordingGroup() ? "" : mediaItemEntity.getRecordingGroup() );
            statement.executeInsert();

        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

}

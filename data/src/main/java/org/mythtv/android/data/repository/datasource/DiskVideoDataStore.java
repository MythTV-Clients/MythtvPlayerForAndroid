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

import android.util.Log;

import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/9/15.
 */
public class DiskVideoDataStore implements VideoDataStore {

    private static final String TAG = DiskVideoDataStore.class.getSimpleName();

    private final VideoCache videoCache;

    public DiskVideoDataStore( VideoCache videoCache ) {

        this.videoCache = videoCache;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );
        Log.d( TAG, "getCategory : category=" + category );

        return this.videoCache.getCategory( category )
                .doOnNext( videoMetadataInfoEntities -> Log.d( TAG, "getCategory : videoMetadataInfoEntities=" + videoMetadataInfoEntities ) )
                .doOnError( throwable -> Log.e( TAG, "getCategory : error", throwable ) );

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getSeriesInCategory( String category, String series ) {
        Log.d( TAG, "getSeriesInCategory : enter" );

        Log.d( TAG, "getSeriesInCategory : category=" + category + ", series=" + series );

        return this.videoCache.getCategory( series )
                .flatMap( Observable::from )
                .toSortedList( ( videoMetadataInfoEntity1, videoMetadataInfoEntity2 ) -> {

                    StringBuilder e1 = new StringBuilder();
                    e1.append( "S" );
                    if( videoMetadataInfoEntity1.getSeason() < 10 ) {
                        e1.append( "0" );
                    }
                    e1.append( videoMetadataInfoEntity1.getSeason() );

                    e1.append( "E" );
                    if( videoMetadataInfoEntity1.getEpisode() < 10 ) {
                        e1.append( "0" );
                    }
                    e1.append( videoMetadataInfoEntity1.getEpisode() );

                    StringBuilder e2 = new StringBuilder();
                    e2.append( "S" );
                    if( videoMetadataInfoEntity2.getSeason() < 10 ) {
                        e2.append( "0" );
                    }
                    e2.append( videoMetadataInfoEntity2.getSeason() );

                    e2.append( "E" );
                    if( videoMetadataInfoEntity2.getEpisode() < 10 ) {
                        e2.append( "0" );
                    }
                    e2.append( videoMetadataInfoEntity2.getEpisode() );

                    return e1.toString().compareTo( e2.toString() );
                })
                .doOnNext( videoMetadataInfoEntities -> Log.d( TAG, "getCategory : videoMetadataInfoEntities=" + videoMetadataInfoEntities ) );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {
        Log.d( TAG, "getVideoById : enter" );
        Log.d( TAG, "getVideoById : id=" + id );

        return this.videoCache.get( id );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoByFilename( String filename ) {
        Log.d( TAG, "getVideoByFilename : enter" );
        Log.d( TAG, "getVideoByFilename : filename=" + filename );

        return null;
    }

    @Override
    public Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }
}

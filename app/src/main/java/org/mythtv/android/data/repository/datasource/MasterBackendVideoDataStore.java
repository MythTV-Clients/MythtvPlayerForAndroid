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

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.net.VideoApi;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/9/15.
 */
public class MasterBackendVideoDataStore implements VideoDataStore {

    private static final String TAG = MasterBackendVideoDataStore.class.getSimpleName();

    private final VideoApi api;

    public MasterBackendVideoDataStore( VideoApi api ) {

        this.api = api;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideos : enter" );

        Log.d( TAG, "getVideos : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        Observable<List<VideoMetadataInfoEntity>> videoList = this.api.getVideoList( folder, sort, descending, startIndex, count )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        Log.d( TAG, "getVideos : exit" );
        return videoList;
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );

        Log.d( TAG, "getCategory : category=" + category );

        return this.api.getVideoList( null, null, false, -1, -1 )
                .flatMap( Observable::from )
                .filter( entity -> entity.contentType().equals( category ) )
                .toSortedList( ( entity1, entity2 ) -> entity1.title().compareTo( entity2.title() ) )
                .doOnNext( entity -> Log.d( TAG, "getCategory : entity=" + entity ) );

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getSeriesInCategory( String category, String series ) {
        Log.d( TAG, "getSeriesInCategory : enter" );

        Log.d( TAG, "getSeriesInCategory : category=" + category + ". series=" + series );

        return this.api.getVideoList( null, null, false, -1, -1 )
                .flatMap( Observable::from )
                .filter( entity -> entity.contentType().equals( category ) )
                .filter( entity -> entity.title().equals( series ) )
                .toSortedList( ( entity1, entity2 ) -> {

                    StringBuilder e1 = new StringBuilder();
                    e1.append( 'S' );
                    if( entity1.season() < 10 ) {
                        e1.append( '0' );
                    }
                    e1.append( entity1.season() ).append( 'E' );
                    if( entity1.episode() < 10 ) {
                        e1.append( '0' );
                    }
                    e1.append( entity1.episode() );

                    StringBuilder e2 = new StringBuilder();
                    e2.append( 'S' );
                    if( entity2.season() < 10 ) {
                        e2.append( '0' );
                    }
                    e2.append( entity2.season() ).append( 'E' );
                    if( entity2.episode() < 10 ) {
                        e2.append( '0' );
                    }
                    e2.append( entity2.episode() );

                    return e1.toString().compareTo( e2.toString() );
                })
                .doOnNext( entity -> Log.d( TAG, "getSeriesInCategory : entity=" + entity ) );

    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : id=" + id );

        Log.d( TAG, "getVideoById : exit" );
        return this.api.getVideoById( id )
                .doOnNext( entity ->  Log.i( TAG, "getVideoById : entity=" + entity ) );
    }

    @Override
    public Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : videoId=" + videoId + ", watched=" + watched );

        Log.d( TAG, "getVideoById : exit" );
        return this.api.updateWatchedStatus( videoId, watched );
    }

}

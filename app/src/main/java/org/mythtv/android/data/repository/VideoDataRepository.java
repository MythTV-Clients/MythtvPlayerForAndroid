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

package org.mythtv.android.data.repository;

import android.util.Log;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.VideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoDataRepository implements VideoRepository {

    private static final String TAG = VideoDataRepository.class.getSimpleName();

    private final VideoDataStoreFactory videoDataStoreFactory;

    @Inject
    public VideoDataRepository( VideoDataStoreFactory videoDataStoreFactory ) {

        this.videoDataStoreFactory = videoDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<VideoMetadataInfo>> getVideoList( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideoList : enter" );
        Log.d( TAG, "getVideoList : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.getVideos( folder, sort, descending, startIndex, count )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "getVideoList : error", throwable ) )
                .map( entities -> VideoMetadataInfoEntityDataMapper.transform( entities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<VideoMetadataInfo>> getVideoListByContentType( final String contentType ) {
        Log.d( TAG, "getVideoListByContentType : enter" );
        Log.d( TAG, "getVideoListByContentType : contentType=" + contentType );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategoryDataStore( contentType );

        return videoDataStore.getCategory( contentType )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "getVideoListByContentType : error", throwable ) )
                .map( entities -> VideoMetadataInfoEntityDataMapper.transform( entities ) );

    }

    @Override
    public Observable<List<VideoMetadataInfo>> getVideoSeriesListByContentType(String contentType) {
        Log.d( TAG, "getVideoSeriesListByContentType : enter" );
        Log.d( TAG, "getVideoSeriesListByContentType : contentType=" + contentType );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategoryDataStore( contentType );

        return videoDataStore.getCategory( contentType )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "getVideoSeriesListByContentType : error", throwable ) )
                .flatMap( Observable::from )
                .filter( entity -> entity.getContentType().equals( contentType ) )
                .distinct( VideoMetadataInfoEntity::getTitle )
                .toSortedList( ( entity1, entity2 ) -> entity1.getTitle().compareTo( entity2.getTitle() ) )
                .map( VideoMetadataInfoEntityDataMapper::transform );

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<VideoMetadataInfo>> getVideoListByContentTypeAndSeries( String contentType, String series ) {
        Log.d( TAG, "getVideoListByContentTypeAndSeries : enter" );
        Log.d( TAG, "getVideoListByContentTypeAndSeries : contentType=" + contentType + ", series=" + series );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategoryDataStore( contentType );

        return videoDataStore.getSeriesInCategory( contentType, series )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "getVideoListByContentTypeAndSeries : error", throwable ) )
                .map( entities -> VideoMetadataInfoEntityDataMapper.transform( entities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<VideoMetadataInfo> getVideo( int id ) {

        final VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.getVideoById( id )
                .map( entities -> VideoMetadataInfoEntityDataMapper.transform( entities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        final VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.updateWatchedStatus( videoId, watched )
                .doOnError( throwable -> Log.e( TAG, "updateWatchedStatus : error", throwable ) )
                .doOnCompleted( () -> videoDataStore.getVideos( null, null, false, -1, -1 ) );
    }

}

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

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.MediaItemDataMapper;
import org.mythtv.android.data.entity.mapper.SeriesDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.VideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.repository.VideoRepository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/3/15.
 */
@Singleton
public class VideoDataRepository implements VideoRepository {

    private static final String TAG = VideoDataRepository.class.getSimpleName();

    private final VideoDataStoreFactory videoDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;

    @Inject
    public VideoDataRepository( final VideoDataStoreFactory videoDataStoreFactory, final ContentDataStoreFactory contentDataStoreFactory ) {

        this.videoDataStoreFactory = videoDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<MediaItem>> getVideoList( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideoList : enter" );
        Log.d( TAG, "getVideoList : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        final VideoDataStore videoDataStore = videoDataStoreFactory.create();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<VideoMetadataInfoEntity>> videoEntities = videoDataStore.getVideos( folder, sort, descending, startIndex, count )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .flatMap( Observable::from )
                .toList();

        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList( null )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        Observable<List<VideoMetadataInfoEntity>> videos = Observable.zip( videoEntities, liveStreamInfoEntities, ( videoEntityList, list ) -> {

            if( null != list && !list.isEmpty() ) {

                for( VideoMetadataInfoEntity videoEntity : videoEntityList ) {

                    for( LiveStreamInfoEntity liveStreamInfoEntity : list ) {

                        if( liveStreamInfoEntity.getSourceFile().endsWith( videoEntity.getFileName() ) ) {

                            videoEntity.setLiveStreamInfoEntity( liveStreamInfoEntity );

                        }

                    }

                }

            }

            return videoEntityList;
        });

        return videos
                .doOnError( throwable -> Log.e( TAG, "getVideoList : error", throwable ) )
                .map( entities -> {
                    try {

                        return MediaItemDataMapper.transformVideos( entities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "getVideoList : error", e );
                    }

                    return new ArrayList<>();
                });
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<MediaItem>> getVideoListByContentType( final String contentType ) {
        Log.d( TAG, "getVideoListByContentType : enter" );
        Log.d( TAG, "getVideoListByContentType : contentType=" + contentType );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategoryDataStore( contentType );
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<VideoMetadataInfoEntity>> videoEntities = videoDataStore.getCategory( contentType )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .flatMap( Observable::from )
                .toList();

        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList( null )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        Observable<List<VideoMetadataInfoEntity>> videos = Observable.zip( videoEntities, liveStreamInfoEntities, ( videoEntityList, list ) -> {

            if( null != list && !list.isEmpty() ) {

                for( VideoMetadataInfoEntity videoEntity : videoEntityList ) {

                    for( LiveStreamInfoEntity liveStreamInfoEntity : list ) {

                        if( liveStreamInfoEntity.getSourceFile().endsWith( videoEntity.getFileName() ) ) {

                            videoEntity.setLiveStreamInfoEntity( liveStreamInfoEntity );

                        }

                    }

                }

            }

            return videoEntityList;
        });

        return videos
                .doOnError( throwable -> Log.e( TAG, "getVideoListByContentType : error", throwable ) )
                .map( entities -> {
                    try {

                        return MediaItemDataMapper.transformVideos( entities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "getVideoListByContentType : error", e );
                    }

                    return new ArrayList<>();
                });

    }

    @Override
    public Observable<List<Series>> getVideoSeriesListByContentType( String contentType ) {
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
                .map( SeriesDataMapper::transformVideos );

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<MediaItem>> getVideoListByContentTypeAndSeries( String contentType, String series ) {
        Log.d( TAG, "getVideoListByContentTypeAndSeries : enter" );
        Log.d( TAG, "getVideoListByContentTypeAndSeries : contentType=" + contentType + ", series=" + series );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategoryDataStore( contentType );
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<VideoMetadataInfoEntity>> videoEntities = videoDataStore.getSeriesInCategory( contentType, series )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .flatMap( Observable::from )
                .toList();

        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList( null )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        Observable<List<VideoMetadataInfoEntity>> videos = Observable.zip( videoEntities, liveStreamInfoEntities, ( videoEntityList, list ) -> {

            if( null != list && !list.isEmpty() ) {

                for( VideoMetadataInfoEntity videoEntity : videoEntityList ) {

                    for( LiveStreamInfoEntity liveStreamInfoEntity : list ) {

                        if( liveStreamInfoEntity.getSourceFile().endsWith( videoEntity.getFileName() ) ) {

                            videoEntity.setLiveStreamInfoEntity( liveStreamInfoEntity );

                        }

                    }

                }

            }

            return videoEntityList;
        });

        return videos
                .doOnError( throwable -> Log.e( TAG, "getVideoListByContentTypeAndSeries : error", throwable ) )
                .map( entities -> {
                    try {

                        return MediaItemDataMapper.transformVideos( entities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "getVideoListByContentTypeAndSeries : error", e );
                    }

                    return new ArrayList<>();
                });
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<MediaItem> getVideo( int id ) {

        final VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<VideoMetadataInfoEntity> videoEntity = videoDataStore.getVideoById( id );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntity = videoEntity
                .flatMap( entity -> contentDataStore.liveStreamInfoEntityList( entity.getFileName() ) );

        Observable<VideoMetadataInfoEntity> video = Observable.zip( videoEntity, liveStreamInfoEntity, ( videoEntity1, liveStreamInfoEntityList ) -> {

            if( null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty() ) {

                videoEntity1.setLiveStreamInfoEntity( liveStreamInfoEntityList.get( 0 ) );

            }

            Log.d( TAG, "getVideo : videoEntity=" + videoEntity1.toString() );
            return videoEntity1;
        });


        return video
                .map( entity -> {
                    try {

                        return MediaItemDataMapper.transform( entity );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "getVideo : error", e );
                    }

                    return new MediaItem();
                });
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

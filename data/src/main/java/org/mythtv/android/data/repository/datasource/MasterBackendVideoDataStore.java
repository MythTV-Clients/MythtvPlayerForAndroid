package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.mythtv.android.data.cache.MemoryVideoCache;
import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
import org.mythtv.android.data.net.VideoApi;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 11/9/15.
 */
public class MasterBackendVideoDataStore implements VideoDataStore {

    private static final String TAG = MasterBackendVideoDataStore.class.getSimpleName();

    private final VideoApi videoApi;
    private final VideoCache videoCache;
    private final MemoryVideoCache memoryVideoCache;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final SearchResultEntityDataMapper searchResultEntityDataMapper;

    private final Action1<List<VideoMetadataInfoEntity>> saveVideosToCacheAction =
            videoMetadataInfoEntities -> {

                if( null != videoMetadataInfoEntities ) {
                    MasterBackendVideoDataStore.this.videoCache.put( videoMetadataInfoEntities );
                }

            };

    private final Action1<List<VideoMetadataInfoEntity>> saveVideosToDbAction =
            videoMetadataInfoEntities -> {

                if( null != videoMetadataInfoEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendVideoDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                        .from( videoMetadataInfoEntities )
                        .toList()
                        .map( MasterBackendVideoDataStore.this.searchResultEntityDataMapper::transformVideos )
                        .subscribe( searchDataStore::refreshVideoData );
                }

            };

    public MasterBackendVideoDataStore( VideoApi videoApi, VideoCache videoCache, MemoryVideoCache memoryVideoCache, SearchDataStoreFactory searchDataStoreFactory, SearchResultEntityDataMapper searchResultEntityDataMapper ) {

        this.videoApi = videoApi;
        this.videoCache = videoCache;
        this.memoryVideoCache = memoryVideoCache;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.searchResultEntityDataMapper = searchResultEntityDataMapper;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideos : enter" );

        Log.d( TAG, "getVideos : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        Observable<List<VideoMetadataInfoEntity>> videoList = this.videoApi.getVideoList( folder, sort, descending, startIndex, count )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnNext( saveVideosToCacheAction );

        Log.d( TAG, "getVideos : exit" );
        return videoList;
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );

        Log.d( TAG, "getCategory : category=" + category );

        Observable<List<VideoMetadataInfoEntity>> videoList = this.videoApi.getVideoList( null, null, false, -1, -1 )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnNext( saveVideosToCacheAction )
                .doOnNext( saveVideosToDbAction );

        return videoList
                .flatMap( Observable::from )
                .filter( videoMetadataInfoEntity -> videoMetadataInfoEntity.getContentType().equals( category ) )
                .toList()
                .doOnNext( videoMetadataInfoEntities -> this.memoryVideoCache.put( category, videoMetadataInfoEntities ) );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : id=" + id );

        Log.d( TAG, "getVideoById : exit" );
        return this.videoApi.getVideoById( id )
                .doOnNext( videoMetadataInfoEntity ->  Log.i( TAG, "getVideoById : video=" + videoMetadataInfoEntity ) );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoByFilename( String filename ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : filename=" + filename );

        Log.d( TAG, "getVideoById : exit" );
        return this.videoApi.getVideoByFilename( filename );
    }

}

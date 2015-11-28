package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;
import org.mythtv.android.data.net.VideoApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Action1<VideoMetadataInfoEntity> saveVideoToCacheAction =
            videoMetadataInfoEntity -> {

                if( null != videoMetadataInfoEntity ) {
                    MasterBackendVideoDataStore.this.videoCache.put( videoMetadataInfoEntity );
                }

            };

    private final Action1<List<VideoMetadataInfoEntity>> saveVideoToCategoryToCacheAction =
            videoMetadataInfoEntities -> {

                if( null != videoMetadataInfoEntities && !videoMetadataInfoEntities.isEmpty() ) {
                    MasterBackendVideoDataStore.this.videoCache.putCategory( videoMetadataInfoEntities );
                }

            };

    public MasterBackendVideoDataStore( VideoApi videoApi, VideoCache videoCache ) {

        this.videoApi = videoApi;
        this.videoCache = videoCache;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideos : enter" );

        Log.d( TAG, "getVideos : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        Observable<List<VideoMetadataInfoEntity>> videoList = this.videoApi.getVideoList( folder, sort, descending, startIndex, count )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        buildCategories( videoList );

        Log.d( TAG, "getVideos : exit" );
        return videoList;
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );

        Log.d( TAG, "getCategory : category=" + category );

        Observable<List<VideoMetadataInfoEntity>> videoList = this.videoApi.getVideoList( null, null, false, -1, -1 )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

        return videoList
                .flatMap( Observable::from )
//                .doOnNext( saveVideoToCacheAction )
//                .doOnNext( saveVideoToCategoryToCacheAction )
                .filter( videoMetadataInfoEntity1 -> videoMetadataInfoEntity1.getContentType().equals( category ) )
                .toList()
                .doOnNext( saveVideoToCategoryToCacheAction );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : id=" + id );

        Log.d( TAG, "getVideoById : exit" );
        return this.videoApi.getVideoById( id )
                .doOnNext( videoMetadataInfoEntity ->  Log.i( TAG, "getVideoById : video=" + videoMetadataInfoEntity ) )
//                .subscribeOn( Schedulers.io() )
//                .observeOn( AndroidSchedulers.mainThread() )
                .doOnNext( saveVideoToCacheAction );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoByFilename( String filename ) {
        Log.d( TAG, "getVideoById : enter" );

        Log.d( TAG, "getVideoById : filename=" + filename );

        Log.d( TAG, "getVideoById : exit" );
        return this.videoApi.getVideoByFilename( filename )
                .doOnNext( saveVideoToCacheAction );
    }

    private Map<String, List<VideoMetadataInfoEntity>> buildCategories( Observable<List<VideoMetadataInfoEntity>> videoList ) {
        Log.d( TAG, "buildCategories : enter");

        Map<String, List<VideoMetadataInfoEntity>> categoryMap = new HashMap<>();
        videoList
                .flatMap( Observable::from )
//                .doOnNext( saveVideoToCacheAction )
//                .doOnNext(saveVideoToCategoryToCacheAction)
//                .filter( videoMetadataInfoEntity1 -> videoMetadataInfoEntity1.getContentType().equals( contentType ) )
                .subscribe(videoMetadataInfoEntity -> mapCategory(videoMetadataInfoEntity, categoryMap));

       Log.d( TAG, "buildCategories : exit" );
        return categoryMap;
    }

    private void mapCategory( VideoMetadataInfoEntity videoMetadataInfoEntity, Map<String, List<VideoMetadataInfoEntity>> categoriesMap ) {
        Log.d( TAG, "mapCategory : enter");

        if( null == videoMetadataInfoEntity ) {
            Log.d( TAG, "mapCategory : videoMetadataInfoEntity is null" );

            return;
        }

        if( !categoriesMap.containsKey( videoMetadataInfoEntity.getContentType() ) ) {
            Log.d( TAG, "mapCategory : adding category" );

            categoriesMap.put( videoMetadataInfoEntity.getContentType(), new ArrayList<>() );
        }

        Log.d( TAG, "mapCategory : adding video to category" );
        categoriesMap.get( videoMetadataInfoEntity.getContentType() ).add( videoMetadataInfoEntity );

        Log.d( TAG, "mapCategory : exit" );
    }

}

package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.mythtv.android.data.cache.MemoryVideoCache;
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
    private final MemoryVideoCache memoryVideoCache;

    public DiskVideoDataStore( VideoCache videoCache, MemoryVideoCache memoryVideoCache ) {

        this.videoCache = videoCache;
        this.memoryVideoCache = memoryVideoCache;
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );
        Log.d( TAG, "getCategory : category=" + category );

        if( "TELEVISION".equals( category ) ) {

            return this.videoCache.getCategory( category )
                    .doOnNext(videoMetadataInfoEntities -> this.memoryVideoCache.put(category, videoMetadataInfoEntities));

        } else {

            return this.videoCache.getCategory(category)
                    .doOnNext(videoMetadataInfoEntities -> this.memoryVideoCache.put(category, videoMetadataInfoEntities));

        }

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

}

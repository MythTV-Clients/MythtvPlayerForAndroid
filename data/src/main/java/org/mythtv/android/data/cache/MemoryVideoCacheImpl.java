package org.mythtv.android.data.cache;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.executor.ThreadExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 12/18/15.
 */
@Singleton
public class MemoryVideoCacheImpl implements MemoryVideoCache {

    private static final String TAG = MemoryVideoCacheImpl.class.getSimpleName();

    private static final String SETTINGS_FILE_NAME = "org.mythtv.android.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "video_last_cache_update";

    private static final long EXPIRATION_TIME = 86400000;   // cache for 1 day

    private final Context context;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    private Map<String, List<VideoMetadataInfoEntity>> cache;

    @Inject
    public MemoryVideoCacheImpl( Context context, FileManager fileManager, ThreadExecutor executor ) {

        if( context == null || fileManager == null || executor == null ) {

            throw new IllegalArgumentException( "Invalid null parameter" );
        }

        this.context = context;
        this.fileManager = fileManager;
        this.threadExecutor = executor;

        cache = new HashMap<>();

    }

    @Override
    public void put( String key, List<VideoMetadataInfoEntity> videos ) {
        Log.d( TAG, "put : enter" );

        if( ( null != key && !"".equals( key ) ) && ( null != videos && !videos.isEmpty() ) ) {
            Log.d( TAG, "put : key and videos is not null" );

            if( !isCached( key ) ) {
                Log.d( TAG, "put : key is not cached" );

                this.executeAsynchronously( new CacheWriter( this.cache, key, videos ) );
                setLastCacheUpdateTimeMillis();

            }

        }

        Log.d( TAG, "put : exit" );
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> get( String key ) {
        Log.d( TAG, "get : enter" );
        Log.d( TAG, "get : key=" + key );

        return Observable
                .from( this.cache.get( key ) )
                .toList();
    }

    @Override
    public boolean isCached( String key ) {
        Log.d( TAG, "isCached : enter" );

        boolean isCached = !this.cache.isEmpty() && this.cache.containsKey( key );
        Log.d( TAG, "isCached : isCached=" + isCached );

        Log.d( TAG, "isCached : exit" );
        return isCached;
    }

    @Override
    public boolean isExpired() {
        Log.d( TAG, "isExpired : enter" );

        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ( ( currentTime - lastUpdateTime ) > EXPIRATION_TIME );
        Log.d( TAG, "isExpired : expired=" + expired );

        if( expired ) {
            Log.d( TAG, "isExpired : cache is expired, evict all entries" );

            this.evictAll();
        }

        Log.d( TAG, "isExpired : exit" );
        return expired;
    }

    @Override
    public void evictAll() {
        Log.d( TAG, "evictAll : enter" );

        this.executeAsynchronously( new CacheEvictor( cache ) );

        Log.d( TAG, "evictAll : exit" );
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        Log.v( TAG, "setLastCacheUpdateTimeMillis : enter" );

        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences( this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis );

        Log.v( TAG, "setLastCacheUpdateTimeMillis : exit" );
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        Log.v( TAG, "getLastCacheUpdateTimeMillis : enter" );

        Log.v( TAG, "getLastCacheUpdateTimeMillis : exit" );
        return this.fileManager.getFromPreferences( this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE );
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously( Runnable runnable ) {

        this.threadExecutor.execute( runnable );

    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {

        private final Map<String, List<VideoMetadataInfoEntity>> cache;
        private final List<VideoMetadataInfoEntity> videos;
        private final String key;

        CacheWriter( Map<String, List<VideoMetadataInfoEntity>> cache, String key, List<VideoMetadataInfoEntity> videos ) {

            this.cache = cache;
            this.key = key;
            this.videos = videos;

        }

        @Override
        public void run() {

            cache.put( key, videos );

        }

    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {

        private final Map<String, List<VideoMetadataInfoEntity>> cache;

        CacheEvictor( Map<String, List<VideoMetadataInfoEntity>> cache ) {

            this.cache = cache;

        }

        @Override
        public void run() {

            this.cache.clear();

        }

    }

}

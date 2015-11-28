package org.mythtv.android.data.cache;

import android.content.Context;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.serializer.VideoEntityJsonSerializer;
import org.mythtv.android.data.cache.serializer.VideoListEntityJsonSerializer;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;
import org.mythtv.android.data.entity.VideoMetadataInfosEntity;
import org.mythtv.android.data.exception.VideoNotFoundException;
import org.mythtv.android.domain.executor.ThreadExecutor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoCacheImpl implements VideoCache {

    private static final String TAG = VideoCacheImpl.class.getSimpleName();

    private static final String SETTINGS_FILE_NAME = "org.mythtv.android.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "video_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final VideoEntityJsonSerializer videoEntityJsonSerializer;
    private final VideoListEntityJsonSerializer videoListEntityJsonSerializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link ProgramCacheImpl}.
     *
     * @param context A
     * @param videoEntityJsonSerializer {@link VideoEntityJsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public VideoCacheImpl( Context context, VideoEntityJsonSerializer videoEntityJsonSerializer, VideoListEntityJsonSerializer videoListEntityJsonSerializer, FileManager fileManager, ThreadExecutor executor ) {
        Log.d( TAG, "initialize : enter" );

        if( context == null || videoEntityJsonSerializer == null || videoListEntityJsonSerializer == null || fileManager == null || executor == null ) {

            throw new IllegalArgumentException( "Invalid null parameter" );
        }

        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.videoEntityJsonSerializer = videoEntityJsonSerializer;
        this.videoListEntityJsonSerializer = videoListEntityJsonSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );
        Log.d( TAG, "getCategory : category=" + category );

        return Observable.create( new Observable.OnSubscribe<List<VideoMetadataInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<VideoMetadataInfoEntity>> subscriber ) {
                Log.d( TAG, "getCategory.call : enter" );

                File videoEntityListFile = VideoCacheImpl.this.buildCategoryFile( category );
                String fileContent = VideoCacheImpl.this.fileManager.readFileContent( videoEntityListFile );
                Log.d( TAG, "getCategory.call : fileContent=" + fileContent );

                VideoMetadataInfoListEntity videoEntities = VideoCacheImpl.this.videoListEntityJsonSerializer.deserialize( fileContent );
                if( null != videoEntities ) {
                    Log.d( TAG, "getCategory.call : videoEntities=" + videoEntities.toString() );

                    subscriber.onNext( Arrays.asList( videoEntities.getVideoMetadataInfosEntity() ) );
                    subscriber.onCompleted();

                } else {
                    Log.d( TAG, "getCategory.call : videoEntityList is null" );

                    subscriber.onError( new VideoNotFoundException() );

                }

                Log.d( TAG, "getCategory.call : exit" );
            }

        });

    }

    @Override
    public void putCategory( List<VideoMetadataInfoEntity> videos ) {
        Log.d( TAG, "putCategory : enter" );

        if( null != videos && !videos.isEmpty() ) {
            Log.d( TAG, "putCategory : videos is not null" );

            File videoEntitiesFile = this.buildCategoryFile( videos.get( 0 ).getContentType() );

            if( !isCategoryCached( videos.get( 0 ).getContentType() ) ) {
                Log.d( TAG, "putCategory : category is not cached" );

                VideoMetadataInfoListEntity videoEntities = new VideoMetadataInfoListEntity();
                videoEntities.setVideoMetadataInfosEntity( videos.toArray( new VideoMetadataInfoEntity[ videos.size() ] ) );

                String jsonString = this.videoListEntityJsonSerializer.serialize( videoEntities );
                this.executeAsynchronously( new CacheWriter( this.fileManager, videoEntitiesFile, jsonString ) );
                setLastCacheUpdateTimeMillis();

            }

        }

        Log.d( TAG, "putCategory : exit" );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> get( int id ) {
        Log.d( TAG, "get : enter" );
        Log.d( TAG, "get : id=" + id );

        return Observable.create( new Observable.OnSubscribe<VideoMetadataInfoEntity>() {

            @Override
            public void call( Subscriber<? super VideoMetadataInfoEntity> subscriber ) {
                Log.d( TAG, "get.call : enter" );

                File videoEntityFile = VideoCacheImpl.this.buildFile( id );
                String fileContent = VideoCacheImpl.this.fileManager.readFileContent( videoEntityFile );
                Log.d( TAG, "get.call : fileContent=" + fileContent );

                VideoMetadataInfoEntity videoEntity = VideoCacheImpl.this.videoEntityJsonSerializer.deserialize( fileContent );
                if( null != videoEntity ) {
                    Log.d( TAG, "get.call : videoEntity=" + videoEntity.toString() );

                    subscriber.onNext( videoEntity );
                    subscriber.onCompleted();

                } else {
                    Log.d( TAG, "get.call : videoEntity is null" );

                    subscriber.onError( new VideoNotFoundException() );

                }

                Log.d( TAG, "get.call : exit" );
            }

        });

    }

    @Override
    public void put( VideoMetadataInfoEntity videoEntity ) {
        Log.d( TAG, "put : enter" );

        if( null != videoEntity ) {
            Log.d( TAG, "put : videoEntity=" + videoEntity.toString() );

            File videoEntityFile = this.buildFile( videoEntity.getId() );

            if( !isCached( videoEntity.getId() ) ) {

                String jsonString = this.videoEntityJsonSerializer.serialize( videoEntity );
                this.executeAsynchronously( new CacheWriter( this.fileManager, videoEntityFile, jsonString ) );
                setLastCacheUpdateTimeMillis();

            }

        }

        Log.d( TAG, "put : exit" );
    }

    @Override
    public boolean isCached( int id ) {
        Log.d( TAG, "isCached : enter" );

        File videoEntityFile = this.buildFile( id );

        Log.d( TAG, "isCached : exit" );
        return this.fileManager.exists( videoEntityFile );
    }

    @Override
    public boolean isCategoryCached( String category ) {
        Log.d( TAG, "isCategoryCached : enter" );

        File videoEntitiesFile = this.buildCategoryFile( category );

        Log.d( TAG, "isCategoryCached : exit" );
        return this.fileManager.exists( videoEntitiesFile );
    }

    @Override
    public boolean isExpired() {
        Log.d(TAG, "isExpired : enter");

        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ( ( currentTime - lastUpdateTime ) > EXPIRATION_TIME );

        if( expired ) {
            Log.d( TAG, "isExpired : cache is expired, evict all entries" );

            this.evictAll();
        }

        Log.d( TAG, "isExpired : exit" );
        return expired;
    }

    @Override
    public synchronized void evictAll() {
        Log.d( TAG, "evictAll : enter" );

        this.executeAsynchronously( new CacheEvictor( this.fileManager, this.cacheDir ) );

        Log.d( TAG, "evictAll : exit" );
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param id The id to build the file.
     * @return A valid file.
     */
    private File buildFile( int id ) {
        Log.d( TAG, "buildFile : enter" );

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append( this.cacheDir.getPath() );
        fileNameBuilder.append( File.separator );
        fileNameBuilder.append( DEFAULT_FILE_NAME );
        fileNameBuilder.append( id );
        Log.d(TAG, "buildFile : fileNameBuild=" + fileNameBuilder.toString());

        Log.d( TAG, "buildFile : exit" );
        return new File( fileNameBuilder.toString() );
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param category The category to build the file.
     * @return A valid file.
     */
    private File buildCategoryFile( String category ) {
        Log.d( TAG, "buildCategoryFile : enter" );

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append( this.cacheDir.getPath() );
        fileNameBuilder.append( File.separator );
        fileNameBuilder.append( DEFAULT_FILE_NAME );
        fileNameBuilder.append( category );
        Log.d(TAG, "buildCategoryFile : fileNameBuild=" + fileNameBuilder.toString());

        Log.d( TAG, "buildCategoryFile : exit" );
        return new File( fileNameBuilder.toString() );
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        Log.d( TAG, "setLastCacheUpdateTimeMillis : enter" );

        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences( this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis );

        Log.d( TAG, "setLastCacheUpdateTimeMillis : exit" );
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        Log.d( TAG, "getLastCacheUpdateTimeMillis : enter" );

        Log.d( TAG, "getLastCacheUpdateTimeMillis : exit" );
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

        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter( FileManager fileManager, File fileToWrite, String fileContent ) {

            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;

        }

        @Override public void run() {
            this.fileManager.writeToFile( fileToWrite, fileContent );
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {

        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor( FileManager fileManager, File cacheDir ) {

            this.fileManager = fileManager;
            this.cacheDir = cacheDir;

        }

        @Override public void run() {
            this.fileManager.clearDirectory( this.cacheDir );
        }

    }

}

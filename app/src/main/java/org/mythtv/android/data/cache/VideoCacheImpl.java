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

package org.mythtv.android.data.cache;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.data.cache.serializer.VideoEntityJsonSerializer;
import org.mythtv.android.data.cache.serializer.VideoListEntityJsonSerializer;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;
import org.mythtv.android.data.exception.VideoNotFoundException;
import org.mythtv.android.domain.executor.ThreadExecutor;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoCacheImpl implements VideoCache {

    private static final String TAG = VideoCacheImpl.class.getSimpleName();

    private static final String SETTINGS_FILE_NAME = "org.mythtv.android.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "video_last_cache_update";

    private static final String DEFAULT_FILE_NAME = "videos";
    private static final long EXPIRATION_TIME = 86400000;   // cache for 1 day

    private final Context context;
    private final File cacheDir;
    private final VideoEntityJsonSerializer videoEntityJsonSerializer;
    private final VideoListEntityJsonSerializer videoListEntityJsonSerializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link VideoCacheImpl}.
     *
     * @param context A
     * @param videoEntityJsonSerializer {@link VideoEntityJsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public VideoCacheImpl(Context context, VideoEntityJsonSerializer videoEntityJsonSerializer, VideoListEntityJsonSerializer videoListEntityJsonSerializer, FileManager fileManager, ThreadExecutor executor ) {
        Log.d( TAG, "initialize : enter" );

        if( context == null || videoEntityJsonSerializer == null || videoListEntityJsonSerializer == null || fileManager == null || executor == null ) {

            throw new IllegalArgumentException( "Invalid null parameter" );
        }

        this.context = context.getApplicationContext();
        this.cacheDir = new File( this.context.getCacheDir().getPath() + File.separator + "videos" );
        this.videoEntityJsonSerializer = videoEntityJsonSerializer;
        this.videoListEntityJsonSerializer = videoListEntityJsonSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void put( List<VideoMetadataInfoEntity> videos ) {
        Log.d( TAG, "put : enter" );

        if( null != videos && !videos.isEmpty() ) {
            Log.d( TAG, "put : videos is not null" );

            File videoEntitiesFile = this.buildFile();

            if( !isCached() ) {
                Log.d( TAG, "put : videos are not cached" );

                VideoMetadataInfoListEntity videoEntities = new VideoMetadataInfoListEntity();
                videoEntities.setVideoMetadataInfosEntity( videos.toArray( new VideoMetadataInfoEntity[ videos.size() ] ) );

                String jsonString = this.videoListEntityJsonSerializer.serialize( videoEntities );
                this.executeAsynchronously( new CacheWriter( this.fileManager, videoEntitiesFile, jsonString ) );
                setLastCacheUpdateTimeMillis();

            }

        }

        Log.d( TAG, "put : exit" );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> get( final int id ) {
        Log.d( TAG, "get : enter" );
        Log.d( TAG, "get : id=" + id );

        return readFromFile()
                .flatMap( Observable::from )
                .filter( entity -> entity.getId() == id );

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( final String category ) {
        Log.d( TAG, "getCategory : enter" );
        Log.d( TAG, "getCategory : category=" + category );

        if( !isCached() ) {
            Log.d( TAG, "getCategory : exit, not cached on disk" );

            return Observable.empty();
        }

        return readFromFile()
                .flatMap( Observable::from )
                .filter( entity -> entity.getContentType().equals( category ) )
//                .distinct( videoMetadataInfoEntity -> videoMetadataInfoEntity.getTitle() )
                .doOnNext( entity -> Log.d( TAG, "getCategory : entity=" + entity ) )
                .toList();

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getDirectory( final String directory ) {
        Log.d( TAG, "getDirectory : enter" );
        Log.d( TAG, "getDirectory : directory=" + directory );

        if( !isCached() ) {
            Log.d( TAG, "getDirectory : exit, not cached on disk" );

            return Observable.empty();
        }

        return readFromFile()
                .flatMap( Observable::from )
                .filter( entity -> entity.getFileName().startsWith( directory ) )
                .toList();
    }

    @Override
    public boolean isCached() {
        Log.d( TAG, "isCached : enter" );

        File videoEntityFile = this.buildFile();

        boolean isCached = this.fileManager.exists( videoEntityFile );
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

        this.executeAsynchronously( new CacheEvictor( this.fileManager, this.cacheDir ) );

        Log.d( TAG, "evictAll : exit" );
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @return A valid file.
     */
    private File buildFile() {
        Log.v( TAG, "buildFile : enter" );

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append( this.cacheDir.getPath() );

        File dir = new File( fileNameBuilder.toString() );
        if( !dir.exists() ) {
            dir.mkdirs();
        }

        fileNameBuilder.append( File.separator );
        fileNameBuilder.append( DEFAULT_FILE_NAME );
        Log.v( TAG, "buildFile : fileNameBuild=" + fileNameBuilder.toString() );

        Log.v( TAG, "buildFile : exit" );
        return new File( fileNameBuilder.toString() );
    }

    private Observable<List<VideoMetadataInfoEntity>> readFromFile() {
        Log.v( TAG, "readFromFile : enter" );

        File file = VideoCacheImpl.this.buildFile();
        String fileContent = VideoCacheImpl.this.fileManager.readFileContent( file );
        Log.v( TAG, "readFromFile : fileContent=" + fileContent );

        VideoMetadataInfoListEntity videoMetadataInfoListEntity = VideoCacheImpl.this.videoListEntityJsonSerializer.deserialize( fileContent );
        Log.v( TAG, "readFromFile : videoMetadataInfoListEntity=" + videoMetadataInfoListEntity );

        Log.v( TAG, "readFromFile : exit" );
        return Observable
                .from( videoMetadataInfoListEntity.getVideoMetadataInfosEntity() )
                .toList()
                .doOnError( VideoNotFoundException::new );
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

        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter( FileManager fileManager, File fileToWrite, String fileContent ) {

            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;

        }

        @Override
        public void run() {
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

        @Override
        public void run() {
            this.fileManager.clearDirectory( this.cacheDir );
        }

    }

}

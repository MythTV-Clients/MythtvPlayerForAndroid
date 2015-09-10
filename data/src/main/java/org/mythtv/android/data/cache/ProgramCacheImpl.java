package org.mythtv.android.data.cache;

import android.content.Context;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.serializer.ProgramEntityJsonSerializer;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 8/26/15.
 */
@Singleton
public class ProgramCacheImpl implements ProgramCache {

    private static final String SETTINGS_FILE_NAME = "org.mythtv.android.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "program_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final ProgramEntityJsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link ProgramCacheImpl}.
     *
     * @param context A
     * @param recordedProgramCacheSerializer {@link ProgramEntityJsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public ProgramCacheImpl(Context context, ProgramEntityJsonSerializer recordedProgramCacheSerializer, FileManager fileManager, ThreadExecutor executor) {

        if( context == null || recordedProgramCacheSerializer == null || fileManager == null || executor == null ) {
            throw new IllegalArgumentException( "Invalid null parameter" );
        }

        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = recordedProgramCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<ProgramEntity> get( int chanId, DateTime startTime ) {
        return Observable.create( new Observable.OnSubscribe<ProgramEntity>() {
            @Override public void call( Subscriber<? super ProgramEntity> subscriber ) {
                File programEntityFile = ProgramCacheImpl.this.buildFile( chanId, startTime );
                String fileContent = ProgramCacheImpl.this.fileManager.readFileContent( programEntityFile );
                ProgramEntity programEntity = ProgramCacheImpl.this.serializer.deserialize( fileContent );

                if( null != programEntity ) {

                    subscriber.onNext( programEntity );
                    subscriber.onCompleted();

                } else {

                    subscriber.onError( new ProgramNotFoundException() );

                }

            }

        });
    }

    @Override
    public void put( ProgramEntity programEntity ) {

        if( null != programEntity ) {

            File userEntitiyFile = this.buildFile( programEntity.getChannel().getChanId(), programEntity.getRecording().getStartTs() );

            if( !isCached( programEntity.getChannel().getChanId(), programEntity.getRecording().getStartTs() ) ) {

                String jsonString = this.serializer.serialize( programEntity );
                this.executeAsynchronously(new CacheWriter( this.fileManager, userEntitiyFile, jsonString ) );
                setLastCacheUpdateTimeMillis();

            }
        }

    }

    @Override
    public boolean isCached( int chanId, DateTime startTime ) {

        File programEntitiyFile = this.buildFile( chanId, startTime );

        return this.fileManager.exists( programEntitiyFile );
    }

    @Override
    public boolean isExpired() {

        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ( ( currentTime - lastUpdateTime ) > EXPIRATION_TIME );

        if( expired ) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public synchronized void evictAll() {

        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));

    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param chanId The id channel to build the file.
     * @param startTime The startime to build the file.
     * @return A valid file.
     */
    private File buildFile( int chanId, DateTime startTime ) {

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append( this.cacheDir.getPath() );
        fileNameBuilder.append( File.separator );
        fileNameBuilder.append( DEFAULT_FILE_NAME );
        fileNameBuilder.append( chanId );
        fileNameBuilder.append( "_" );
        fileNameBuilder.append( startTime.getMillis() );

        return new File( fileNameBuilder.toString() );
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {

        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences( this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis );

    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
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

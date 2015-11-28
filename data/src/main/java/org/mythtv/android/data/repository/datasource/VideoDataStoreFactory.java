package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.net.VideoApi;
import org.mythtv.android.data.net.VideoApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoDataStoreFactory {

    private static final String TAG = VideoDataStoreFactory.class.getSimpleName();

    private final Context context;
    private final VideoCache videoCache;

    @Inject
    public VideoDataStoreFactory( Context context, VideoCache videoCache ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == videoCache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.videoCache = videoCache;

        Log.d( TAG, "initialize : exit" );
    }

    public VideoDataStore create( int id ) {
        Log.d( TAG, "create : enter" );

        VideoDataStore videoDataStore;

        if( !this.videoCache.isExpired() && this.videoCache.isCached( id ) ) {
            Log.d( TAG, "create : cache is not expired and video exists in cache" );

            videoDataStore = new DiskVideoDataStore( this.videoCache );

        } else {
            Log.d( TAG, "create : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "create : exit" );
        return videoDataStore;
    }

    public VideoDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper();
        VideoApi api = new VideoApiImpl( this.context, videoMetadataInfoEntityJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendVideoDataStore( api, this.videoCache );
    }

    public VideoDataStore createCategory( String category ) {
        Log.d( TAG, "createCategory : enter" );

        VideoDataStore videoDataStore;

        if( !this.videoCache.isExpired() && this.videoCache.isCategoryCached( category ) ) {
            Log.d( TAG, "createCategory : cache is not expired and category exists in cache" );

            videoDataStore = new DiskVideoDataStore( this.videoCache );

        } else {
            Log.d( TAG, "createCategory : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "createCategory : exit" );
        return videoDataStore;
    }

}

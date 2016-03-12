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
    private final SearchDataStoreFactory searchDataStoreFactory;

    @Inject
    public VideoDataStoreFactory( Context context, VideoCache videoCache, SearchDataStoreFactory searchDataStoreFactory ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == videoCache || null == searchDataStoreFactory ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.videoCache = videoCache;
        this.searchDataStoreFactory = searchDataStoreFactory;

        Log.d( TAG, "initialize : exit" );
    }

    public VideoDataStore create() {
        Log.d( TAG, "create : enter" );

        VideoDataStore videoDataStore;

        if( !this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "create : cache is not expired and videos exists in cache" );

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
        return new MasterBackendVideoDataStore( api, this.videoCache, this.searchDataStoreFactory );
    }

    public VideoDataStore createCategoryDataStore( String category ) {
        Log.d( TAG, "createCategoryDataStore : enter" );

        VideoDataStore videoDataStore;

        if( this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "createCategoryCategoryDataStore : cache is not expired" );

            videoDataStore = new DiskVideoDataStore( this.videoCache );

        } else {
            Log.d( TAG, "createCategoryCategoryDataStore : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "createCategoryDataStore : exit" );
        return videoDataStore;
    }

}

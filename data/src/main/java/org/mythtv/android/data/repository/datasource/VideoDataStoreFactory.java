package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.data.cache.MemoryVideoCache;
import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
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
    private final MemoryVideoCache memoryVideoCache;
    private final MemoryVideoDataStore memoryVideoDataStore;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final SearchResultEntityDataMapper searchResultEntityDataMapper;

    @Inject
    public VideoDataStoreFactory( Context context, VideoCache videoCache, MemoryVideoCache memoryVideoCache, MemoryVideoDataStore memoryVideoDataStore, SearchDataStoreFactory searchDataStoreFactory, SearchResultEntityDataMapper searchResultEntityDataMapper ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == videoCache || null == memoryVideoCache || null == memoryVideoDataStore || null == searchDataStoreFactory || null == searchResultEntityDataMapper ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.videoCache = videoCache;
        this.memoryVideoCache = memoryVideoCache;
        this.memoryVideoDataStore = memoryVideoDataStore;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.searchResultEntityDataMapper = searchResultEntityDataMapper;

        Log.d( TAG, "initialize : exit" );
    }

    public VideoDataStore create() {
        Log.d( TAG, "create : enter" );

        VideoDataStore videoDataStore;

        if( !this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "create : cache is not expired and videos exists in cache" );

            videoDataStore = new DiskVideoDataStore( this.videoCache, this.memoryVideoCache );

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
        return new MasterBackendVideoDataStore( api, this.videoCache, this.memoryVideoCache, this.searchDataStoreFactory, this.searchResultEntityDataMapper );
    }

    public VideoDataStore createCategoryDataStore( String category ) {
        Log.d( TAG, "createCategoryDataStore : enter" );

        VideoDataStore videoDataStore;

        if( !this.memoryVideoCache.isExpired() && this.memoryVideoCache.isCached( category ) ) {
            Log.d( TAG, "createCategoryDataStore : memory cache is not expired and videos exists in cache" );

            videoDataStore = this.memoryVideoDataStore;

        } else if( this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "createCategoryCategoryDataStore : cache is not expired" );

            videoDataStore = new DiskVideoDataStore( this.videoCache, this.memoryVideoCache );

        } else {
            Log.d( TAG, "createCategoryCategoryDataStore : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "createCategoryDataStore : exit" );
        return videoDataStore;
    }

}

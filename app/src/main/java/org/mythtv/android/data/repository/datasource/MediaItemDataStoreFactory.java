package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.data.entity.MediaItemEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/17/17.
 */
@Singleton
public class MediaItemDataStoreFactory {

    private static final String TAG = MediaItemDataStoreFactory.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final VideoDataStoreFactory videoDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final DualCache<MediaItemEntity> cache;

    @Inject
    public MediaItemDataStoreFactory(
            final DvrDataStoreFactory dvrDataStoreFactory,
            final VideoDataStoreFactory videoDataStoreFactory,
            final ContentDataStoreFactory contentDataStoreFactory,
            final SearchDataStoreFactory searchDataStoreFactory,
            final DualCache<MediaItemEntity> cache
    ) {

        if( null == dvrDataStoreFactory || null == videoDataStoreFactory || null == contentDataStoreFactory || null == searchDataStoreFactory || null == cache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.videoDataStoreFactory = videoDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.cache = cache;

    }

    public MediaItemDataStore create( final int id ) {
        Log.d( TAG, "create : enter" );

        if( cache.contains( String.valueOf( id ) ) ) {

            Log.d( TAG, "create : exit, getting mediaItemEntity from cache" );
            return new DiskMediaItemDataStore( this.dvrDataStoreFactory, this.contentDataStoreFactory, cache );

        } else {

            Log.d( TAG, "create : exit, getting mediaItemEntity from master backend" );
            return createMasterBackendDataStore();
        }


    }

    public MediaItemDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendMediaItemDataStore( this.dvrDataStoreFactory, this.videoDataStoreFactory, this.contentDataStoreFactory, this.searchDataStoreFactory, this.cache );
    }

}

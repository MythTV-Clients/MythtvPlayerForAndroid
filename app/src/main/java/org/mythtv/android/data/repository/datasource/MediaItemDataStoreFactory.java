package org.mythtv.android.data.repository.datasource;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.domain.MediaItem;

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

    private final DualCache<MediaItem> cache;

    @Inject
    public MediaItemDataStoreFactory( final DualCache<MediaItem> cache ) {

        if( null == cache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.cache = cache;

    }

}

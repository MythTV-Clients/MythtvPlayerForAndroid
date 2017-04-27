package org.mythtv.android.data.repository.datasource;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.domain.Media;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/21/17.
 */
@Singleton
public class DiskMediaItemDataStore implements MediaItemDataStore {

    private static final String UNSUPPORTED_OPERATION_EXCEPTION_VALUE = "this call should always go to the backend";

    private final DualCache<MediaItemEntity> cache;

    @Inject
    public DiskMediaItemDataStore( final DualCache<MediaItemEntity> cache ) {

        this.cache = cache;

    }

    @Override
    public Observable<List<SeriesEntity>> series( final Media media ) {

        throw new UnsupportedOperationException( UNSUPPORTED_OPERATION_EXCEPTION_VALUE );
    }

    @Override
    public Observable<List<MediaItemEntity>> mediaItems( final Media media, @Nullable final String title ) {

        throw new UnsupportedOperationException( UNSUPPORTED_OPERATION_EXCEPTION_VALUE );
    }

    @Override
    public Observable<MediaItemEntity> mediaItem( final Media media, final int id ) {

        return Observable.just( cache.get( String.valueOf( id ) ) );
    }

    @Override
    public Observable<MediaItemEntity> addLiveStream( final Media media, final int id ) {

        throw new UnsupportedOperationException( UNSUPPORTED_OPERATION_EXCEPTION_VALUE );
    }

    @Override
    public Observable<MediaItemEntity> removeLiveStream( final Media media, final int id ) {

        throw new UnsupportedOperationException( UNSUPPORTED_OPERATION_EXCEPTION_VALUE );
    }

    @Override
    public Observable<MediaItemEntity> updateWatchedStatus( final Media media, final int id, final boolean watched ) {

        throw new UnsupportedOperationException( UNSUPPORTED_OPERATION_EXCEPTION_VALUE );
    }

}

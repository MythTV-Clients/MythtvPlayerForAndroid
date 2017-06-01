package org.mythtv.android.data.repository;

import org.mythtv.android.data.entity.mapper.MediaItemDataMapper;
import org.mythtv.android.data.entity.mapper.SeriesDataMapper;
import org.mythtv.android.data.repository.datasource.MediaItemDataStore;
import org.mythtv.android.data.repository.datasource.MediaItemDataStoreFactory;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/14/17.
 */
@Singleton
public class MediaItemDataRepository implements MediaItemRepository {

    private static final String CONVERT2METHODREF = "Convert2MethodRef";

    private final MediaItemDataStoreFactory mediaItemDataStoreFactory;

    @Inject
    public MediaItemDataRepository( final MediaItemDataStoreFactory mediaItemDataStoreFactory ) {

        if( null == mediaItemDataStoreFactory ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.mediaItemDataStoreFactory = mediaItemDataStoreFactory;

    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<List<Series>> series( Media media ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        return mediaItemDataStore.series( media )
                .map( SeriesDataMapper::transform );
    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<List<MediaItem>> mediaItems( final Media media, @Nullable final String title ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        return mediaItemDataStore.mediaItems( media, title )
                .map( MediaItemDataMapper::transformMediaItems );

    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<MediaItem> mediaItem( final Media media, final int id ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.create( id );

        return mediaItemDataStore.mediaItem( media, id )
                .map( MediaItemDataMapper::transform );
    }

    @Override
    public Observable<MediaItem> addLiveStream( final Media media, final int id ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        return mediaItemDataStore.addLiveStream( media, id )
                .map( MediaItemDataMapper::transform );
    }

    @Override
    public Observable<MediaItem> removeLiveStream( final Media media, final int id ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        return mediaItemDataStore.removeLiveStream( media, id )
                .map( MediaItemDataMapper::transform );
    }

    @Override
    public Observable<MediaItem> updateWatchedStatus( Media media, int id, boolean watched ) {

        MediaItemDataStore mediaItemDataStore = mediaItemDataStoreFactory.createMasterBackendDataStore();

        return mediaItemDataStore.updateWatchedStatus( media, id, watched )
                .map( MediaItemDataMapper::transform );
    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<List<MediaItem>> search( String searchString ) {

        SearchDataStore searchDataStore = mediaItemDataStoreFactory.createSearchDataStore();

        return searchDataStore.search( searchString )
                .map( MediaItemDataMapper::transformMediaItems );
    }

}

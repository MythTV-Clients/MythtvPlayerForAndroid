package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.domain.Media;

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
 * Created on 4/21/17.
 */
@Singleton
public class DiskMediaItemDataStore implements MediaItemDataStore {

    private static final String TAG = DiskMediaItemDataStore.class.getSimpleName();
    private static final String UNSUPPORTED_OPERATION_EXCEPTION_VALUE = "this call should always go to the backend";

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;
    private final DualCache<MediaItemEntity> cache;

    @Inject
    public DiskMediaItemDataStore( final DvrDataStoreFactory dvrDataStoreFactory, final ContentDataStoreFactory contentDataStoreFactory, final DualCache<MediaItemEntity> cache ) {

        if( null == dvrDataStoreFactory || null == contentDataStoreFactory || null == cache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;
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

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<MediaItemEntity> cached = Observable.just( cache.get( String.valueOf( id ) ) );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList = cached
                .flatMap( entity -> contentDataStore.liveStreamInfoEntityList( entity.url() ) );
        Observable<Long> bookmark = dvrDataStore.getBookmark( id, "Duration" );

        Observable<MediaItemEntity> mediaItemEntity = Observable.zip( cached, liveStreamInfoEntityList, bookmark, (mediaItemEntity1, liveStreamInfoEntityList1, bookmark1 ) -> {

            LiveStreamInfoEntity liveStreamInfoEntity = null;
            if( null != liveStreamInfoEntityList1 && !liveStreamInfoEntityList1.isEmpty() ) {

                liveStreamInfoEntity = liveStreamInfoEntityList1.get( 0 );

            }

            return MediaItemEntity.create(
                    mediaItemEntity1.id(), mediaItemEntity1.media(), mediaItemEntity1.title(),
                    mediaItemEntity1.subTitle(), mediaItemEntity1.description(),
                    mediaItemEntity1.startDate(), mediaItemEntity1.programFlags(),
                    mediaItemEntity1.season(), mediaItemEntity1.episode(), mediaItemEntity1.studio(),
                    mediaItemEntity1.castMembers(), mediaItemEntity1.characters(),
                    ( null == liveStreamInfoEntity ? mediaItemEntity1.url() : liveStreamInfoEntity.relativeUrl() ),
                    mediaItemEntity1.fanartUrl(), mediaItemEntity1.coverartUrl(),
                    mediaItemEntity1.bannerUrl(), mediaItemEntity1.previewUrl(),
                    mediaItemEntity1.contentType(), mediaItemEntity1.duration(),
                    ( null == liveStreamInfoEntity ? mediaItemEntity1.percentComplete() : liveStreamInfoEntity.percentComplete() ),
                    mediaItemEntity1.recording(),
                    ( null == liveStreamInfoEntity ? mediaItemEntity1.liveStreamId() : liveStreamInfoEntity.id() ),
                    mediaItemEntity1.watched(), mediaItemEntity1.updateSavedBookmarkUrl(),
                    ( null == bookmark1 ? mediaItemEntity1.bookmark() : bookmark1 ),
                    mediaItemEntity1.inetref(),
                    mediaItemEntity1.certification(), mediaItemEntity1.parentalLevel(),
                    mediaItemEntity1.recordingGroup(), mediaItemEntity1.validationErrors()
            );

        });

        return mediaItemEntity
                .doOnError( throwable -> Log.e( TAG, "mediaItem : error", throwable ) );

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

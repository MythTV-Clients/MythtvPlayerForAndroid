package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.MediaItemEntityDataMapper;
import org.mythtv.android.data.entity.mapper.SeriesEntityDataMapper;
import org.mythtv.android.domain.Media;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/21/17.
 */
@Singleton
public class MasterBackendMediaItemDataStore implements MediaItemDataStore {

    private static final String TAG = MasterBackendMediaItemDataStore.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final VideoDataStoreFactory videoDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final DualCache<MediaItemEntity> cache;

    private final Consumer<List<SeriesEntity>> removeStaleTitleInfosDbAction =
            titleInfoEntities -> {

                if( null != titleInfoEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendMediaItemDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                            .fromIterable( titleInfoEntities )
                            .toList()
                            .subscribe( searchDataStore::refreshSeriesData );
                }

            };

    private final Consumer<List<MediaItemEntity>> saveMediaItemsToCacheAction =
            mediaItemEntities -> {

                if( null != mediaItemEntities ) {

                    Observable
                            .fromIterable( mediaItemEntities )
                            .doOnNext( mediaItemEntity -> MasterBackendMediaItemDataStore.this.cache.put( String.valueOf( mediaItemEntity.id() ), mediaItemEntity ) )
                            .subscribe();
                }

            };

    private final Consumer<List<MediaItemEntity>> saveRecordedProgramMediaItemsToDbAction =
            mediaItemEntities -> {

                if( null != mediaItemEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendMediaItemDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                            .fromIterable( mediaItemEntities )
                            .toList()
                            .subscribe( searchDataStore::refreshRecordedProgramData );
                }

            };

    private final Consumer<List<MediaItemEntity>> saveVideoMediaItemsToDbAction =
            mediaItemEntities -> {

                if( null != mediaItemEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendMediaItemDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                            .fromIterable( mediaItemEntities )
                            .toList()
                            .subscribe( searchDataStore::refreshVideoData );
                }

            };

    @Inject
    public MasterBackendMediaItemDataStore(
            final DvrDataStoreFactory dvrDataStoreFactory,
            final VideoDataStoreFactory videoDataStoreFactory,
            final ContentDataStoreFactory contentDataStoreFactory,
            final SearchDataStoreFactory searchDataStoreFactory,
            final DualCache<MediaItemEntity> cache
    ) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.videoDataStoreFactory = videoDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.cache = cache;

    }

    @Override
    public Observable<List<SeriesEntity>> series( final Media media ) {

        if( Media.PROGRAM.equals( media ) ) {

            return recordingSeriesEntity()
                    .doOnNext( removeStaleTitleInfosDbAction );

        } else if( Media.VIDEO.equals( media ) || Media.TELEVISION.equals( media ) ) {

            return videoSeriesEntities( media );

        } else {

            throw new IllegalArgumentException( "media not supported" );
        }

    }

    @Override
    public Observable<List<MediaItemEntity>> mediaItems( final Media media, @Nullable final String title ) {

        if( Media.UPCOMING.equals( media ) ) {

            return upcoming();

        } else if( Media.RECENT.equals( media ) ) {

            return recent()
                    .doOnNext( saveMediaItemsToCacheAction )
                    .doOnNext( saveRecordedProgramMediaItemsToDbAction );

        } else if( Media.PROGRAM.equals( media ) ) {

            return recordedProgramsInSeries( title )
                    .doOnNext( saveMediaItemsToCacheAction )
                    .doOnNext( saveRecordedProgramMediaItemsToDbAction );

        } else if( Media.TELEVISION.equals( media ) ) {

            return videosInCategory( media )
                    .flatMap( Observable::fromIterable )
                    .filter( entity -> entity.title().equals( title ) )
                    .toList()
                    .toObservable();

        } else {

            return videosInCategory( media )
                    .doOnNext( saveMediaItemsToCacheAction )
                    .doOnNext( saveVideoMediaItemsToDbAction );
        }

    }

    @Override
    public Observable<MediaItemEntity> mediaItem( final Media media, final int id ) {
        Log.d( TAG, "mediaItem : enter" );

        if( media.equals( Media.PROGRAM ) || media.equals( Media.UPCOMING ) || media.equals( Media.RECENT ) ) {

            return recordedProgram( id );

        } else {

            return video( id );
        }

    }

    @Override
    public Observable<MediaItemEntity> addLiveStream( final Media media, final int id ) {
        Log.d( TAG, "addLiveStream : enter" );

        ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();
        Observable<MediaItemEntity> mediaItem = mediaItem( media, id );

        Observable<LiveStreamInfoEntity> addLiveStream = contentDataStore.addLiveStream( media, id )
                .doOnError( throwable -> Log.e( TAG, "removeLiveStream : error", throwable ) );

        return Observable.zip( addLiveStream, mediaItem, ( addLiveStream1, mediaItem1 ) -> mediaItem1 );
    }

    @Override
    public Observable<MediaItemEntity> removeLiveStream( final Media media, final int id ) {
        Log.d( TAG, "removeLiveStream : enter" );

        ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return mediaItem( media, id )
                .switchMap( mediaItemEntity -> contentDataStore.removeLiveStream( mediaItemEntity.liveStreamId() ) )
                .switchMap( response -> mediaItem( media, id ) )
                .doOnNext( mediaItemEntity -> Log.i( TAG, "removeLiveStream : " + mediaItemEntity.toString() ));
    }

    @Override
    public Observable<MediaItemEntity> updateWatchedStatus( final Media media, final int id, final boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        if( media.equals( Media.UPCOMING ) ) {

            throw new IllegalArgumentException( "can't mark an upcoming program as watched" );
        }

        Observable<MediaItemEntity> mediaItem = mediaItem( media, id );

        if( media.equals( Media.PROGRAM ) || media.equals( Media.RECENT ) ) {

            DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

            Observable<Boolean> updateWatchStatus = dvrDataStore.updateWatchedStatus( id, watched )
                    .doOnError( throwable -> Log.e( TAG, "updateWatchedStatus : error", throwable ) );

            return Observable.zip( updateWatchStatus, mediaItem, ( updateWatchStatus1, mediaItem1 ) -> mediaItem1 );

        } else {

            VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

            Observable<Boolean> updateWatchStatus = videoDataStore.updateWatchedStatus( id, watched )
                    .doOnError( throwable -> Log.e( TAG, "updateWatchedStatus : error", throwable ) );

            return Observable.zip( updateWatchStatus, mediaItem, ( updateWatchStatus1, mediaItem1 ) -> mediaItem1 );

        }

    }

    private Observable<List<SeriesEntity>> recordingSeriesEntity() {
        Log.d( TAG, "recordingSeries : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.titleInfoEntityList()
                .doOnError( throwable -> Log.e( TAG, "recordingSeriesEntity : error", throwable ) )
                .map( SeriesEntityDataMapper::transformTitleInfoEntities );
    }

    private Observable<List<SeriesEntity>> videoSeriesEntities( final Media media ) {
        Log.d( TAG, "videoSeriesEntities : enter" );

        final VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.getCategory( media.name() )
                .doOnError( throwable -> Log.e( TAG, "getVideoSeriesListByContentType : error", throwable ) )
                .flatMap( Observable::fromIterable )
                .filter( entity -> entity.contentType().equals( media.name() ) )
                .distinct( VideoMetadataInfoEntity::title )
                .toSortedList( ( entity1, entity2 ) -> entity1.title().compareTo( entity2.title() ) )
                .map( SeriesEntityDataMapper::transformVideos )
                .toObservable();
    }

    private Observable<List<MediaItemEntity>> upcoming() {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.upcomingProgramEntityList( 1, -1, true, -1, -1 )
                .map( MediaItemEntityDataMapper::transformPrograms );
    }

    private Observable<List<MediaItemEntity>> recent() {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<ProgramEntity>> programEntities = dvrDataStore.recordedProgramEntityList( true, 0, 50, null, null, null );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList( null );

        Observable<List<ProgramEntity>> recordedProgramEntityList = Observable.zip( programEntities, liveStreamInfoEntities, ( programEntityList, list ) -> {

            if( null != list && !list.isEmpty() ) {

                for( ProgramEntity programEntity : programEntityList ) {

                    for( LiveStreamInfoEntity liveStreamInfoEntity : list ) {

                        if( liveStreamInfoEntity.sourceFile().endsWith( programEntity.fileName() ) ) {

                            programEntity.setLiveStreamInfoEntity( liveStreamInfoEntity );

                        }

                    }

                }
            }

            return programEntityList;
        });

        // Limit results to 50, then remove anything in the LiveTV storage group and only take 10 for the final results
        return recordedProgramEntityList
                .take( 10 )
                .map( MediaItemEntityDataMapper::transformPrograms );
    }

    private Observable<List<MediaItemEntity>> recordedProgramsInSeries( final String title ) {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.recordedProgramEntityList( true, 0, -1, title, null, null )
                .map( MediaItemEntityDataMapper::transformPrograms );
    }

    private Observable<List<MediaItemEntity>> videosInCategory( final Media media ) {

        final VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.getCategory( media.name() )
                .map( videoEntities -> {
                    try {

                        return MediaItemEntityDataMapper.transformVideos( videoEntities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "videosInCategory : error", e );
                    }

                    return new ArrayList<>();
                });
    }

    private Observable<MediaItemEntity> recordedProgram( final int id ) {
        Log.d( TAG, "recordedProgram : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<ProgramEntity> programEntity = dvrDataStore.recordedProgramEntityDetails( id );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntity = programEntity
                .flatMap( entity -> contentDataStore.liveStreamInfoEntityList( entity.fileName() ) );
        Observable<Long> bookmark = dvrDataStore.getBookmark( id, "Duration" );

        Observable<ProgramEntity> recordedProgramEntity = Observable.zip( programEntity, liveStreamInfoEntity, bookmark, ( programEntity1, liveStreamInfoEntityList, bookmark1 ) -> {

            if( null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty() ) {

                programEntity1.setLiveStreamInfoEntity( liveStreamInfoEntityList.get( 0 ) );

            }

            programEntity1.setBookmark( bookmark1 );

            Log.d( TAG, "recordedProgram : programEntity=" + programEntity1.toString() );
            return programEntity1;
        });

        return recordedProgramEntity
                .doOnError( throwable -> Log.e( TAG, "recordedProgram : error", throwable ) )
                .map( MediaItemEntityDataMapper::transform );
    }

    private Observable<MediaItemEntity> video( final int id ) {

        final VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<VideoMetadataInfoEntity> videoEntity = videoDataStore.getVideoById( id );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntity = videoEntity
                .flatMap( entity -> contentDataStore.liveStreamInfoEntityList( entity.fileName() ) );

        Observable<VideoMetadataInfoEntity> video = Observable.zip( videoEntity, liveStreamInfoEntity, ( videoEntity1, liveStreamInfoEntityList ) -> {

            if( null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty() ) {

                videoEntity1.setLiveStreamInfoEntity( liveStreamInfoEntityList.get( 0 ) );

            }

            Log.d( TAG, "video : videoEntity=" + videoEntity1.toString() );
            return videoEntity1;
        });


        return video
                .map( entity -> {
                    try {

                        return MediaItemEntityDataMapper.transform( entity );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "video : error", e );
                    }

                    return null;
                })
;
    }

}

package org.mythtv.android.data.repository;

import android.util.Log;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.mapper.MediaItemDataMapper;
import org.mythtv.android.data.entity.mapper.SeriesDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
 * Created on 4/14/17.
 */
@Singleton
public class MediaItemDataRepository implements MediaItemRepository {

    private static final String TAG = MediaItemDataRepository.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final VideoDataStoreFactory videoDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;
    private final DualCache<MediaItem> cache;

    @Inject
    public MediaItemDataRepository(
            final DvrDataStoreFactory dvrDataStoreFactory,
            final VideoDataStoreFactory videoDataStoreFactory,
            final ContentDataStoreFactory contentDataStoreFactory,
            final DualCache<MediaItem> cache
    ) {

        if( null == dvrDataStoreFactory || null == videoDataStoreFactory || null == contentDataStoreFactory || null == cache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.videoDataStoreFactory = videoDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;
        this.cache = cache;

    }

    @Override
    public Observable<List<Series>> series( Media media ) {

        if( Media.PROGRAM.equals( media ) ) {

            return recordingSeries();

        } else if( Media.TELEVISION.equals( media ) ) {

            return null;

        } else {

            throw new IllegalArgumentException( "media not supported" );
        }

    }

    @Override
    public Observable<List<MediaItem>> mediaItems( final Media media, @Nullable final String title ) {

        if( Media.UPCOMING.equals( media ) ) {

            return upcoming();

        } else if( Media.RECENT.equals( media ) ) {

            return recent();

        } else if( Media.PROGRAM.equals( media ) ) {

            return recordingsInSeries( title );

        } else {

            throw new IllegalArgumentException( "media not supported" );
        }

    }

    @Override
    public Observable<MediaItem> mediaItem( int id ) {
        return null;
    }

    @Override
    public Observable<List<MediaItem>> search( String searchString ) {
        return null;
    }

    private Observable<List<MediaItem>> recent() {
        Log.d( TAG, "recent : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<ProgramEntity>> programEntities = dvrDataStore.recordedProgramEntityList( true, 1, 50, null, null, null );
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
                .flatMap( Observable::from )
                .filter( programEntity -> !programEntity.recording().storageGroup().equalsIgnoreCase( "LiveTV" ) )
                .take( 10 )
                .toList()
                .map( recordedProgramEntities -> {
                    try {

                        return MediaItemDataMapper.transformPrograms( recordedProgramEntities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "recent : error", e );
                    }

                    return new ArrayList<>();
                });
    }

    private Observable<List<MediaItem>> upcoming() {

        DvrDataStore dvrDataStore = dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.upcomingProgramEntityList( 1, -1, true, -1, -1 )
                .map( upcomingPrograms -> {
                    try {

                        return MediaItemDataMapper.transformPrograms(upcomingPrograms);

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "upcoming : error", e );
                    }

                    return new ArrayList<>();
                });

    }

    private Observable<List<Series>> recordingSeries() {
        Log.d( TAG, "recordingSeries : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.titleInfoEntityList()
                .doOnError( throwable -> Log.e( TAG, "recordingSeries : error", throwable ) )
                .map( titleInfoEntities -> SeriesDataMapper.transformPrograms( titleInfoEntities ) );
    }

    private Observable<List<MediaItem>> recordingsInSeries( final String title ) {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<ProgramEntity>> programEntities = dvrDataStore.recordedProgramEntityList( true, 1, -1, title, null, null );
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

        return recordedProgramEntityList
                .map( recordedProgramEntities -> {
                    try {

                        return MediaItemDataMapper.transformPrograms( recordedProgramEntities );

                    } catch( UnsupportedEncodingException e ) {
                        Log.e( TAG, "recordedPrograms : error", e );
                    }

                    return new ArrayList<>();
                });

    }
}

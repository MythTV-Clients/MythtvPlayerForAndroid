package org.mythtv.android.data.repository;

import android.util.Log;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.MediaItemDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.VideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.repository.ContentRepository;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/2/17.
 */
@Singleton
public class ContentDataRepository implements ContentRepository {

    private static final String TAG = ContentDataRepository.class.getSimpleName();
    private static final String CONVERT2METHODREF = "Convert2MethodRef";

    private final ContentDataStoreFactory contentDataStoreFactory;
    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final VideoDataStoreFactory videoDataStoreFactory;

    @Inject
    public ContentDataRepository( final ContentDataStoreFactory contentDataStoreFactory, final DvrDataStoreFactory dvrDataStoreFactory, final VideoDataStoreFactory videoDataStoreFactory ) {

        this.contentDataStoreFactory = contentDataStoreFactory;
        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.videoDataStoreFactory = videoDataStoreFactory;

    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<MediaItem> addLiveStream( final int id, final Media media ) {

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        if( Media.PROGRAM.equals( media ) ) {

            final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

            Observable<LiveStreamInfoEntity> liveStreamInfoEntity = contentDataStore.addLiveStream( id, media )
                    .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                    .map( entity ->  entity );

            Observable<ProgramEntity> programEntity = dvrDataStore.recordedProgramEntityDetails( id );

            Observable<ProgramEntity> recordedProgramEntity = Observable.zip( liveStreamInfoEntity, programEntity, ( liveStreamInfoEntity1, programEntity1 ) -> {

                programEntity1.setLiveStreamInfoEntity( liveStreamInfoEntity1 );

                return programEntity1;
            });

            return recordedProgramEntity
                    .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                    .map( recordedProgram  -> {
                        try {

                            return MediaItemDataMapper.transform( recordedProgram );

                        } catch( UnsupportedEncodingException e ) {
                            Log.e( TAG, e.getLocalizedMessage(), e );
                        }

                        return null;
                    });

        } else {

            final VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

            Observable<LiveStreamInfoEntity> liveStreamInfoEntity = contentDataStore.addLiveStream( id, media )
                    .doOnError( throwable -> Log.e( TAG, "addLiveStream : error", throwable ) )
                    .map( entity ->  entity );

            Observable<VideoMetadataInfoEntity> videoEntity = videoDataStore.getVideoById( id );

            Observable<VideoMetadataInfoEntity> videoMetadataInfoEntity = Observable.zip( liveStreamInfoEntity, videoEntity, ( liveStreamInfoEntity1, videoEntity1 ) -> {

                videoEntity1.setLiveStreamInfoEntity( liveStreamInfoEntity1 );

                return videoEntity1;
            });

            return videoMetadataInfoEntity
                    .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                    .map( video  -> {
                        try {

                            return MediaItemDataMapper.transform( video );

                        } catch( UnsupportedEncodingException e ) {
                            Log.e( TAG, e.getLocalizedMessage(), e );
                        }

                        return null;
                    });

        }

    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<MediaItem> removeLiveStream( final int id, final Media media ) {

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<Boolean> removeliveStreamInfoEntity = contentDataStore.removeLiveStream( id )
                .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                .map( entity ->  entity );

        if( Media.PROGRAM.equals( media ) ) {

            final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

            Observable<ProgramEntity> programEntity = dvrDataStore.recordedProgramEntityDetails( id );

            Observable<ProgramEntity> recordedProgramEntity = Observable.zip( removeliveStreamInfoEntity, programEntity, ( removeliveStreamInfoEntity1, programEntity1 ) -> {

                programEntity1.setLiveStreamInfoEntity( null );

                return programEntity1;
            });

            return recordedProgramEntity
                    .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                    .map( recordedProgram  -> {
                        try {

                            return MediaItemDataMapper.transform( recordedProgram );

                        } catch( UnsupportedEncodingException e ) {
                            Log.e( TAG, e.getLocalizedMessage(), e );
                        }

                        return null;
                    });

        } else {

            final VideoDataStore videoDataStore = this.videoDataStoreFactory.createMasterBackendDataStore();

            Observable<VideoMetadataInfoEntity> videoEntity = videoDataStore.getVideoById( id );

            Observable<VideoMetadataInfoEntity> videoMetadataInfoEntity = Observable.zip( removeliveStreamInfoEntity, videoEntity, ( removeliveStreamInfoEntity1, videoEntity1 ) -> {

                videoEntity1.setLiveStreamInfoEntity( null );

                return videoEntity1;
            });

            return videoMetadataInfoEntity
                    .doOnError( throwable -> Log.e( TAG, throwable.getLocalizedMessage(), throwable ) )
                    .map( video  -> {
                        try {

                            return MediaItemDataMapper.transform( video );

                        } catch( UnsupportedEncodingException e ) {
                            Log.e( TAG, e.getLocalizedMessage(), e );
                        }

                        return null;
                    });

        }

    }

}

package org.mythtv.android.data.repository;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ContentDataRepository implements ContentRepository {

    private static final String TAG = ContentDataRepository.class.getSimpleName();

    private final ContentDataStoreFactory contentDataStoreFactory;

    @Inject
    public ContentDataRepository( ContentDataStoreFactory contentDataStoreFactory ) {

        this.contentDataStoreFactory = contentDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addliveStream( String storageGroup, String filename, String hostname ) {
        Log.d( TAG, "addliveStreamInfos : enter" );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addliveStream( storageGroup, filename, hostname )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addRecordingliveStream( int recordedId, int chanId, DateTime startTime ) {
        Log.d(TAG, "addRecordingliveStream : enter");

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addRecordingliveStream( recordedId, chanId, startTime )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addVideoliveStream( int id ) {
        Log.d(TAG, "addVideoliveStream : enter");

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addVideoliveStream( id )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<LiveStreamInfo>> liveStreamInfos( String filename ) {
        Log.d( TAG, "liveStreamInfos : enter" );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.liveStreamInfoEntityList( filename )
                .map( liveStreamInfoEntities -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> liveStreamInfo( int id ) {
        Log.d( TAG, "liveStreamInfo : enter" );
        Log.d( TAG, "liveStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.liveStreamInfoEntityDetails( id)
                .map(liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> removeLiveStream( int id ) {
        Log.d( TAG, "removeStreamInfo : enter" );
        Log.d( TAG, "removeStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.removeLiveStream( id );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> stopLiveStream( int id ) {
        Log.d( TAG, "stopStreamInfo : enter" );
        Log.d( TAG, "stopStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.stopLiveStream( id );
    }

}

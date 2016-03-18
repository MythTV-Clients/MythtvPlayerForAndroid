package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.net.ContentApi;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public class MasterBackendContentDataStore implements ContentDataStore {

    private static final String TAG = MasterBackendContentDataStore.class.getSimpleName();

    private final ContentApi api;

    public MasterBackendContentDataStore( ContentApi api ) {

        this.api = api;

    }

    @Override
    public Observable<LiveStreamInfoEntity> addliveStream( String storageGroup, String filename, String hostname ) {
        Log.d( TAG, "addliveStream : enter" );

        Log.d( TAG, "addliveStream : exit" );
        return this.api.addliveStream( storageGroup, filename, hostname );
    }

    @Override
    public Observable<LiveStreamInfoEntity> addRecordingliveStream( int recordedId, int chanId, DateTime startTime ) {
        Log.d( TAG, "addRecordingliveStream : enter" );

        Log.d( TAG, "addRecordingliveStream : exit" );
        return this.api.addRecordingliveStream( recordedId, chanId, startTime );
    }

    @Override
    public Observable<LiveStreamInfoEntity> addVideoliveStream( int id ) {
        Log.d( TAG, "addVideoliveStream : enter" );

        Log.d( TAG, "addVideoliveStream : exit" );
        return this.api.addVideoliveStream( id );
    }

    @Override
    public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( String filename ) {
        Log.d( TAG, "liveStreamInfoEntityList : enter" );

        Log.d( TAG, "liveStreamInfoEntityList : exit" );
        return this.api.liveStreamInfoEntityList( filename );
    }

    @Override
    public Observable<LiveStreamInfoEntity> liveStreamInfoEntityDetails( int id ) {
        Log.d( TAG, "liveStreamInfoEntityDetails : enter" );

        Log.d( TAG, "liveStreamInfoEntityDetails : exit" );
        return this.api.liveStreamInfoById( id );
    }

    @Override
    public Observable<Boolean> removeLiveStream( int id ) {
        Log.d( TAG, "removeLiveStream : enter" );

        Log.d( TAG, "removeLiveStream : exit" );
        return this.api.removeLiveStream(id);
    }

    @Override
    public Observable<Boolean> stopLiveStream( int id ) {
        Log.d( TAG, "stopLiveStream : enter" );

        Log.d( TAG, "stopLiveStream : exit" );
        return this.api.stopLiveStream( id );
    }
}

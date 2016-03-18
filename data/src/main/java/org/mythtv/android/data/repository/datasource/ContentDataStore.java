package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/17/15.
 */
public interface ContentDataStore {

    Observable<LiveStreamInfoEntity> addliveStream( final String storageGroup, final String filename, final String hostname );

    Observable<LiveStreamInfoEntity> addRecordingliveStream( final int recordedId, final int chanId, final DateTime startTime );

    Observable<LiveStreamInfoEntity> addVideoliveStream( final int id );

    Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename );

    Observable<LiveStreamInfoEntity> liveStreamInfoEntityDetails( final int id );

    Observable<Boolean> removeLiveStream( final int id );

    Observable<Boolean> stopLiveStream( final int id );

}

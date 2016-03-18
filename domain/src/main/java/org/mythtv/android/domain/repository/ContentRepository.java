package org.mythtv.android.domain.repository;

import org.joda.time.DateTime;
import org.mythtv.android.domain.LiveStreamInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface ContentRepository {

    Observable<LiveStreamInfo> addliveStream( final String storageGroup, final String filename, final String hostname );

    Observable<LiveStreamInfo> addRecordingliveStream( final int recordedId, final int chanId, final DateTime startTime );

    Observable<LiveStreamInfo> addVideoliveStream( final int id );

    Observable<List<LiveStreamInfo>> liveStreamInfos( String filename );

    Observable<LiveStreamInfo> liveStreamInfo( int id );

    Observable<Boolean> removeLiveStream( final int id );

    Observable<Boolean> stopLiveStream( final int id );

}

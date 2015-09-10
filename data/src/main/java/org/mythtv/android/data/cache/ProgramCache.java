package org.mythtv.android.data.cache;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.ProgramEntity;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface ProgramCache {

    Observable<ProgramEntity> get( final int chanId, final DateTime startTime );

    void put( ProgramEntity programEntity );

    boolean isCached( final int chanId, final DateTime startTime );

    boolean isExpired();

    void evictAll();

}

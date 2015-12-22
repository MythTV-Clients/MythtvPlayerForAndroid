package org.mythtv.android.data.cache;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/3/15.
 */
public interface MemoryVideoCache {

    void put( String key, List<VideoMetadataInfoEntity> videos);

    Observable<List<VideoMetadataInfoEntity>> get( final String key );

    boolean isCached( String key );

    boolean isExpired();

    void evictAll();

}

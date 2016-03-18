package org.mythtv.android.data.cache;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/3/15.
 */
public interface VideoCache {

    void put( List<VideoMetadataInfoEntity> videos );

    Observable<VideoMetadataInfoEntity> get( final int id );

    Observable<List<VideoMetadataInfoEntity>> getCategory( String category );

    Observable<List<VideoMetadataInfoEntity>> getDirectory( String directory );

    boolean isCached();

    boolean isExpired();

    void evictAll();

}

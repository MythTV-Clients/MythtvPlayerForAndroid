package org.mythtv.android.data.cache;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/3/15.
 */
public interface VideoCache {

    Observable<List<VideoMetadataInfoEntity>> getCategory( String category );

    void putCategory( List<VideoMetadataInfoEntity> videos );

    Observable<VideoMetadataInfoEntity> get( final int id );

    void put( VideoMetadataInfoEntity videoMetadataInfoEntity );

    boolean isCached( final int id );

    boolean isCategoryCached( final String category );

    boolean isExpired();

    void evictAll();

}

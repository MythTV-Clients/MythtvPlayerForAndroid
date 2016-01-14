package org.mythtv.android.data.repository.datasource;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/9/15.
 */
public interface VideoDataStore {

    Observable<List<VideoMetadataInfoEntity>> getVideos( final String folder, final String sort, final boolean descending, final int startIndex, final int count );

    Observable<List<VideoMetadataInfoEntity>> getCategory( String category );

    Observable<List<VideoMetadataInfoEntity>> getSeriesInCategory( String category, String series );

    Observable<VideoMetadataInfoEntity> getVideoById( final int id );

    Observable<VideoMetadataInfoEntity> getVideoByFilename( final String filename );

}

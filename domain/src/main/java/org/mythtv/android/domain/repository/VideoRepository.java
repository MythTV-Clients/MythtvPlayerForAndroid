package org.mythtv.android.domain.repository;

import org.mythtv.android.domain.VideoMetadataInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface VideoRepository {

    Observable<List<VideoMetadataInfo>> getVideoList( String folder, String sort, boolean descending, int startIndex, int count );

    Observable<List<VideoMetadataInfo>> getVideoListByContentType( String contentType );

    Observable<List<VideoMetadataInfo>> getVideoSeriesListByContentType( String contentType );

    Observable<List<VideoMetadataInfo>> getVideoListByContentTypeAndSeries( String contentType, String series );

    Observable<VideoMetadataInfo> getVideo( int id );

    Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched );

}

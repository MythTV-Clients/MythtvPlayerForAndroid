package org.mythtv.android.data.repository.datasource;

import org.mythtv.android.data.cache.MemoryVideoCache;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 12/20/15.
 */
public class MemoryVideoDataStore implements VideoDataStore {

    private final MemoryVideoCache memoryVideoCache;

    @Inject
    public MemoryVideoDataStore( MemoryVideoCache memoryVideoCache ) {

        this.memoryVideoCache = memoryVideoCache;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideos( String folder, String sort, boolean descending, int startIndex, int count ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getCategory( String category ) {

        return memoryVideoCache.get( category );
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( int id ) {

        return null;
    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoByFilename( String filename ) {

        return null;
    }

}

package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public class DiskDvrDataStore implements DvrDataStore {

    private final ProgramCache recordedProgramCache;

    public DiskDvrDataStore(ProgramCache recordedProgramCache) {

        this.recordedProgramCache = recordedProgramCache;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        return null;
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {

        return this.recordedProgramCache.get( chanId, startTime );
    }

}

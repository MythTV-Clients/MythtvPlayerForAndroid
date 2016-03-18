package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public class DiskDvrDataStore implements DvrDataStore {

    private static final String TAG = DiskDvrDataStore.class.getSimpleName();

    private final ProgramCache recordedProgramCache;

    public DiskDvrDataStore( ProgramCache recordedProgramCache ) {

        this.recordedProgramCache = recordedProgramCache;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgramEntityDetails : enter" );
        Log.d( TAG, "recordedProgramEntityDetails : chanId=" + chanId + ", startTime=" + startTime );

        return this.recordedProgramCache.get( chanId, startTime );
    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {

        throw new UnsupportedOperationException( "Operation is not available" );

    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {

        throw new UnsupportedOperationException( "Operation is not available" );

    }

}

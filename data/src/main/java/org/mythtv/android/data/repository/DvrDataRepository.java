package org.mythtv.android.data.repository;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.mapper.ProgramEntityDataMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class DvrDataRepository implements DvrRepository {

    private static final String TAG = DvrDataRepository.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final TitleInfoEntityDataMapper titleInfoEntityDataMapper;
    private final ProgramEntityDataMapper programEntityDataMapper;

    @Inject
    public DvrDataRepository( DvrDataStoreFactory dvrDataStoreFactory, TitleInfoEntityDataMapper titleInfoEntityDataMapper, ProgramEntityDataMapper programEntityDataMapper ) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.titleInfoEntityDataMapper = titleInfoEntityDataMapper;
        this.programEntityDataMapper = programEntityDataMapper;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<TitleInfo>> titleInfos() {
        Log.d( TAG, "titleInfos : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.titleInfoEntityList()
                .map( titleInfoEntities -> this.titleInfoEntityDataMapper.transform( titleInfoEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Program>> recordedPrograms( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "recordedPrograms : enter" );
        Log.d( TAG, "recordedPrograms : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup )
                .map( recordedProgramEntities -> this.programEntityDataMapper.transform( recordedProgramEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Program> recordedProgram( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgram : enter" );
        Log.d( TAG, "recordedProgram : chanId=" + chanId + ", startTime=" + startTime );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.create( chanId, startTime );

        return dvrDataStore.recordedProgramEntityDetails( chanId, startTime )
                .map( recordedProgramEntity -> this.programEntityDataMapper.transform( recordedProgramEntity ) );
    }

}

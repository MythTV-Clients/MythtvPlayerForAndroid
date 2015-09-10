package org.mythtv.android.data.repository;

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

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final TitleInfoEntityDataMapper titleInfoEntityDataMapper;
    private final ProgramEntityDataMapper programEntityDataMapper;

    @Inject
    public DvrDataRepository(DvrDataStoreFactory dvrDataStoreFactory, TitleInfoEntityDataMapper titleInfoEntityDataMapper, ProgramEntityDataMapper programEntityDataMapper) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.titleInfoEntityDataMapper = titleInfoEntityDataMapper;
        this.programEntityDataMapper = programEntityDataMapper;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<TitleInfo>> titleInfos() {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        return dvrDataStore.titleInfoEntityList()
                .map( titleInfoEntities -> this.titleInfoEntityDataMapper.transform( titleInfoEntities ) );

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Program>> recordedPrograms( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        return dvrDataStore.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup )
                .map(recordedProgramEntities -> this.programEntityDataMapper.transform( recordedProgramEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Program> recordedProgram( int chanId, DateTime startTime ) {

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.create( chanId, startTime );
        return dvrDataStore.recordedProgramEntityDetails( chanId, startTime )
                .map( recordedProgramEntity -> this.programEntityDataMapper.transform( recordedProgramEntity ) );
    }

}

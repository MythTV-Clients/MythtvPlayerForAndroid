package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.net.DvrApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dmfrey on 8/27/15.
 */
public class MasterBackendDvrDataStore implements DvrDataStore {

    private final DvrApi api;
    private final ProgramCache recordedProgramCache;

    private final Action1<ProgramEntity> saveToCacheAction =
            programEntity -> {
                if( null != programEntity ) {
                    MasterBackendDvrDataStore.this.recordedProgramCache.put( programEntity );
                }
            };

    public MasterBackendDvrDataStore(DvrApi api, ProgramCache recordedProgramCache) {

        this.api = api;
        this.recordedProgramCache = recordedProgramCache;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        return this.api.titleInfoEntityList();
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {

        return this.api.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup );
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {

        return this.api.recordedProgramById( chanId, startTime )
                .doOnNext( saveToCacheAction );
    }

}

package org.mythtv.android.data.repository.datasource;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public interface DvrDataStore {

    Observable<List<TitleInfoEntity>> titleInfoEntityList();

    Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup );

    Observable<ProgramEntity> recordedProgramEntityDetails( final int chanId, final DateTime startTime );

}

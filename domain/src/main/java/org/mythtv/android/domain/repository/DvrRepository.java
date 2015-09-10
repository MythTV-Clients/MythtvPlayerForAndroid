package org.mythtv.android.domain.repository;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.TitleInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface DvrRepository {

    Observable<List<TitleInfo>> titleInfos();

    Observable<List<Program>> recordedPrograms( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup );

    Observable<Program> recordedProgram( int chanId, DateTime startTime );

}

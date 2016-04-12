package org.mythtv.android.domain.repository;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.TitleInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface DvrRepository {

    Observable<List<TitleInfo>> titleInfos();

    Observable<List<Program>> recordedPrograms( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup );

    Observable<Program> recordedProgram( final int chanId, final DateTime startTime );

    Observable<List<Program>> upcoming( final int startIndex, final int count, final boolean showAll, final int recordId, final int recStatus );

    Observable<List<Program>> recent();

    Observable<List<Encoder>> encoders();

    Observable<Boolean> updateRecordingWatchedStatus( final int chanId, final DateTime startTime, final boolean watched );

}

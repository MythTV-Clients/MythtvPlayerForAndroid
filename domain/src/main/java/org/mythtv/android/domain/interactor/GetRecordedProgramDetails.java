package org.mythtv.android.domain.interactor;

import org.joda.time.DateTime;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRecordedProgramDetails extends UseCase {

    private final int chanId;
    private final DateTime startTime;
    private final DvrRepository dvrRepository;

    public GetRecordedProgramDetails( final int chanId, final DateTime startTime, final DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.chanId = chanId;
        this.startTime = startTime;
        this.dvrRepository = dvrRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.dvrRepository.recordedProgram( this.chanId, this.startTime );
    }

}

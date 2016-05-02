package org.mythtv.android.domain.interactor;

import org.joda.time.DateTime;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by dmfrey on 4/9/16.
 */
public class PostUpdatedRecordedWatchedStatus extends DynamicUseCase {

    private final DvrRepository dvrRepository;

    public PostUpdatedRecordedWatchedStatus( final DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.dvrRepository = dvrRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final int chanId = (Integer) parameters.get( "CHAN_ID" );
        final DateTime startTime = (DateTime) parameters.get( "START_TIME" );
        final boolean watched = (Boolean) parameters.get( "WATCHED" );

        return this.dvrRepository.updateWatchedStatus( chanId, startTime, watched );
    }

}

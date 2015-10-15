package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRecordedProgramList extends UseCase {

    private final boolean descending;
    private final int startIndex;
    private final int count;
    private final String titleRegEx;
    private final String recGroup;
    private final String storageGroup;
    private final DvrRepository dvrRepository;

    @Inject
    public GetRecordedProgramList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup, DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;
        this.dvrRepository = dvrRepository;

    }

    @Override
    public Observable buildUseCaseObservable() {

        return this.dvrRepository.recordedPrograms( this.descending, this.startIndex, this.count, this.titleRegEx, this.recGroup, this.storageGroup );
    }

}

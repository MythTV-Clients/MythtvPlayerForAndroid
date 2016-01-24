package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRecentProgramList extends UseCase {

    private final DvrRepository dvrRepository;

    @Inject
    public GetRecentProgramList( DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.dvrRepository = dvrRepository;

    }

    @Override
    public Observable buildUseCaseObservable() {

        return this.dvrRepository.recent();
    }

}

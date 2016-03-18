package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import rx.Observable;

/**
 * Created by dmfrey on 10/25/15.
 */
public class GetLiveStreamDetails extends UseCase {

    private final int id;
    private final ContentRepository contentRepository;

    public GetLiveStreamDetails( final int id, final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.id = id;
        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.contentRepository.liveStreamInfo( this.id );
    }

}

package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import rx.Observable;

/**
 * Created by dmfrey on 10/25/15.
 */
public class GetLiveStreamsList extends UseCase {

    private final String filename;
    private final ContentRepository contentRepository;

    public GetLiveStreamsList( final String filename, final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.filename = filename;
        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.contentRepository.liveStreamInfos( this.filename );
    }

}

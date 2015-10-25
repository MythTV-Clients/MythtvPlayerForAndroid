package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetAddLiveStreamDetails extends UseCase {

    private final String storageGroup;
    private final String filename;
    private final String hostname;
    private final ContentRepository contentRepository;

    public GetAddLiveStreamDetails( final String storageGroup, final String filename, final String hostname, final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super( threadExecutor, postExecutionThread );

        this.storageGroup = storageGroup;
        this.filename = filename;
        this.hostname = hostname;
        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.contentRepository.addliveStream( this.storageGroup, this.filename, this.hostname );
    }

}

package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetAddLiveStreamDetails extends DynamicUseCase {

    private final ContentRepository contentRepository;

    public GetAddLiveStreamDetails(final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final String storageGroup = (String) parameters.get( "STORAGE_GROUP" );
        final String filename = (String) parameters.get( "FILE_NAME" );
        final String hostname = (String) parameters.get( "HOST_NAME" );

        return this.contentRepository.addliveStream( storageGroup, filename, hostname );
    }

}

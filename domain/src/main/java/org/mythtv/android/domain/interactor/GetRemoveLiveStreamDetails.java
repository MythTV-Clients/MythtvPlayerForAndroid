package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRemoveLiveStreamDetails extends DynamicUseCase {

    private final ContentRepository contentRepository;

    public GetRemoveLiveStreamDetails(final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final Integer id = (Integer) parameters.get( "LIVE_STREAM_ID" );

        return this.contentRepository.removeLiveStream( id );
    }

}

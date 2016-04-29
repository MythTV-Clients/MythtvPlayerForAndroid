package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by dmfrey on 4/9/16.
 */
public class PostUpdatedVideoWatchedStatus extends DynamicUseCase {

    private final VideoRepository videoRepository;

    public PostUpdatedVideoWatchedStatus( final VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final int videoId = (Integer) parameters.get( "VIDEO_ID" );
        final boolean watched = (Boolean) parameters.get( "WATCHED" );

        return this.videoRepository.updateWatchedStatus( videoId, watched );
    }

}

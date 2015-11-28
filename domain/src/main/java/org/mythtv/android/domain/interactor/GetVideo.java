package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 11/9/15.
 */
public class GetVideo extends UseCase {

    private final int id;
    private final VideoRepository videoRepository;

    @Inject
    public GetVideo( int id, VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.id = id;
        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.videoRepository.getVideo( id );
    }

}

package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 11/9/15.
 */
public class GetVideoList extends UseCase {

    private final String folder;
    private final String sort;
    private final boolean descending;
    private final int startIndex;
    private final int count;
    private final VideoRepository videoRepository;

    @Inject
    public GetVideoList( String folder, String sort, boolean descending, int startIndex, int count, VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.videoRepository.getVideoList( folder, sort, descending, startIndex, count );
    }

}

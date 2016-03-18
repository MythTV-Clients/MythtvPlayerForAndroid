package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 2/27/16.
 */
public class GetVideoSeriesCategoryContentTypeList extends UseCase {

    private final String contentType;
    private final VideoRepository videoRepository;

    @Inject
    public GetVideoSeriesCategoryContentTypeList(String contentType, VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.contentType = contentType;
        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.videoRepository.getVideoSeriesListByContentType( contentType );
    }

}

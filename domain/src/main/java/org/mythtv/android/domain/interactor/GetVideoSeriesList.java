package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.ContentType;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 11/13/15.
 */
public class GetVideoSeriesList extends UseCase {

    private final String series;
    private final VideoRepository videoRepository;

    @Inject
    public GetVideoSeriesList( String series, VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.series = series;
        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.videoRepository.getVideoListByContentTypeAndSeries( ContentType.TELEVISION, series );
    }

}

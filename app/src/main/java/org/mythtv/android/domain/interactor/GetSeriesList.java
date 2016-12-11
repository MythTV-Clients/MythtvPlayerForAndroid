package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.ContentType;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.Map;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/17/16.
 */

public class GetSeriesList extends DynamicUseCase {

    public static final String MEDIA_KEY = "media";

    private final DvrRepository dvrRepository;
    private final VideoRepository videoRepository;

    public GetSeriesList( final DvrRepository dvrRepository, final VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.dvrRepository = dvrRepository;
        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        if (!parameters.containsKey( MEDIA_KEY ) ) {

            throw new IllegalArgumentException( "Key [" + MEDIA_KEY + "] is required!" );
        }

        Media media = (Media) parameters.get( MEDIA_KEY );
        switch( media ) {

            case PROGRAM:

                return dvrRepository.titleInfos();

            case VIDEO:

                return videoRepository.getVideoSeriesListByContentType( ContentType.TELEVISION );

            default :
                throw new IllegalArgumentException( "Key [" + media.name() + "] not found" );
        }

    }

}

package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

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

    private final MediaItemRepository mediaItemRepository;

    public GetSeriesList( final MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.mediaItemRepository = mediaItemRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        if( null == parameters || !parameters.containsKey( MEDIA_KEY ) ) {

            throw new IllegalArgumentException( "Key [" + MEDIA_KEY + "] is required!" );
        }

        Media media = (Media) parameters.get( MEDIA_KEY );
        switch( media ) {

            case PROGRAM:

                return mediaItemRepository.series( media );

            case TELEVISION:
            case VIDEO:

                return mediaItemRepository.series( Media.TELEVISION );

            default :
                throw new IllegalArgumentException( "Key [" + media.name() + "] not found" );
        }

    }

}

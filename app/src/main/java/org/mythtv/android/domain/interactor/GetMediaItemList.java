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

public class GetMediaItemList extends DynamicUseCase {

    public static final String MEDIA_KEY = "media";
    public static final String TITLE_REGEX_KEY = "title_regex";

    public static final String TV_KEY = "tv";

    private final MediaItemRepository mediaItemRepository;

    public GetMediaItemList( final MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
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

                return getRecordedPrograms( media, parameters );

            case RECENT:
            case UPCOMING:
            case VIDEO:
            case MOVIE:
            case HOMEVIDEO:
            case MUSICVIDEO:
            case ADULT:

                return this.mediaItemRepository.mediaItems( media, null );

            case TELEVISION:

                if( parameters.containsKey( TV_KEY ) ) {

                    return this.mediaItemRepository.mediaItems( media, null );

                } else {

                    return this.mediaItemRepository.series( media );
                }

            default :
                throw new IllegalArgumentException( "Key [" + media.name() + "] not found" );
        }

    }

    private Observable getRecordedPrograms( final Media media, final Map parameters ) {

        String titleRegEx = null;
        if( parameters.containsKey( TITLE_REGEX_KEY ) ) {
            titleRegEx = (String) parameters.get( TITLE_REGEX_KEY );
        }

        return this.mediaItemRepository.mediaItems( media, titleRegEx );
    }

}

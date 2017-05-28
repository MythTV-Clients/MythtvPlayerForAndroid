package org.mythtv.android.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/17/16.
 */

public class GetMediaItemList extends UseCase<List<MediaItem>, GetMediaItemList.Params> {

    public static final String MEDIA_KEY = "media";
    public static final String TITLE_REGEX_KEY = "title_regex";

    public static final String TV_KEY = "tv";

    private final MediaItemRepository mediaItemRepository;

    @Inject
    public GetMediaItemList( final MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.mediaItemRepository = mediaItemRepository;

    }

    @Override
    protected Observable<List<MediaItem>> buildUseCaseObservable( Params params ) {
        Preconditions.checkNotNull( params );

        switch( params.media ) {

            case PROGRAM:

                return getRecordedPrograms( params );

            case RECENT:
            case UPCOMING:
            case VIDEO:
            case MOVIE:
            case HOMEVIDEO:
            case MUSICVIDEO:
            case ADULT:

                return this.mediaItemRepository.mediaItems( params.media, null );

            case TELEVISION:

                 return this.mediaItemRepository.mediaItems( params.media, params.titleRegEx );

            default :
                throw new IllegalArgumentException( "Key [" + params.media.name() + "] not found" );
        }

    }

    private Observable getRecordedPrograms( final Params params ) {

        return this.mediaItemRepository.mediaItems( params.media, params.titleRegEx );
    }

    public static final class Params {

        /* private */ final Media media;
        /* private */ final String titleRegEx;
        /* private */ final boolean tv;

        private Params( final Media media, final String titleRegEx, final boolean tv ) {

            this.media = media;
            this.titleRegEx = titleRegEx;
            this.tv = tv;

        }

        public static Params forMedia( final Media media ) {

            return new Params( media, null, false );
        }

        public static Params forMedia( final Media media, final String titleRegEx ) {

            return new Params( media, titleRegEx,false );
        }

        public static Params forMedia( final Media media, final String titleRegEx, final boolean tv ) {

            return new Params( media, titleRegEx, tv );
        }

        public static Params forMedia( final Media media, final boolean tv ) {

            return new Params( media, null, tv );
        }

    }

}

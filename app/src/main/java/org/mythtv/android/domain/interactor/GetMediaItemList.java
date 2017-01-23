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

public class GetMediaItemList extends DynamicUseCase {

    public static final String MEDIA_KEY = "media";
    public static final String DESCENDING_KEY = "descending";
    public static final String START_INDEX_KEY = "start_index";
    public static final String COUNT_KEY = "count";
    public static final String TITLE_REGEX_KEY = "title_regex";
    public static final String REC_GROUP_KEY = "rec_group";
    public static final String STORAGE_GROUP_KEY = "storage_group";
    public static final String FOLDER_KEY = "folder";
    public static final String SORT_KEY = "sort";

    public static final String TV_KEY = "tv";

    private final DvrRepository dvrRepository;
    private final VideoRepository videoRepository;

    public GetMediaItemList( final DvrRepository dvrRepository, final VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
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

                return getRecordedPrograms( parameters );

            case RECENT:

                return this.dvrRepository.recent();

            case UPCOMING:

                return this.dvrRepository.upcoming( -1, -1, false, -1, -1 );

            case VIDEO:

                return getVideos( parameters );

            case MOVIE:
            case HOMEVIDEO:
            case MUSICVIDEO:
            case ADULT:

                return videoRepository.getVideoListByContentType( media.name() );

            case TELEVISION:

                if( parameters.containsKey( TV_KEY ) ) {

                    return videoRepository.getVideoListByContentType( media.name() );

                } else {

                    return getVideoSeriesList(parameters);
                }

            default :
                throw new IllegalArgumentException( "Key [" + media.name() + "] not found" );
        }

    }

    private Observable getRecordedPrograms( Map parameters ) {

        Boolean descending = Boolean.FALSE;
        if( parameters.containsKey( DESCENDING_KEY ) ) {
            descending = (Boolean) parameters.get( DESCENDING_KEY );
        }

        Integer startIndex = -1;
        if( parameters.containsKey( START_INDEX_KEY ) ) {
            startIndex = (Integer) parameters.get( START_INDEX_KEY );
        }

        Integer count = -1;
        if( parameters.containsKey( COUNT_KEY ) ) {
            count = (Integer) parameters.get( COUNT_KEY );
        }

        String titleRegEx = null;
        if( parameters.containsKey( TITLE_REGEX_KEY ) ) {
            titleRegEx = (String) parameters.get( TITLE_REGEX_KEY );
        }

        String recGroup = null;
        if( parameters.containsKey( REC_GROUP_KEY ) ) {
            recGroup = (String) parameters.get( REC_GROUP_KEY );
        }

        String storageGroup = null;
        if( parameters.containsKey( STORAGE_GROUP_KEY ) ) {
            storageGroup = (String) parameters.get( STORAGE_GROUP_KEY );
        }

        return this.dvrRepository.recordedPrograms( descending, startIndex, count, titleRegEx, recGroup, storageGroup );
    }

    private Observable getVideos( Map parameters ) {

        String folder = null;
        if( parameters.containsKey( FOLDER_KEY ) ) {
            folder = (String) parameters.get( FOLDER_KEY );
        }

        String sort = null;
        if( parameters.containsKey( SORT_KEY ) ) {
            sort = (String) parameters.get( SORT_KEY );
        }

        Boolean descending = Boolean.FALSE;
        if( parameters.containsKey( DESCENDING_KEY ) ) {
            descending = (Boolean) parameters.get( DESCENDING_KEY );
        }

        Integer startIndex = -1;
        if( parameters.containsKey( START_INDEX_KEY ) ) {
            startIndex = (Integer) parameters.get( START_INDEX_KEY );
        }

        Integer count = -1;
        if( parameters.containsKey( COUNT_KEY ) ) {
            count = (Integer) parameters.get( COUNT_KEY );
        }

        return this.videoRepository.getVideoList( folder, sort, descending, startIndex, count );
    }

    private Observable getVideoSeriesList( Map parameters ) {

        String titleRegEx = null;
        if( parameters.containsKey( TITLE_REGEX_KEY ) ) {
            titleRegEx = (String) parameters.get( TITLE_REGEX_KEY );
        }

        return videoRepository.getVideoListByContentTypeAndSeries( ContentType.TELEVISION, titleRegEx );
    }

}

package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ErrorEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.domain.Error;
import org.mythtv.android.domain.MediaItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/22/17.
 */
@Singleton
@SuppressWarnings( "PMD.GodClass" )
public final class MediaItemDataMapper {

    private static final String TAG = MediaItemDataMapper.class.getSimpleName();

    private MediaItemDataMapper() { }

    public static MediaItem transform( final MediaItemEntity mediaItem ) {

        List<Error> errors = Collections.emptyList();
        if( null != mediaItem.validationErrors() && !mediaItem.validationErrors().isEmpty() ) {

            errors = new ArrayList<>( mediaItem.validationErrors().size() );

            for( ErrorEntity errorEntity : mediaItem.validationErrors() ) {

                errors.add( Error.create( errorEntity.field(), errorEntity.defaultMessage(), errorEntity.messageResource() ) );

            }

        }

        return MediaItem.create(
            mediaItem.id(),
            mediaItem.media(),
            mediaItem.title(),
            mediaItem.subTitle(),
            mediaItem.description(),
            mediaItem.startDate(),
            mediaItem.programFlags(),
            mediaItem.season(),
            mediaItem.episode(),
            mediaItem.studio(),
            mediaItem.castMembers(),
            mediaItem.characters(),
            mediaItem.url(),
            mediaItem.fanartUrl(),
            mediaItem.coverartUrl(),
            mediaItem.bannerUrl(),
            mediaItem.previewUrl(),
            mediaItem.contentType(),
            mediaItem.duration(),
            mediaItem.percentComplete(),
            mediaItem.recording(),
            mediaItem.liveStreamId(),
            mediaItem.watched(),
            mediaItem.updateSavedBookmarkUrl(),
            mediaItem.bookmark(),
            mediaItem.inetref(),
            mediaItem.certification(),
            mediaItem.parentalLevel(),
            mediaItem.recordingGroup(),
            errors );
    }

    public static List<MediaItem> transformMediaItems( final Collection<MediaItemEntity> mediaItemCollection ) {

        List<MediaItem> mediaItemList = new ArrayList<>( mediaItemCollection.size() );

        MediaItem mediaItem;
        for( MediaItemEntity mediaItemEntity : mediaItemCollection ) {

            mediaItem = transform( mediaItemEntity );
            if( null != mediaItemEntity ) {

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

}

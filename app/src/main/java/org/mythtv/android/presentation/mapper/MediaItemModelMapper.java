package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.Error;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.ErrorModel;
import org.mythtv.android.presentation.model.MediaItemModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 7/10/16.
 */

@PerActivity
public class MediaItemModelMapper {

    @Inject
    public MediaItemModelMapper() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public MediaItemModel transform( MediaItem mediaItem ) {

        List<ErrorModel> errors = new ArrayList<>();
        for( Error error : mediaItem.validationErrors() ) {
            errors.add( ErrorModel.fromError( error ) );
        }

        return MediaItemModel.create(
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

    public List<MediaItemModel> transform( Collection<MediaItem> mediaItemCollection ) {

        List<MediaItemModel> mediaItemList = new ArrayList<>( mediaItemCollection.size() );

        MediaItemModel mediaItemModel;
        for( MediaItem mediaItem : mediaItemCollection ) {

            mediaItemModel = transform( mediaItem );
            if( null != mediaItemModel ) {

                mediaItemList.add( mediaItemModel );

            }

        }

        return mediaItemList;
    }

}

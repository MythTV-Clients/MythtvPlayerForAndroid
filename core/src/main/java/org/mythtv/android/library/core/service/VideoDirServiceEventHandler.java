package org.mythtv.android.library.core.service;

import android.util.Log;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.domain.videoDir.VideoDir;
import org.mythtv.android.library.core.domain.videoDir.VideoDirItem;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosForParentEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.videoDir.AllVideoDirItemsEvent;
import org.mythtv.android.library.events.videoDir.AllVideoDirsEvent;
import org.mythtv.android.library.events.videoDir.RequestAllVideoDirItemsEvent;
import org.mythtv.android.library.events.videoDir.RequestAllVideoDirsEvent;
import org.mythtv.android.library.events.videoDir.VideoDirDetails;
import org.mythtv.android.library.events.videoDir.VideoDirItemDetails;
import org.mythtv.android.library.persistence.service.VideoDirPersistenceService;
import org.mythtv.android.library.persistence.service.VideoPersistenceService;
import org.mythtv.android.library.persistence.service.video.VideoPersistenceServiceEventHandler;
import org.mythtv.android.library.persistence.service.videoDir.VideoDirPersistenceServiceEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDirServiceEventHandler implements VideoDirService {

    private static final String TAG = VideoDirServiceEventHandler.class.getSimpleName();

    VideoDirPersistenceService mVideoDirPersistenceService;
    VideoPersistenceService mVideoPersistenceService;

    public VideoDirServiceEventHandler() {

        mVideoDirPersistenceService = new VideoDirPersistenceServiceEventHandler();
        mVideoPersistenceService = new VideoPersistenceServiceEventHandler();

    }

    @Override
    public AllVideoDirItemsEvent requestAllVideoDirItems( RequestAllVideoDirItemsEvent event ) {
        Log.d( TAG, "requestAllVideoDirItems : enter" );

        List<VideoDirItem> videoDirItems = new ArrayList<>();

        AllVideoDirsEvent videoDirsEvent = mVideoDirPersistenceService.requestAllVideoDirs( new RequestAllVideoDirsEvent( event.getParent() ) );
        if( videoDirsEvent.isEntityFound() ) {

            for( VideoDirDetails detail : videoDirsEvent.getDetails() ) {

                VideoDir videoDir = VideoDir.fromDetails( detail );

                VideoDirItem videoDirItem = new VideoDirItem();
                videoDirItem.setId( videoDir.getId() );
                videoDirItem.setPath( videoDir.getPath() );
                videoDirItem.setName( videoDir.getName() );
                videoDirItem.setParent( videoDir.getParent() );
                videoDirItem.setSort( 0 );
                videoDirItems.add( videoDirItem );

            }

        }

        AllVideosEvent videosEvent = mVideoPersistenceService.requestAllVideosForParent( new RequestAllVideosForParentEvent( event.getParent() ) );
        if( videosEvent.isEntityFound() ) {

            for( VideoDetails detail : videosEvent.getDetails() ) {

                Video video = Video.fromDetails( detail );

                VideoDirItem videoDirItem = new VideoDirItem();
                videoDirItem.setId( video.getId().longValue() );
                videoDirItem.setName( video.getTitle() );
                videoDirItem.setSort( 1 );
                videoDirItem.setVideo( video );
                videoDirItems.add( videoDirItem );

            }

        }

        Collections.sort( videoDirItems, new Comparator<VideoDirItem>() {

            @Override
            public int compare( VideoDirItem lhs, VideoDirItem rhs ) {

                int compareSort = new Integer( lhs.getSort() ).compareTo( rhs.getSort() );
                if( compareSort != 0 ) return compareSort;

                return lhs.getName().compareTo( rhs.getName() );
            }

        });

        List<VideoDirItemDetails> details = new ArrayList<>();
        for( VideoDirItem videoDirItem : videoDirItems ) {

            details.add( videoDirItem.toDetails() );

        }

        Log.d( TAG, "requestAllVideoDirItems : exit" );
        return new AllVideoDirItemsEvent( details );
    }

}

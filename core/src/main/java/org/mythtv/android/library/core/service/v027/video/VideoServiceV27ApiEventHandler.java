package org.mythtv.android.library.core.service.v027.video;

import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.VideoService;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideoEvent;
import org.mythtv.android.library.events.video.DeleteVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.RequestVideoEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDeletedEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.library.events.video.VideosDeletedEvent;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;
import org.mythtv.android.library.persistence.service.VideoPersistenceService;
import org.mythtv.android.library.persistence.service.video.VideoPersistenceServiceEventHandler;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.v027.beans.VideoMetadataInfo;
import org.mythtv.services.api.v027.beans.VideoMetadataInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/24/14.
 */
public class VideoServiceV27ApiEventHandler implements VideoService {

    private static final String TAG = VideoServiceV27ApiEventHandler.class.getSimpleName();

    private static final String VIDEO_LIST_REQ_ID = "VIDOE_LIST_REQ_ID";

    MythTvApi027Context mMythTvApiContext;
    VideoPersistenceService mVideoPersistenceService;

    public VideoServiceV27ApiEventHandler() {

        mMythTvApiContext = (MythTvApi027Context) MainApplication.getInstance().getMythTvApiContext();
        mVideoPersistenceService = new VideoPersistenceServiceEventHandler();

    }

    @Override
    public AllVideosEvent requestAllVideos( RequestAllVideosEvent event ) {

        return mVideoPersistenceService.requestAllVideos( event );
    }

    @Override
    public AllVideosEvent searchVideos( SearchVideosEvent event ) {

        return mVideoPersistenceService.searchVideos( event );
    }

    @Override
    public VideosUpdatedEvent updateVideos( UpdateVideosEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( VIDEO_LIST_REQ_ID, true );

        try {

            VideoMetadataInfoList mVideoList = mMythTvApiContext.getVideoService().getVideoList( event.getDescending(), event.getStartIndex(), event.getCount(), eTagInfo, VIDEO_LIST_REQ_ID );
            if( null != mVideoList ) {

                List<VideoDetails> videoDetails = new ArrayList<>();

                for( VideoMetadataInfo video : mVideoList.getVideoMetadataInfos() ) {

                    videoDetails.add( VideoHelper.toDetails( video ) );

                }

                event.setDetails( videoDetails );
                VideosUpdatedEvent updated = mVideoPersistenceService.updateVideos( event );
                if( updated.isEntityFound() ) {

                    return new VideosUpdatedEvent( updated.getDetails() );
                }
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "updateVideos : error", e );

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        return VideosUpdatedEvent.notUpdated();
    }

    @Override
    public VideosDeletedEvent deleteVideos( DeleteVideosEvent event ) {

        return mVideoPersistenceService.deleteVideos( event );
    }

    @Override
    public VideoDetailsEvent requestVideo( RequestVideoEvent event ) {

        return mVideoPersistenceService.requestVideo( event );
    }

    @Override
    public VideoDeletedEvent deleteVideo( DeleteVideoEvent event ) {

        return mVideoPersistenceService.deleteVideo( event );
    }

}

package org.mythtv.android.library.core.service;

import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.v028.video.VideoHelper;
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
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.v028.beans.VideoMetadataInfo;
import org.mythtv.services.api.v028.beans.VideoMetadataInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/24/14.
 */
public class VideoServiceEventHandler implements VideoService {

    private static final String TAG = VideoServiceEventHandler.class.getSimpleName();

    VideoPersistenceService mVideoPersistenceService;

    public VideoServiceEventHandler() {

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

        return MainApplication.getInstance().getVideoService().updateVideos( new UpdateVideosEvent( null, null, false, null, null ) );
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

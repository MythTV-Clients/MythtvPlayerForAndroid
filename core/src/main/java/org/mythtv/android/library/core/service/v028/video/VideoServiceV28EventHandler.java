package org.mythtv.android.library.core.service.v028.video;

import org.mythtv.android.library.core.service.VideoService;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v028.beans.VideoMetadataInfo;
import org.mythtv.services.api.v028.beans.VideoMetadataInfoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/24/14.
 */
public class VideoServiceV28EventHandler implements VideoService {

    private static final String VIDEO_LIST_REQ_ID = "VIDOE_LIST_REQ_ID";

    MythTvApi028Context mMythTvApiContext;

    public VideoServiceV28EventHandler( MythTvApiContext mythTvApiContext ) {

        mMythTvApiContext = (MythTvApi028Context) mythTvApiContext;

    }

    @Override
    public AllVideosEvent getVideoList( RequestAllVideosEvent event ) {

        List<VideoDetails> videoDetails = new ArrayList<VideoDetails>();

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( VIDEO_LIST_REQ_ID, true );
        VideoMetadataInfoList videoList = mMythTvApiContext.getVideoService().getVideoList( event.getFolder(), event.getSort(), event.getDescending(), event.getStartIndex(), event.getCount(), eTagInfo, VIDEO_LIST_REQ_ID);
        for( VideoMetadataInfo video : videoList.getVideoMetadataInfos() ) {
            videoDetails.add( VideoHelper.toDetails( video ) );
        }

        return new AllVideosEvent( videoDetails );
    }

}

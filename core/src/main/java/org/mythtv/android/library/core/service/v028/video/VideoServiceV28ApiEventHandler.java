/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.library.core.service.v028.video;

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
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.v028.beans.VideoMetadataInfo;
import org.mythtv.services.api.v028.beans.VideoMetadataInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/*
 * Created by dmfrey on 11/24/14.
 */
public class VideoServiceV28ApiEventHandler implements VideoService {

    private static final String TAG = VideoServiceV28ApiEventHandler.class.getSimpleName();

    private static final String VIDEO_LIST_REQ_ID = "VIDOE_LIST_REQ_ID";

    MythTvApi028Context mMythTvApiContext;
    VideoPersistenceService mVideoPersistenceService;

    public VideoServiceV28ApiEventHandler() {

        mMythTvApiContext = (MythTvApi028Context) MainApplication.getInstance().getMythTvApiContext();
        mVideoPersistenceService = new VideoPersistenceServiceEventHandler();

    }

    @Override
    public AllVideosEvent requestAllVideos( RequestAllVideosEvent event ) {

        return mVideoPersistenceService.requestAllVideos( event );
    }

    @Override
    public AllVideosEvent requestAllVideoTvTitles( RequestAllVideosEvent event ) {

        return mVideoPersistenceService.requestAllVideoTvTitles( event );
    }

    @Override
    public AllVideosEvent requestAllVideoTvTitleSeasons( RequestAllVideosEvent event ) {

        return mVideoPersistenceService.requestAllVideoTvTitleSeasons(event);
    }

    @Override
    public AllVideosEvent searchVideos( SearchVideosEvent event ) {

        return mVideoPersistenceService.searchVideos( event );
    }

    @Override
    public VideosUpdatedEvent updateVideos( UpdateVideosEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( VIDEO_LIST_REQ_ID, true );

        try {

            VideoMetadataInfoList mVideoList = mMythTvApiContext.getVideoService().getVideoList( event.getFolder(), event.getSort(), event.getDescending(), event.getStartIndex(), event.getCount(), eTagInfo, VIDEO_LIST_REQ_ID );
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
            //Log.w( TAG, "updateVideos : error", e );

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

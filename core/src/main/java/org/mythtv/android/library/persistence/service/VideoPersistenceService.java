package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideoEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.library.events.video.VideoDeletedEvent;
import org.mythtv.android.library.events.video.VideosDeletedEvent;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;
import org.mythtv.android.library.events.video.RequestVideoEvent;

/**
 * Created by dmfrey on 4/15/15.
 */
public interface VideoPersistenceService {

    AllVideosEvent requestAllVideos( RequestAllVideosEvent event );

    AllVideosEvent searchVideos( SearchVideosEvent event );

    VideosUpdatedEvent updateVideos( UpdateVideosEvent event );

    VideosDeletedEvent deleteVideos( DeleteVideosEvent event );

    VideoDetailsEvent requestVideo( RequestVideoEvent event );

    VideoDeletedEvent deleteVideo( DeleteVideoEvent event );

}

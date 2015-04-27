package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideoEvent;
import org.mythtv.android.library.events.video.DeleteVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.RequestVideoEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDeletedEvent;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.library.events.video.VideosDeletedEvent;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;

/**
 * Created by dmfrey on 11/24/14.
 */
public interface VideoService {

    AllVideosEvent requestAllVideos( RequestAllVideosEvent event );

    AllVideosEvent searchVideos( SearchVideosEvent event );

    VideosUpdatedEvent updateVideos( UpdateVideosEvent event );

    VideosDeletedEvent deleteVideos( DeleteVideosEvent event );

    VideoDetailsEvent requestVideo( RequestVideoEvent event );

    VideoDeletedEvent deleteVideo( DeleteVideoEvent event );

}

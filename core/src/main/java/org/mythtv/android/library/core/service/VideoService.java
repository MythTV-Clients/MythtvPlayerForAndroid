package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;

/**
 * Created by dmfrey on 11/24/14.
 */
public interface VideoService {

    AllVideosEvent getVideoList( RequestAllVideosEvent event );

}

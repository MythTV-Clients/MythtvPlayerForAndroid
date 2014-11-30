package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public interface ContentService {

    LiveStreamDetailsEvent getLiveStream( RequestLiveStreamDetailsEvent event );

    LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event );

    LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event );

    LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event );

    LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event );

}

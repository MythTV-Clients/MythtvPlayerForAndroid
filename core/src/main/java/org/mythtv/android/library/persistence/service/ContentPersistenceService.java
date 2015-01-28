package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.AllLiveStreamInfosEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamInfosEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public interface ContentPersistenceService {

    AllLiveStreamInfosEvent refreshLiveStreamInfoList( AllLiveStreamInfosEvent event );

    LiveStreamDetailsEvent updateLiveStream( LiveStreamDetailsEvent event );

    LiveStreamAddedEvent addLiveStream( LiveStreamAddedEvent event );

    LiveStreamAddedEvent addRecordingLiveStream( LiveStreamAddedEvent event );

    LiveStreamAddedEvent addVideoLiveStream( LiveStreamAddedEvent event );

    LiveStreamRemovedEvent removeLiveStream( LiveStreamRemovedEvent event );

}

package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.content.AllLiveStreamsEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.LiveStreamsUpdatedEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamsEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.UpdateLiveStreamsEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public interface ContentPersistenceService {

    AllLiveStreamsEvent requestAllLiveStreams( RequestAllLiveStreamsEvent event );

    LiveStreamsUpdatedEvent updateLiveStreams( UpdateLiveStreamsEvent event );

    LiveStreamDetailsEvent requestLiveStream( RequestLiveStreamDetailsEvent event );

    LiveStreamAddedEvent addLiveStream( LiveStreamAddedEvent event );

    LiveStreamAddedEvent addRecordingLiveStream( LiveStreamAddedEvent event );

    LiveStreamAddedEvent addVideoLiveStream( LiveStreamAddedEvent event );

    LiveStreamDetailsEvent updateLiveStream( LiveStreamDetailsEvent event );

    LiveStreamRemovedEvent removeLiveStream( LiveStreamRemovedEvent event );

}

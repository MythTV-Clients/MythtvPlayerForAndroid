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

package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.AllLiveStreamsEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamsUpdatedEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamsEvent;
import org.mythtv.android.library.events.content.UpdateLiveStreamsEvent;

/*
 * Created by dmfrey on 11/18/14.
 */
public interface ContentService {

    AllLiveStreamsEvent requestAllLiveStreams( RequestAllLiveStreamsEvent event );

    LiveStreamsUpdatedEvent updateLiveStreams( UpdateLiveStreamsEvent event );

    LiveStreamDetailsEvent requestLiveStream( RequestLiveStreamDetailsEvent event );

    LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event );

    LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event );

    LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event );

    LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event );

}

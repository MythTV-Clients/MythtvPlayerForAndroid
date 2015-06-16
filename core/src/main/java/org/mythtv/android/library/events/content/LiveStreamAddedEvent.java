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

package org.mythtv.android.library.events.content;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.CreatedEvent;

/*
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamAddedEvent extends CreatedEvent {

    private final Integer key;
    private Integer recordedId;
    private Integer chanId;
    private DateTime startTime;
    private Integer videoId;
    private final LiveStreamDetails details;

    public LiveStreamAddedEvent( final Integer key, final LiveStreamDetails details ) {

        this.key = key;
        this.details = details;

        this.recordedId = null;
        this.chanId = null;
        this.startTime = null;
        this.videoId = null;

    }

    public Integer getKey() {
        return key;
    }

    public LiveStreamDetails getDetails() {
        return details;
    }

    public Integer getRecordedId() {
        return recordedId;
    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public static LiveStreamAddedEvent recordingAdded( final Integer recordedId, final LiveStreamDetails details ) {

        LiveStreamAddedEvent ev = new LiveStreamAddedEvent( null, details );
        ev.recordedId = recordedId;

        return ev;
    }

    public static LiveStreamAddedEvent recordingAdded( final Integer chanId, final DateTime startTime, final LiveStreamDetails details ) {

        LiveStreamAddedEvent ev = new LiveStreamAddedEvent( null, details );
        ev.chanId = chanId;
        ev.startTime = startTime;

        return ev;
    }

    public static LiveStreamAddedEvent videoAdded( final Integer videoId, final LiveStreamDetails details ) {

        LiveStreamAddedEvent ev = new LiveStreamAddedEvent( null, details );
        ev.videoId = videoId;

        return ev;
    }

    public static LiveStreamAddedEvent notAdded() {

        return new LiveStreamAddedEvent( null, null );
    }

}

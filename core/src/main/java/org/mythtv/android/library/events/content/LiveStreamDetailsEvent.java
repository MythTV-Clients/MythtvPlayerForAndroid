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
import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamDetailsEvent extends ReadEvent {

    private final Integer chanId;
    private final DateTime startTime;
    private final LiveStreamDetails details;

    private boolean modified;

    public LiveStreamDetailsEvent( final Integer chanId, final DateTime startTime, final LiveStreamDetails details ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.details = details;

        entityFound = true;
        modified = true;

    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() { return startTime; }

    public LiveStreamDetails getDetails() {
        return details;
    }

    public boolean isModified() {
        return modified;
    }

    public static LiveStreamDetailsEvent notModified( final Integer chanId, final DateTime startTime ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( chanId, startTime, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

    public static LiveStreamDetailsEvent notFound( final Integer chanId, final DateTime startTime ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( chanId, startTime, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

}

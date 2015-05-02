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
import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RequestLiveStreamDetailsEvent extends RequestReadEvent {

    private final Integer chanId;
    private final DateTime startTime;

    public RequestLiveStreamDetailsEvent( final Integer chanId, final DateTime startTime ) {

        this.chanId = chanId;
        this.startTime = startTime;

    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() { return startTime; }

}

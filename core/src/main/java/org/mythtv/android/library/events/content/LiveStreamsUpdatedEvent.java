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

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.List;

/**
 * Created by dmfrey on 4/5/15.
 */
public class LiveStreamsUpdatedEvent extends UpdatedEvent {

    private final List<LiveStreamDetails> details;

    public LiveStreamsUpdatedEvent(final List<LiveStreamDetails> details) {

        this.details = details;

        if( null != details ) {

            entityFound = !details.isEmpty();

        } else {

            entityFound = false;

        }

    }

    public List<LiveStreamDetails> getDetails() {

        return details;
    }

    public static LiveStreamsUpdatedEvent notUpdated() {

        return new LiveStreamsUpdatedEvent( null );
    }

}

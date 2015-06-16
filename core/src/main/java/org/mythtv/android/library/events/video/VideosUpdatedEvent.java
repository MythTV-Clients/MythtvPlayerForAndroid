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

package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.Collections;
import java.util.List;

/*
 * Created by dmfrey on 4/16/15.
 */
public class VideosUpdatedEvent extends UpdatedEvent {

    private final List<VideoDetails> details;

    public VideosUpdatedEvent( final List<VideoDetails> details ) {

        if( null != details ) {

            this.details = Collections.unmodifiableList( details );
            entityFound = !details.isEmpty();

        } else {

            this.details = null;
            entityFound = false;

        }

    }

    public List<VideoDetails> getDetails() {

        return details;
    }

    public static VideosUpdatedEvent notUpdated() {

        return new VideosUpdatedEvent( null );
    }

}

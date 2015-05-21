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

package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllRecordedProgramsEvent extends RequestReadEvent {

    private final String title;
    private final String inetref;
    private final String recordingGroup;
    private final Integer limit;
    private final Integer offset;

    public RequestAllRecordedProgramsEvent( final String title, final String inetref, final String recordingGroup ) {

        this.title = title;
        this.inetref = inetref;
        this.recordingGroup = recordingGroup;
        this.limit = null;
        this.offset = null;

    }

    public RequestAllRecordedProgramsEvent( final String title, final String inetref, final String recordingGroup, final Integer limit, final Integer offset ) {

        this.title = title;
        this.inetref = inetref;
        this.recordingGroup = recordingGroup;
        this.limit = limit;
        this.offset = offset;

    }

    public String getTitle() { return title; }

    public String getInetref() { return inetref; }

    public String getRecordingGroup() { return recordingGroup; }

    public Integer getLimit() {

        return limit;
    }

    public Integer getOffset() {

        return offset;
    }

}

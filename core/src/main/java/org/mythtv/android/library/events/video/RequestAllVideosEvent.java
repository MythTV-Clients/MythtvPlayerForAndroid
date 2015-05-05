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

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllVideosEvent extends RequestReadEvent {

    private final String contentType;
    private final String title;
    private final Integer season;
    private final Integer limit;
    private final Integer offset;

    public RequestAllVideosEvent( final String contentType, final String title, final Integer season ) {

        this.contentType = contentType;
        this.title = title;
        this.season = season;
        this.limit = null;
        this.offset = null;

    }

    public RequestAllVideosEvent( final String contentType, final String title, final Integer season, final Integer limit, final Integer offset ) {

        this.contentType = contentType;
        this.title = title;
        this.season = season;
        this.limit = limit;
        this.offset = offset;

    }

    public String getContentType() {
        return contentType;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSeason() { return season; }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

}

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

import org.mythtv.android.library.events.UpdateEvent;

import java.util.List;

/**
 * Created by dmfrey on 4/16/15.
 */
public class UpdateVideosEvent extends UpdateEvent {

    private final String folder;
    private final String sort;
    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;

    private List<VideoDetails> details;

    public UpdateVideosEvent( final String folder, final String sort, final Boolean descending, final Integer startIndex, final Integer count ) {

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;

    }

    public UpdateVideosEvent( final String folder, final String sort, final Boolean descending, final Integer startIndex, final Integer count, List<VideoDetails> details ) {

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;

        this.details = details;

    }

    public String getFolder() {
        return folder;
    }

    public String getSort() {
        return sort;
    }

    public Boolean getDescending() {
        return descending;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getCount() {
        return count;
    }

    public List<VideoDetails> getDetails() {
        return details;
    }

    public void setDetails( List<VideoDetails> details ) {

        this.details = details;

    }

}

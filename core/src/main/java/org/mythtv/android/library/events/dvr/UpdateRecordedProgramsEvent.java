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

import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class UpdateRecordedProgramsEvent extends RequestReadEvent {

    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;
    private final String titleRegEx;
    private final String recGroup;
    private final String storageGroup;

    private List<ProgramDetails> details;

    public UpdateRecordedProgramsEvent( final Boolean descending, final Integer startIndex, final Integer count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

    }

    public UpdateRecordedProgramsEvent( final Boolean descending, final Integer startIndex, final Integer count, final String titleRegEx, final String recGroup, final String storageGroup, List<ProgramDetails> details ) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

        this.details = details;

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

    public String getTitleRegEx() { return titleRegEx; }

    public String getRecGroup() {
        return recGroup;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public List<ProgramDetails> getDetails() {
        return details;
    }

    public void setDetails( List<ProgramDetails> details ) {
        this.details = details;
    }

}

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

import org.mythtv.android.library.events.ReadEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Created by dmfrey on 11/12/14.
 */
public class AllProgramsEvent extends ReadEvent {

    private final List<ProgramDetails> details;

    private Map<String, List<ProgramDetails>> mPrograms = new TreeMap<>();
    private Map<String, String> mCategories = new TreeMap<>();
    private Map<ProgramDetails, Long> deleted;

    public AllProgramsEvent( final List<ProgramDetails> details ) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = !this.details.isEmpty();

        preparePrograms();

    }

    public List<ProgramDetails> getDetails() {
        return details;
    }

    public Map<String, List<ProgramDetails>> getPrograms() {
        return mPrograms;
    }

    public Map<String, String> getCategories() {
        return mCategories;
    }

    public Map<ProgramDetails, Long> getDeleted() {
        return deleted;
    }

    public void setDeleted( Map<ProgramDetails, Long> deleted ) {

        this.deleted = deleted;

    }

    private void preparePrograms() {

        for( ProgramDetails program : details ) {

            String cleanedTitle = program.getTitle();
            if( !mPrograms.containsKey( cleanedTitle ) ) {

                List<ProgramDetails> categoryPrograms = new ArrayList<>();
                categoryPrograms.add( program );
                mPrograms.put( cleanedTitle, categoryPrograms );

                mCategories.put( cleanedTitle, program.getTitle() );

            } else {

                mPrograms.get( cleanedTitle ).add( program );

            }

        }

    }

}

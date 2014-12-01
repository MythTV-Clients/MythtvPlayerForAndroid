package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.ReadEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllProgramsEvent extends ReadEvent {

    private final List<ProgramDetails> details;

    private Map<String, List<ProgramDetails>> mPrograms = new TreeMap<String, List<ProgramDetails>>();
    private Map<String, String> mCategories = new TreeMap<String, String>();

    public AllProgramsEvent( final List<ProgramDetails> details ) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = details.size() > 0;

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

    private void preparePrograms() {

        for( ProgramDetails program : details ) {

            String cleanedTitle = program.getTitle();
            if( !mPrograms.containsKey( cleanedTitle ) ) {

                List<ProgramDetails> categoryPrograms = new ArrayList<ProgramDetails>();
                categoryPrograms.add( program );
                mPrograms.put( cleanedTitle, categoryPrograms );

                mCategories.put( cleanedTitle, program.getTitle() );

            } else {

                mPrograms.get( cleanedTitle ).add( program );

            }

        }

    }

}

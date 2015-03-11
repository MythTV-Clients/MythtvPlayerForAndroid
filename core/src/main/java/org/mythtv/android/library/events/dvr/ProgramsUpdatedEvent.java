package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class ProgramsUpdatedEvent extends UpdatedEvent {

    private final List<ProgramDetails> details;

    public ProgramsUpdatedEvent(final List<ProgramDetails> details) {

        this.details = details;

        if( null != details ) {
            entityFound = !details.isEmpty();
        } else {
            entityFound = false;
        }

    }

    public List<ProgramDetails> getDetails() {

        return details;
    }

    public static ProgramsUpdatedEvent notUpdated() {

        return new ProgramsUpdatedEvent( null );
    }

}

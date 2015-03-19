package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeleteEvent;

import java.util.List;

/**
 * Created by dmfrey on 3/18/15.
 */
public class DeleteProgramsEvent extends DeleteEvent {

    private final List<ProgramDetails> details;

    public DeleteProgramsEvent( final List<ProgramDetails> details ) {

        this.details = details;

    }

    public List<ProgramDetails> getDetails() {

        return details;
    }

}
